package com.aura.admin.adminqamm.service;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dao.CargaColombiaDao;
import com.aura.admin.adminqamm.dto.CargaMasivaDto;
import com.aura.admin.adminqamm.dto.CargaMasivaUserDto;
import com.aura.admin.adminqamm.dto.ClienteDto;
import com.aura.admin.adminqamm.dto.ColaboradorDto;
import com.aura.admin.adminqamm.dto.ResponseCargaColombiaDto;
import com.aura.admin.adminqamm.dto.ResponseCargaNequiDto;
import com.aura.admin.adminqamm.dto.UsuarioDto;
import com.aura.admin.adminqamm.dto.request.CargaRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.AuxCarga;
import com.aura.admin.adminqamm.model.AuxCargaNequi;
import com.aura.admin.adminqamm.model.Cliente;
import com.aura.admin.adminqamm.model.DetCarga;
import com.aura.admin.adminqamm.model.DetCargaNequi;
import com.aura.admin.adminqamm.repository.AuxCargaNequiRepository;
import com.aura.admin.adminqamm.repository.AuxCargaRepository;
import com.aura.admin.adminqamm.repository.ClientRepository;
import com.aura.admin.adminqamm.repository.DetCargaNequiRepository;
import com.aura.admin.adminqamm.repository.DetCargaRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;

@Service
public class CargaColombiaService {
	
	Logger logger = LoggerFactory.getLogger(CargaColombiaService.class);
	
	@Autowired
	private AuxCargaRepository auxCargaRepository;
	
	@Autowired
	private AuxCargaNequiRepository auxCargaNequiRepository;
	
	@Autowired
	private DetCargaRepository detCargaRepository;
	
	@Autowired
	private DetCargaNequiRepository detCargaNequiRepository;
	
	@Autowired
	private CargaColombiaDao cargaColombiaDao;
	
	@Autowired
	private ClientRepository clienteRepository;
	
	@Autowired
    private PermissionService permissionService;
	
	private static final String RFC_CARGA_COLOMBIA = "AAA010101AA1";
	
	private static final String RFC_CARGA_NEQUI = "NIT";
	
	private static final String SITUACION_CARGA_D = "D";
	
	private static final String SITUACION_CARGA_N = "N";
	
	private static final String SITUACION_CARGA_E = "E";
	
	private static final Integer[] SITUACION_CARGA_OK = {1, 2};
	
	private static final Integer[] SITUACION_CARGA_ERR = {-1, -2, -3};
	
	
	
	public ResponseCargaColombiaDto insertCargaColombia(int loggedIdUser, CargaRequestDto cargaColombia) throws BusinessException {
		
		ResponseCargaColombiaDto responseCargaColombiaDto = null;
		if (logger.isInfoEnabled()) {
			logger.info("/**** Procesa archivo carga masiva colombia  ****/" + cargaColombia.getArchivo().getOriginalFilename());
		}
		
		try {
			if (cargaColombia.getArchivo().getInputStream()==null) {
				throw new BusinessException("Archivo vacio", 401);
			}
		
			String nombreArchivo = cargaColombia.getArchivo().getOriginalFilename();
			
			String hashName = getBytesOfMd5(cargaColombia.getArchivo().getInputStream());
			
			if (auxCargaRepository.findFileByHash(hashName)!= null) {
				logger.info("/**El archivo: "+cargaColombia.getArchivo().getOriginalFilename()+" ya existe.**/");
				throw new BusinessException("El archivo ya se ha procesado previamente :: ", 406);
				
			}
			
			if (nombreArchivo.endsWith(".xlsx")) {
				responseCargaColombiaDto = procesarXlsx(cargaColombia.getArchivo().getInputStream(), nombreArchivo, hashName);
			} else if (nombreArchivo.endsWith(".xls")) {
				responseCargaColombiaDto = procesarXls(cargaColombia.getArchivo().getInputStream(), nombreArchivo, hashName);
			} else {
				throw new BusinessException("Error formato incorrecto.", 406);
			}
		} catch (IOException | NoSuchAlgorithmException e) {
			logger.error("ERROR al leer el archivo para la carga masiva colombia :: ",e.getMessage());
		}
		
		logger.info("/**** Informacion cargada correctamente  ****/");
		return responseCargaColombiaDto;
	}
	
	
	public ResponseCargaNequiDto insertCargaNequi(int loggedIdUser, CargaRequestDto cargaColombia) throws BusinessException {
		
		ResponseCargaNequiDto responseCargaNequiDto = null;
		if (logger.isInfoEnabled()) {
			logger.info("/**** Procesa archivo carga masiva nequi  ****/" + cargaColombia.getArchivo().getOriginalFilename());
		}
		
		try {
			if (cargaColombia.getArchivo().getInputStream()==null) {
				throw new BusinessException("Archivo vacio", 401);
			}
		
			String nombreArchivo = cargaColombia.getArchivo().getOriginalFilename();
			
			String hashName = getBytesOfMd5(cargaColombia.getArchivo().getInputStream());
			
			if (auxCargaNequiRepository.findFileByHash(hashName)!= null) {
				logger.info("/**El archivo: "+cargaColombia.getArchivo().getOriginalFilename()+" ya existe.**/");
				throw new BusinessException("El archivo ya se ha procesado previamente :: ", 406);
				
			}
			
			if (nombreArchivo.endsWith(".xlsx")) {
				responseCargaNequiDto = procesarXlsxUser(cargaColombia.getArchivo().getInputStream(), nombreArchivo, hashName);
			} else if (nombreArchivo.endsWith(".xls")) {
				responseCargaNequiDto = procesarXlsUser(cargaColombia.getArchivo().getInputStream(), nombreArchivo, hashName);
			} else {
				throw new BusinessException("Error formato incorrecto.", 406);
			}
		} catch (IOException | NoSuchAlgorithmException e) {
			logger.error("ERROR al leer el archivo para la carga masiva colombia :: ",e.getMessage());
		}
		
		logger.info("/**** Informacion cargada correctamente  ****/");
		return responseCargaNequiDto;
	}

	public List<ColaboradorDto> obetenerResgistrosProcesados(Integer idCargaMasiva) throws BusinessException {
		List<ColaboradorDto> colaboradores = new ArrayList<ColaboradorDto>();
	    
		if(idCargaMasiva==null) {
			throw new BusinessException("El id de la carga es requerido", 401);
		}
		
	    Cliente clienteColombia = clienteRepository.getByRfc(RFC_CARGA_COLOMBIA);
	    List<DetCarga> detCargaList = detCargaRepository.getByIdCargaMasiva(idCargaMasiva);
	    
	    for (DetCarga detCargaItem : detCargaList) {
		    	ColaboradorDto colaboradorRes = new ColaboradorDto();
		    	ClienteDto clienteRes = new ClienteDto();
		      
		    	colaboradorRes.setNombre(detCargaItem.getName());
		    	colaboradorRes.setApellidoPat(detCargaItem.getSurname());
		    	colaboradorRes.setApellidoMat(detCargaItem.getSurname2());
		    	colaboradorRes.setDescError(detCargaItem.getDescError());
		    	logger.info("/**** Descripcion :: "+detCargaItem.getDescError());
		    	
		    	colaboradorRes.setNumeroDocumento(detCargaItem.getDocumentNumber());
		    	clienteRes.setRazon(clienteColombia.getRazon());
		    	colaboradorRes.setClienteDto(clienteRes);
		      
		    	colaboradores.add(colaboradorRes);
		}
		   
		return colaboradores;
	}
	
	public List<UsuarioDto> obetenerResgistrosNequiProcesados(Integer idCargaMasiva) throws BusinessException {
		List<UsuarioDto> colaboradores = new ArrayList<UsuarioDto>();
	    
		if(idCargaMasiva==null) {
			throw new BusinessException("El id de la carga es requerido", 401);
		}
		
	    //Cliente clienteColombia = clienteRepository.getByRfc(RFC_CARGA_COLOMBIA);
	    List<DetCargaNequi> detCargaList = detCargaNequiRepository.getByIdCargaMasiva(idCargaMasiva);
	    
	    for (DetCargaNequi detCargaItem : detCargaList) {
		    	UsuarioDto colaboradorRes = new UsuarioDto();
		      
		    	colaboradorRes.setNombre(detCargaItem.getNombre());
		    	colaboradorRes.setPrimerApellido(detCargaItem.getPrimerApellido());
		    	colaboradorRes.setSegundoApellido(detCargaItem.getSegundoApellido());
		    	colaboradorRes.setTipoDocumentoId(detCargaItem.getTipoDocumentoId());
		    	colaboradorRes.setCelular(detCargaItem.getCelular());
		    	//colaboradorRes.setCuentaNequi(detCargaItem.getCuentaNequi());
		    	colaboradorRes.setNumeroDocumentoId(detCargaItem.getNumeroDocumentoId());
		    	switch (detCargaItem.getIdEstatusCarga()) {
		    	case -3: colaboradorRes.setObservacioCarga("El Documento de Identificación ya se encuentra registrado como clabeBancaria");
		    		break;
		    	case -2: colaboradorRes.setObservacioCarga("El número telefónico no cumple con el formato");
		    		break;
		    	case -1: colaboradorRes.setObservacioCarga("El trabajador ya se encuentra registrado");
		    		break;
		    	case 1: colaboradorRes.setObservacioCarga("Registro nuevo");
		    		break;
		    	case 2: colaboradorRes.setObservacioCarga("Registro cargado exitosamente ");
		    		break;
		    	default:
					break;
		    	}
		    	logger.info("/**** Descripcion :: "+detCargaItem.getObservacionCarga());
		    	colaboradores.add(colaboradorRes);
		}
		   
		return colaboradores;
	}
	
	private ResponseCargaColombiaDto procesarXls(InputStream inputStream, String nombreArchivo, String hashName) {
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
			            
			            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			    		String createAt = sdf.format(new Date());
			    		detCargaFila.setCreatedAt(createAt);
			                   		            
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
		
		return cargaMasiva(listaDetCarga, nombreArchivo, hashName);
	}


	private ResponseCargaColombiaDto procesarXlsx(InputStream inputStream, String nombreArchivo, String hashName) {
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
			            
			            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			    		String createAt = sdf.format(new Date());
			    		detCargaFila.setCreatedAt(createAt);
			                   		            
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
		
		return cargaMasiva(listaDetCarga, nombreArchivo, hashName);	
	}
	
	private ResponseCargaNequiDto procesarXlsxUser(InputStream inputStream, String nombreArchivo, String hashName) {
		List<CargaMasivaUserDto> listaCargaDTO = new ArrayList<CargaMasivaUserDto>();
		
		List<DetCargaNequi> listaDetCarga = new ArrayList<DetCargaNequi>();
		
		try {
	        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		    Sheet firstSheet = workbook.getSheetAt(0);
		    Iterator iterator = firstSheet.iterator();
		        	        
		    DataFormatter formatter = new DataFormatter();
		   
		    while (iterator.hasNext()) {
		    	
		    	DetCargaNequi detCargaFila = new DetCargaNequi();
		    	
	        	Row nextRow = (Row) iterator.next();
	            Iterator cellIterator = nextRow.cellIterator();
	            
	            if (nextRow.getRowNum()>0) {
	            	CargaMasivaUserDto cargaMasivaUserDTO = new CargaMasivaUserDto();			            
	            	cargaMasivaUserDTO.setFila(nextRow.getRowNum());
	            	
		            while(cellIterator.hasNext()) {
			           	Cell cell = (Cell) cellIterator.next();
			            String contenidoCelda = formatter.formatCellValue(cell);
			      
			            int numCelda = cell.getColumnIndex();
			            
			            logger.info("celda: " + contenidoCelda+" :: INDEX "+numCelda);
			            
			            caseFilaUser(numCelda, detCargaFila, contenidoCelda, cargaMasivaUserDTO);
			            
			                   		            
		            }
		            
		            listaDetCarga.add(detCargaFila);
	            	
		            if (cargaMasivaUserDTO.getErrores().isEmpty()) {
		            	cargaMasivaUserDTO.setProcesar(Boolean.TRUE);
			        } else {
			        	cargaMasivaUserDTO.setProcesar(Boolean.FALSE);
			        }	
		            listaCargaDTO.add(cargaMasivaUserDTO);
	            }	            
	        }	            
		} catch (IOException e) {			
			e.printStackTrace();
		} 
		
		return cargaMasivaNequi(listaDetCarga, nombreArchivo, hashName);
	}
	
	
	private ResponseCargaNequiDto procesarXlsUser(InputStream inputStream, String nombreArchivo, String hashName) {
		List<CargaMasivaUserDto> listaCargaDTO = new ArrayList<CargaMasivaUserDto>();
		
		List<DetCargaNequi> listaDetCarga = new ArrayList<DetCargaNequi>();
		
		
		try {
	        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		    Sheet firstSheet = workbook.getSheetAt(0);
		    Iterator iterator = firstSheet.iterator();
		        	        
		    DataFormatter formatter = new DataFormatter();
		   
		    while (iterator.hasNext()) {
		    	
		    	DetCargaNequi detCargaFila = new DetCargaNequi();
		    	
	        	Row nextRow = (Row) iterator.next();
	            Iterator cellIterator = nextRow.cellIterator();
	            
	            if (nextRow.getRowNum()>0) {
	            	CargaMasivaUserDto cargaMasivaUserDTO = new CargaMasivaUserDto();			            
	            	cargaMasivaUserDTO.setFila(nextRow.getRowNum());
	            	
		            while(cellIterator.hasNext()) {
			           	Cell cell = (Cell) cellIterator.next();
			            String contenidoCelda = formatter.formatCellValue(cell);
			      
			            int numCelda = cell.getColumnIndex();
			            
			            logger.info("celda: " + contenidoCelda+" :: INDEX "+numCelda);
			            
			            caseFilaUser(numCelda, detCargaFila, contenidoCelda, cargaMasivaUserDTO);
			            
			                   		            
		            }
		            
		            listaDetCarga.add(detCargaFila);
		            
		            if (cargaMasivaUserDTO.getErrores().isEmpty()) {
		            	cargaMasivaUserDTO.setProcesar(Boolean.TRUE);
			        } else {
			        	cargaMasivaUserDTO.setProcesar(Boolean.FALSE);
			        }
		            listaCargaDTO.add(cargaMasivaUserDTO);
	            }	            
	        }	            
		} catch (IOException e) {			
			e.printStackTrace();
		} 
		
		return cargaMasivaNequi(listaDetCarga, nombreArchivo, hashName);
	}
	
	
	private ResponseCargaColombiaDto cargaMasiva(List<DetCarga> listaDetCarga, String nombreArchivo, String hashName ) {
	    
	    AuxCarga cargarAuxBD = cargarAuxBD(nombreArchivo, hashName);
	    auxCargaRepository.save(cargarAuxBD);
	    logger.info("/**** Persistio AuxCarga ****/");
	    for (DetCarga detCarga : listaDetCarga) {
	      logger.info("/**** Carga masiva item :: "+detCarga.getName()+" :: "+cargarAuxBD.getIdCargaMasiva());
//	      DetCargaId detCargaId = new DetCargaId();
	      
//	      detCargaId.setIdCargaMasiva(cargarAuxBD.getIdCargaMasiva());
	      detCarga.setIdCargaMasiva(cargarAuxBD.getIdCargaMasiva());
//	      detCarga.setDetCargaId(detCargaId);
	      
	      detCargaRepository.save(detCarga);
	      logger.info("/**** Persistio DetCarga ****/");
	    }
	    
	    detCargaRepository.callFuntionAguila(cargarAuxBD.getIdCargaMasiva(),RFC_CARGA_COLOMBIA);
	    
	    return generaResponse(cargarAuxBD.getIdCargaMasiva());
	  }
	
	private ResponseCargaNequiDto cargaMasivaNequi(List<DetCargaNequi> listaDetCarga, String nombreArchivo, String hashName ) {
	    
	    AuxCargaNequi cargarAuxBD = cargarAuxNequiBD(nombreArchivo, hashName);
	    auxCargaNequiRepository.save(cargarAuxBD);
	    logger.info("/**** Persistio AuxCarga ****/");
	    for (DetCargaNequi detCarga : listaDetCarga) {
	      logger.info("/**** Carga masiva item :: "+detCarga.getNombre()+" :: "+cargarAuxBD.getIdCargaMasiva());
	      detCarga.setCuentaNequi("1");
	      detCarga.setIdCargaMasiva(cargarAuxBD.getIdCargaMasiva());
	      
	      detCargaNequiRepository.save(detCarga);
	      logger.info("/**** Persistio DetCarga ****/");
	    }
	    
	    
	    String rfcCargaNequi = detCargaNequiRepository.getNit(cargarAuxBD.getIdCargaMasiva());
	    logger.info("RFC CARGA NEqui: " + rfcCargaNequi);
	    detCargaNequiRepository.callFunctionNequi(cargarAuxBD.getIdCargaMasiva(),rfcCargaNequi);
	    
	    return generaResponseNequi(cargarAuxBD.getIdCargaMasiva());
	  }

	  private ResponseCargaColombiaDto generaResponse(Integer idCargaMasiva) {
	    
	    ResponseCargaColombiaDto responseCarga = new ResponseCargaColombiaDto();
	    
	    Integer exitosos = detCargaRepository.countSituacionExito(idCargaMasiva, SITUACION_CARGA_D, SITUACION_CARGA_N);
	      
	    Integer fallidos = detCargaRepository.countSituacionError(idCargaMasiva, SITUACION_CARGA_E);
	   
	    responseCarga.setExitosos(exitosos);
	    responseCarga.setFallidos(fallidos);
	    responseCarga.setProcesados(exitosos+fallidos);
	    responseCarga.setIdCargaMasiva(idCargaMasiva);
	    
	    logger.info("/**** Exitosos :: "+responseCarga.getExitosos());
	    logger.info("/**** Fallidos :: "+responseCarga.getFallidos());
	  
	    return responseCarga;
	  }
	  
	  private ResponseCargaNequiDto generaResponseNequi(Integer idCargaMasiva) {
		    
		  	ResponseCargaNequiDto responseCarga = new ResponseCargaNequiDto();
		    
		    Integer exitosos = detCargaNequiRepository.countSituacionExito(idCargaMasiva, SITUACION_CARGA_OK[0],SITUACION_CARGA_OK[1]);
		      
		    Integer fallidos = detCargaNequiRepository.countSituacionError(idCargaMasiva, SITUACION_CARGA_ERR[0], SITUACION_CARGA_ERR[1], SITUACION_CARGA_ERR[2]);
		   
		    responseCarga.setExitosos(exitosos);
		    responseCarga.setFallidos(fallidos);
		    responseCarga.setProcesados(exitosos+fallidos);
		    responseCarga.setIdCargaMasiva(idCargaMasiva);
		    
		    logger.info("/**** Exitosos :: "+responseCarga.getExitosos());
		    logger.info("/**** Fallidos :: "+responseCarga.getFallidos());
		  
		    return responseCarga;
	  }

	  private AuxCarga cargarAuxBD(String nombreArchivo, String hashName) {
		AuxCarga auxCargaBD = new AuxCarga();
		
		auxCargaBD.setFechaAlta(new Date());
		auxCargaBD.setNombreArchivo(nombreArchivo);
		auxCargaBD.setHash(hashName);
			
		logger.info("/**** Cargo datos auxiliar ****/");
		return auxCargaBD;
			
	}
	
	private AuxCargaNequi cargarAuxNequiBD(String nombreArchivo, String hashName) {
		AuxCargaNequi auxCargaBD = new AuxCargaNequi();
		
		auxCargaBD.setFechaAlta(new Date());
		auxCargaBD.setNombreArchivo(nombreArchivo);
		auxCargaBD.setHash(hashName);
			
		logger.info("/**** Cargo datos auxiliar ****/");
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
				errorStr.append("Se present� un error inesperado. Por favor valide los datos ")
					.append(cargaMasivaDTO.getFila().intValue()+1)
					.append(", columna ").append(numColumna+1);
				cargaMasivaDTO.getErrores().add(errorStr.toString());
			}	
	}
	
	
	private void caseFilaUser (int numColumna, DetCargaNequi detCargaFila, String contenidoCelda, CargaMasivaUserDto cargaMasivaUserDTO) {
		
		
		try {
			switch (numColumna) {
			case 0:
				detCargaFila.setNit(contenidoCelda);
				break;
			case 1:
				detCargaFila.setNombre(removeAccentAndNumbers(contenidoCelda));
				break;
			case 2:
				detCargaFila.setPrimerApellido(removeAccentAndNumbers(contenidoCelda));
				break;
			case 3:
				detCargaFila.setSegundoApellido(removeAccentAndNumbers(contenidoCelda));
				break;
			case 4:
				detCargaFila.setTipoDocumentoId(getValue(contenidoCelda));
				break;
			case 5:
				detCargaFila.setNumeroDocumentoId(contenidoCelda);
				break;
			case 6:
				if (contenidoCelda.charAt(0) != '+') {
					contenidoCelda = '+' + contenidoCelda;
				}
				detCargaFila.setCelular(contenidoCelda);
				break;
			case 7:
				detCargaFila.setEmail(contenidoCelda);
				break;
			/*case 8:
				detCargaFila.setCuentaNequi(contenidoCelda);
				break;*/
			case 8:
				SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yy");
				SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy");
				Date fechaDate = inputFormat.parse(contenidoCelda);
				String fechaN = outputFormat.format(fechaDate); 
				Date auxDate = outputFormat.parse(fechaN);
				detCargaFila.setFechaNacimiento(auxDate);
				break;
			case 9:
				detCargaFila.setGenero(contenidoCelda);
				break;
			case 10:
				detCargaFila.setPeriodicidad(String.valueOf(contenidoCelda.charAt(0)));
				break;
			case 11:
				String salario = contenidoCelda.replaceAll("[,]", "");
				detCargaFila.setSalarioDiario(Double.valueOf(salario));
				break;
			case 12:
				detCargaFila.setAreaPosicion(removeAccentAndNumbers(contenidoCelda));
				break;
			case 13:
				detCargaFila.setTipoContrato(removeAccentAndNumbers(contenidoCelda));
				break;
			case 14:
				detCargaFila.setCEstado(Integer.valueOf(getValue(contenidoCelda)));
				break;
			case 15:
				
				detCargaFila.setIdBancoNequi(Integer.valueOf(getValue(contenidoCelda)));
				break;
			case 16:
				detCargaFila.setIdTipoCuentaBanco(Integer.valueOf(getValue(contenidoCelda)));
			case 17:
				detCargaFila.setNumCuentaBanco(contenidoCelda);
				break;
			
			default:
				break;
	        }
		} catch (NumberFormatException e) {
			logger.error(" No es un valor tipo numero :: ");
			StringBuilder errorStr = new StringBuilder();
			errorStr.append("ERROR al formatear numero en la fila ")
				.append(cargaMasivaUserDTO.getFila().intValue()+1)
				.append(", columna ").append(numColumna+1);
			cargaMasivaUserDTO.getErrores().add(errorStr.toString());
		} catch (Exception e) {
			logger.error(" Error generico :: ");
			StringBuilder errorStr = new StringBuilder();
			errorStr.append("Se present� un error inesperado. Por favor valide los datos ")
				.append(cargaMasivaUserDTO.getFila().intValue()+1)
				.append(", columna ").append(numColumna+1);
			cargaMasivaUserDTO.getErrores().add(errorStr.toString());
		}

	}
	
	
	public static String removeAccentAndNumbers(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String withoutAccents = pattern.matcher(normalized).replaceAll("").replaceAll("[^\\p{ASCII}]", "");
        String upper = withoutAccents.replaceAll("[^a-zA-Z\\s]", "");
        return upper.toUpperCase();
    }
	
	public static String getValue(String cadena) {
        int indiceInicio = cadena.indexOf('(');
        int indiceFin = cadena.indexOf(')', indiceInicio + 1);

        return (indiceInicio != -1 && indiceFin != -1) ? cadena.substring(indiceInicio + 1, indiceFin) : null;
    }
	
	public static String getBytesOfMd5(InputStream is) throws IOException, NoSuchAlgorithmException {
		

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = is.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        is.close();
        byte[] hashbyte = complete.digest();
		StringBuilder sb = new StringBuilder();
		for (byte b : hashbyte)
		  sb.append(String.format("%02x", b & 0xFF));
		String hexHash = sb.toString();
        return hexHash;
            
	    
	}
	
	public List<DetCarga> getUsers(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<DetCarga> usuarios = detCargaRepository.findAll();
        

        return usuarios;
    
	}
}
