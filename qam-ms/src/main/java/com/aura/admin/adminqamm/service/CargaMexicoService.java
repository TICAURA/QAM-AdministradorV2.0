package com.aura.admin.adminqamm.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
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

@Service
public class CargaMexicoService {
	
	Logger logger = LoggerFactory.getLogger(CargaMexicoService.class);
	
	@Autowired
	private ColaboradorService colaboradorService;

	public void insertCargaMexico(int loggedIdUser, CargaRequestDto cargaMexico) throws BusinessException {
		
		if (logger.isInfoEnabled()) {
			logger.info("/**** Procesa archivo carga masiva mexico  ****/" + cargaMexico.getArchivo().getName());
		}
		
		try {
			if (cargaMexico.getArchivo().getInputStream()==null) {
				throw new BusinessException("Archivo vacio", 401);
			}
		
			String nombreArchivo = cargaMexico.getArchivo().getName();
			
			if (nombreArchivo.endsWith(".xlsx")) {
				procesarXlsx(cargaMexico.getArchivo().getInputStream());
			} else if (nombreArchivo.endsWith(".xls")) {
				procesarXls(cargaMexico.getArchivo().getInputStream());
			} else {
				throw new BusinessException("Error formato incorrecto.", 401);
			}
		} catch (IOException e) {
			logger.error("ERROR al leer el archivo para la carga masiva mexico :: ",e.getMessage());
		}
	}

	private void procesarXls(InputStream inputStream) {
		List<ColaboradorDto> colaboradorDTO = new ArrayList<ColaboradorDto>();
		List<CargaMasivaDto> listaCargaDTO = new ArrayList<CargaMasivaDto>();
		
		try {
	        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		    Sheet firstSheet = workbook.getSheetAt(0);
		    Iterator iterator = firstSheet.iterator();
		        	        
		    DataFormatter formatter = new DataFormatter();
		    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		    while (iterator.hasNext()) {
		    	ColaboradorDto colaboradorFila = new ColaboradorDto();
		    	ClienteDto clienteFila = new ClienteDto();
		    	PersonaDto personaFila = new PersonaDto();
		    	DatosContactoDto contactoFila = new DatosContactoDto();		    	
		    	CuentaBancoDto cuentaBanco = new CuentaBancoDto();		    	
		    	
	        	Row nextRow = (Row) iterator.next();
	            Iterator cellIterator = nextRow.cellIterator();
	            
	            if (nextRow.getRowNum()>0) {
	            	CargaMasivaDto cargaMasivaDTO = new CargaMasivaDto();			            
	            	cargaMasivaDTO.setFila(nextRow.getRowNum());
	            	
		            while(cellIterator.hasNext()) {
			           	Cell cell = (Cell) cellIterator.next();
			            String contenidoCelda = formatter.formatCellValue(cell);
			      
			            int numCelda = cell.getColumnIndex();
			            if (numCelda==16 || numCelda==17 || numCelda==18 || numCelda==24) {
			            	contenidoCelda = formatter.formatCellValue(cell, evaluator);
			            }
			            
			            logger.info("celda: " + contenidoCelda+" :: INDEX "+numCelda);
			            
			            caseFila(numCelda, clienteFila, personaFila, contactoFila, colaboradorFila, contenidoCelda, cuentaBanco, cargaMasivaDTO);
			            
			                   		            
		            }
		            List<DatosContactoDto> contactosFila = new ArrayList<DatosContactoDto>();
		            contactosFila.add(contactoFila);
		            
		            colaboradorFila.setPersonaDto(personaFila);
		            colaboradorFila.setCuentaBancoDto(cuentaBanco);
		            colaboradorFila.setClienteDto(clienteFila);
		            colaboradorFila.setDatosContactoDto(contactosFila);
		            
		            cargaMasivaDTO.setColaboradorDTO(colaboradorFila);
	            	
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
		cargaMasiva(listaCargaDTO);	
	}

	private void cargaMasiva(List<CargaMasivaDto> listaCargaDTO) {
		colaboradorService.cargaMasiva(listaCargaDTO);
	}

	private void procesarXlsx(InputStream inputStream) {
		// TODO Auto-generated method stub
		
	}
	
	private void caseFila (int numColumna, ClienteDto clienteFila, PersonaDto personaFila, 
			DatosContactoDto contactoFila, ColaboradorDto colaboradorFila,String contenidoCelda,
			CuentaBancoDto cuentaBanco, CargaMasivaDto cargaMasivaDTO) {
		
			SimpleDateFormat dateFromat = new SimpleDateFormat("dd-MM-yy");
			
			try {
				switch (numColumna) {
				case 0:
					clienteFila.setRazon(contenidoCelda);
					break;
				case 1:
					clienteFila.setRfc(contenidoCelda);
					break;
				case 2:
					clienteFila.setRegistroPatronal(contenidoCelda);
					break;
				case 3:
					clienteFila.setSubRazonSocial(contenidoCelda);
					break;
				case 4:
					clienteFila.setSubRfc(contenidoCelda);
					break;
				case 5:
					validaLongitud(contenidoCelda, 9, cargaMasivaDTO, numColumna);
					colaboradorFila.setClaveColaborador(Long.valueOf(contenidoCelda));
					break;
				case 6:
					validaLongitud(contenidoCelda, 48, cargaMasivaDTO, numColumna);
					personaFila.setApellidoPat(contenidoCelda);
					break;
				case 7:
					validaLongitud(contenidoCelda, 48, cargaMasivaDTO, numColumna);
					personaFila.setApellidoMat(contenidoCelda);
					break;
				case 8:
					validaLongitud(contenidoCelda, 32, cargaMasivaDTO, numColumna);
					personaFila.setNombre(contenidoCelda);
					break;
				case 9:
					validaLongitud(contenidoCelda, 13, cargaMasivaDTO, numColumna);
					personaFila.setRfc(contenidoCelda);
					break;
				case 10:
					validaLongitud(contenidoCelda, 11, cargaMasivaDTO, numColumna);
					personaFila.setNss(contenidoCelda);
					break;
				case 11:
					validaLongitud(contenidoCelda, 18, cargaMasivaDTO, numColumna);
					personaFila.setCurp(contenidoCelda);
					break;
				case 12:															
					Date fechaIngreso = dateFromat.parse(contenidoCelda);
					colaboradorFila.setFechaIngreso(fechaIngreso);	
					break;
				case 13:
					validaLongitud(contenidoCelda, 60, cargaMasivaDTO, numColumna);
					contactoFila.setNumClave(contenidoCelda);
					contactoFila.setIdTctk(Integer.valueOf(1));
					contactoFila.setEsCelularEnrolamiento(Boolean.TRUE);
					break;
				case 14:
					validaLongitud(contenidoCelda, 90, cargaMasivaDTO, numColumna);
					personaFila.setEmailCorporativo(contenidoCelda);
					break;
				case 15:
					validaLongitud(contenidoCelda, 13, cargaMasivaDTO, numColumna);
					colaboradorFila.setSalarioDiarioReal(Double.valueOf(contenidoCelda));
					break;
				case 16:
					validaLongitud(contenidoCelda, 13, cargaMasivaDTO, numColumna);
					colaboradorFila.setSalarioNetoPeriodo(Double.valueOf(contenidoCelda));
					break;
				case 17:
					validaLongitud(contenidoCelda, 13, cargaMasivaDTO, numColumna);
					colaboradorFila.setSalarioDiarioIntegrado(Double.valueOf(contenidoCelda));
					break;
				case 18:
					validaLongitud(contenidoCelda, 13, cargaMasivaDTO, numColumna);
					colaboradorFila.setDistribucionImssComplemento(Double.valueOf(contenidoCelda));
					break;
				case 19:
					validaLongitud(contenidoCelda, 10, cargaMasivaDTO, numColumna);
					cuentaBanco.setCuenta(contenidoCelda);
					break;
				case 20:
					validaLongitud(contenidoCelda, 18, cargaMasivaDTO, numColumna);
					cuentaBanco.setClabe(contenidoCelda);
					break;
				case 21:
					
					break;
				case 22:
					validaLongitud(contenidoCelda, 64, cargaMasivaDTO, numColumna);
					cuentaBanco.setDescripcionBanco(contenidoCelda);			
					break;
				case 23:
					validaLongitud(contenidoCelda, 1, cargaMasivaDTO, numColumna);
					colaboradorFila.setPeriodicidad(contenidoCelda);
					break;
				case 24:
					Date fechaAlta = dateFromat.parse(contenidoCelda);
					cuentaBanco.setFechaAlta(fechaAlta);
					personaFila.setFchAltaQuincenam(fechaAlta);
					break;
				case 25:
					validaLongitud(contenidoCelda, 1, cargaMasivaDTO, numColumna);
					personaFila.setGenero(contenidoCelda);
					break;
				case 26:
					Date fechaNacimiento = dateFromat.parse(contenidoCelda);
					personaFila.setFechaDeNacimiento(fechaNacimiento);
					break;
				default:
					break;
		        }	
				
			} catch (ParseException e) {
				logger.error(" No es un valor tipo fecha :: ");
				StringBuilder errorStr = new StringBuilder();
				errorStr.append("ERROR al formatear fecha en la fila ")
					.append(cargaMasivaDTO.getFila().intValue()+1)
					.append(", columna ").append(numColumna+1);
				cargaMasivaDTO.getErrores().add(errorStr.toString());
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
				errorStr.append("Se presento un error inesperado. Por favor valide los datos ")
					.append(cargaMasivaDTO.getFila().intValue()+1)
					.append(", columna ").append(numColumna+1);
				cargaMasivaDTO.getErrores().add(errorStr.toString());
			}	
		}
	
	private void validaLongitud(String contenidoCelda, int longitud, CargaMasivaDto cargaMasivaDTO, int numColumna) {
		if (contenidoCelda.length()>longitud) {
			cargaMasivaDTO.getErrores().add(mensajeGenerico(cargaMasivaDTO.getFila().intValue(), numColumna));
		}	
	}
	
	private String mensajeGenerico(int fila, int columna) {
		StringBuilder errorStr = new StringBuilder();
		errorStr.append("Se presento un error inesperado. Por favor valide los datos de la fila ")
			.append(fila+1)
			.append(", columna ").append(columna+1);
		
		return errorStr.toString();
	}

}
