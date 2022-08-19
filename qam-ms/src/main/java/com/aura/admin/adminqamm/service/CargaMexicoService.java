package com.aura.admin.adminqamm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.exception.BusinessException;

@Service
public class CargaMexicoService {
	
	Logger logger = LoggerFactory.getLogger(CargaMexicoService.class);

	public void insertCargaMexico(int loggedIdUser) throws BusinessException {
		
		if (logger.isInfoEnabled()) {
			logger.info("/**** Procesa archivo carga masiva mexico  ****/");
		}
		
	}

}
