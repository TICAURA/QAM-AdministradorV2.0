package com.aura.admin.adminqamm.service;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aura.admin.adminqamm.exception.BusinessException;

@Service
public class CargaMexicoService {
	
	Logger logger = LoggerFactory.getLogger(CargaMexicoService.class);

	public void insertCargaMexico(int loggedIdUser, MultipartFile cargaMexico) throws BusinessException {
		
		if (logger.isInfoEnabled()) {
			logger.info("/**** Procesa archivo carga masiva mexico  ****/" + cargaMexico.getName());
		}
		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(cargaMexico.getInputStream());
		} catch (IOException e) {
			logger.error("ERROR al leer el archivo para la carga masiva mexico :: ",e.getMessage());
		}
	}

}
