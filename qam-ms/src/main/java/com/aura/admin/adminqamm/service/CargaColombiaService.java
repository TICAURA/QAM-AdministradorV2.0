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
public class CargaColombiaService {
	
	Logger logger = LoggerFactory.getLogger(CargaColombiaService.class);
	
	/*@Autowired
	private AuxCargaRepository auxCargaRepository;
	@Autowired
	private DetCargaRepository detCargaRepository;*/
	
	public void insertCargaColombia(int loggedIdUser, CargaRequestDto cargaColombia) throws BusinessException {
		
		
		if (logger.isInfoEnabled()) {
			logger.info("/**** Procesa archivo carga masiva colombia  ****/" + cargaColombia.getArchivo().getName());
		}
		
		try {
			if (cargaColombia.getArchivo().getInputStream()==null) {
				throw new BusinessException("Archivo vacio", 401);
			}
		
			String nombreArchivo = cargaColombia.getArchivo().getName();
			
			logger.info(nombreArchivo);
			if (nombreArchivo.endsWith(".xlsx")) {
				logger.info("Validacion correcta");
			} else if (nombreArchivo.endsWith(".xls")) {
				logger.info("Validacion correcta");
			} else {
				throw new BusinessException("Error formato incorrecto.", 401);
			}
		} catch (IOException e) {
			logger.error("ERROR al leer el archivo para la carga masiva mexico :: ",e.getMessage());
		}
	}
	


}
