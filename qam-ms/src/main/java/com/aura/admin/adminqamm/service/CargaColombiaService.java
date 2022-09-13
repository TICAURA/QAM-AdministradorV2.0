package com.aura.admin.adminqamm.service;


import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dto.CargaMasivaDto;
import com.aura.admin.adminqamm.dto.ClienteDto;
import com.aura.admin.adminqamm.dto.ColaboradorDto;
import com.aura.admin.adminqamm.dto.CuentaBancoDto;
import com.aura.admin.adminqamm.dto.DatosContactoDto;
import com.aura.admin.adminqamm.dto.PersonaDto;
import com.aura.admin.adminqamm.dto.request.CargaRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.AuxCarga;
import com.aura.admin.adminqamm.model.DetCarga;
import com.aura.admin.adminqamm.repository.AuxCargaRepository;
import com.aura.admin.adminqamm.repository.DetCargaRepository;

@Service
public class CargaColombiaService {
	
	Logger logger = LoggerFactory.getLogger(CargaColombiaService.class);
	
	@Autowired
	private AuxCargaRepository auxCargaRepository;
	
	@Autowired
	private DetCargaRepository detCargaRepository;
	
    public HSSFWorkbook obtenerWorkBook(int loggedIdUser) throws BusinessException{
    	
    	CellStyle style = null;
    	
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "Layout Colombia");
            
        String[] headers = new String[] { "name", "father_last_name", "mother_last_name",
                "email", "plate", "phone_number","document_number", "amount"};
        HSSFRow headerRow = sheet.createRow(0);
        
        style = workbook.createCellStyle();// Create style
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        
        for (int i = 0; i < headers.length; ++i) {
            String header = headers[i];
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(header);
            cell.setCellStyle(style);
        }
        return workbook;
        
    }
	
	public void insertCargaColombia(int loggedIdUser, CargaRequestDto cargaColombia) throws BusinessException {
		
		if (logger.isInfoEnabled()) {
			logger.info("/**** Procesa archivo carga masiva colombia  ****/" + cargaColombia.getArchivo().getName());
		}
		
		try {
			if (cargaColombia.getArchivo().getInputStream()==null) {
				throw new BusinessException("Archivo vacio", 401);
			}
		
			String nombreArchivo = cargaColombia.getArchivo().getName();
			
			if (nombreArchivo.endsWith(".xlsx")) {
				procesarXlsx(cargaColombia.getArchivo().getInputStream(), nombreArchivo);
			} else if (nombreArchivo.endsWith(".xls")) {
				procesarXls(cargaColombia.getArchivo().getInputStream(), nombreArchivo);
			} else {
				throw new BusinessException("Error formato incorrecto.", 401);
			}
		} catch (IOException e) {
			logger.error("ERROR al leer el archivo para la carga masiva mexico :: ",e.getMessage());
		}
		
		
		
		logger.info("/**** Informacion carga correctamente  ****/" + cargaColombia.getArchivo().getName());
	}
	
	private void procesarXls(InputStream inputStream, String nombreArchivo) {
		List<CargaMasivaDto> listaCargaDTO = new ArrayList<CargaMasivaDto>();
		
		List<DetCarga> listaDetCarga = new ArrayList<DetCarga>();
		
		try {
			 HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			 HSSFSheet firstSheet = workbook.getSheetAt(0);
			 Iterator iterator = firstSheet.iterator();
		        	        
			 DataFormatter formatter = new DataFormatter();
			
			 while (iterator.hasNext()) {
		    	DetCarga detCargaFila = new DetCarga();
		    	
		    	HSSFRow nextRow = (HSSFRow) iterator.next();
	            Iterator cellIterator = nextRow.cellIterator();
	            
	            if (nextRow.getRowNum()>0) {
	            	CargaMasivaDto cargaMasivaDTO = new CargaMasivaDto();			            
	            	cargaMasivaDTO.setFila(nextRow.getRowNum());
	            	
		            while(cellIterator.hasNext()) {
		            	HSSFCell cell = (HSSFCell) cellIterator.next();
			            String contenidoCelda = formatter.formatCellValue(cell);
			      
			            int numCelda = cell.getColumnIndex();
			            
			            logger.info("celda: " + contenidoCelda+" :: INDEX "+numCelda);
			            
			            caseFila(numCelda, detCargaFila, contenidoCelda, cargaMasivaDTO);
			                   		            
		            }
		            
		            listaDetCarga.add(detCargaFila);
	            	
		            if (cargaMasivaDTO.getErrores().isEmpty()) {
			            cargaMasivaDTO.setProcesar(Boolean.TRUE);
			        } else {
			           	cargaMasivaDTO.setProcesar(Boolean.FALSE);
			        }	
		            listaCargaDTO.add(cargaMasivaDTO);
	            }	            
	        }	            
		} catch (IOException e) {			
			e.printStackTrace();
		} 
		cargaMasiva(listaDetCarga, nombreArchivo);
	}

	private void procesarXlsx(InputStream inputStream, String nombreArchivo) {
		List<CargaMasivaDto> listaCargaDTO = new ArrayList<CargaMasivaDto>();
		
		List<DetCarga> listaDetCarga = new ArrayList<DetCarga>();
		
		try {
	        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		    Sheet firstSheet = workbook.getSheetAt(0);
		    Iterator iterator = firstSheet.iterator();
		        	        
		    DataFormatter formatter = new DataFormatter();
		   
		    while (iterator.hasNext()) {
		    	DetCarga detCargaFila = new DetCarga();
		    	
	        	Row nextRow = (Row) iterator.next();
	            Iterator cellIterator = nextRow.cellIterator();
	            
	            if (nextRow.getRowNum()>0) {
	            	CargaMasivaDto cargaMasivaDTO = new CargaMasivaDto();			            
	            	cargaMasivaDTO.setFila(nextRow.getRowNum());
	            	
		            while(cellIterator.hasNext()) {
			           	Cell cell = (Cell) cellIterator.next();
			            String contenidoCelda = formatter.formatCellValue(cell);
			      
			            int numCelda = cell.getColumnIndex();
			            
			            logger.info("celda: " + contenidoCelda+" :: INDEX "+numCelda);
			            
			            caseFila(numCelda, detCargaFila, contenidoCelda, cargaMasivaDTO);
			                   		            
		            }
		            
		            listaDetCarga.add(detCargaFila);
	            	
		            if (cargaMasivaDTO.getErrores().isEmpty()) {
			            cargaMasivaDTO.setProcesar(Boolean.TRUE);
			        } else {
			           	cargaMasivaDTO.setProcesar(Boolean.FALSE);
			        }	
		            listaCargaDTO.add(cargaMasivaDTO);
	            }	            
	        }	            
		} catch (IOException e) {			
			e.printStackTrace();
		} 
		cargaMasiva(listaDetCarga, nombreArchivo);	
	}
	
	private void cargaMasiva(List<DetCarga> listaDetCarga, String nombreArchivo) {
		
		AuxCarga cargarAuxBD = cargarAuxBD(nombreArchivo);
		auxCargaRepository.save(cargarAuxBD);
		
		for (DetCarga detCarga : listaDetCarga) {
			detCarga.setIdCargaMasiva(cargarAuxBD.getIdCargaMasiva());
			detCargaRepository.save(detCarga);
		}
		
	}

	private AuxCarga cargarAuxBD(String nombreArchivo) {
		AuxCarga auxCargaBD = new AuxCarga();
		
		auxCargaBD.setFechaAlta(new Date());
		auxCargaBD.setNombreArchivo(nombreArchivo);	
		
		return auxCargaBD;
	}

	private void caseFila (int numColumna, DetCarga detCargaFila, String contenidoCelda, CargaMasivaDto cargaMasivaDTO) {

			try {
				switch (numColumna) {
				case 0:
					detCargaFila.setName(contenidoCelda);
					break;
				case 1:
					detCargaFila.setSurname(contenidoCelda);
					break;
				case 2:
					detCargaFila.setSurname2(contenidoCelda);
					break;
				case 3:
					detCargaFila.setEmail(contenidoCelda);
					break;
				case 4:
					detCargaFila.setPlate(contenidoCelda);
					break;
				case 5:
					detCargaFila.setPhoneNumber(contenidoCelda);
					break;
				case 6:
					detCargaFila.setDocumentNumber(contenidoCelda);
					break;
				case 7:
					detCargaFila.setAmount(Double.valueOf(contenidoCelda));
					break;
				default:
					break;
		        }
			} catch (NumberFormatException e) {
				logger.error(" No es un valor tipo numero :: ");
				StringBuilder errorStr = new StringBuilder();
				errorStr.append("ERROR al formatear numero en la fila ")
					.append(cargaMasivaDTO.getFila().intValue()+1)
					.append(", columna ").append(numColumna+1);
				cargaMasivaDTO.getErrores().add(errorStr.toString());
			} catch (Exception e) {
				logger.error(" Error generico :: ");
				StringBuilder errorStr = new StringBuilder();
				errorStr.append("Se presentó un error inesperado. Por favor valide los datos ")
					.append(cargaMasivaDTO.getFila().intValue()+1)
					.append(", columna ").append(numColumna+1);
				cargaMasivaDTO.getErrores().add(errorStr.toString());
			}	
		}
}
