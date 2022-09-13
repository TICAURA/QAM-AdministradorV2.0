package com.aura.admin.adminqamm.service;


import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.aura.admin.adminqamm.model.AuxCarga;
import com.aura.admin.adminqamm.model.DetCarga;
import com.aura.admin.adminqamm.model.Persona;
import com.aura.admin.adminqamm.repository.AuxCargaRepository;
import com.aura.admin.adminqamm.repository.DetCargaRepository;

@Service
public class CargaColombiaService {
	
	Logger logger = LoggerFactory.getLogger(CargaColombiaService.class);
	
	@Autowired
	private AuxCargaRepository auxCargaRepository;
	@Autowired
	private DetCargaRepository detCargaRepository;
	
	public void insertCargaColombia(int loggedIdUser, CargaRequestDto cargaColombia) throws BusinessException {
		
		
		if (logger.isInfoEnabled()) {
			logger.info("/**** Procesa archivo carga masiva colombia  ****/" + cargaColombia.getArchivo().getName());
			
			AuxCarga auxCargaBD = new AuxCarga();
			cargarAuxBD(cargaColombia, auxCargaBD);
			auxCargaRepository.save(auxCargaBD);
			
			
		}
		logger.info("/**** Informacion carga correctamente  ****/" + cargaColombia.getArchivo().getName());
	}
	
	private void cargarAuxBD(CargaRequestDto cargaColombia, AuxCarga auxCargaBD) {
		
		Date todayDate = new Date();
		
		auxCargaBD.setFechaAlta(todayDate);
		auxCargaBD.setIdCargaMasiva(cargaColombia.getIdCarga());
		auxCargaBD.setNombreArchivo(cargaColombia.getArchivo().getName());	
		
	}
}
