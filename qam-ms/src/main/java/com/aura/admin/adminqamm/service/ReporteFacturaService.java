/**
 * 
 */
package com.aura.admin.adminqamm.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dao.FacturaDao;
import com.aura.admin.adminqamm.dto.FacturaReporteDto;
import com.aura.admin.adminqamm.exception.BusinessException;

/**
 * @author Cesar Agustin Soto
 *
 */
@Service
public class ReporteFacturaService {
	
	@Autowired
	private FacturaDao facturaDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ReporteIncidenciaService.class);
	
	private HSSFFont headerFont;

	private CellStyle style = null;
	
	private Font font = null;
	
	private HSSFPalette palette = null;
	
	private CellStyle style2 = null;
	
	private CellStyle style3 = null;
	
	private Font font2 = null;
	
	
	public HSSFWorkbook obtenerWorkBook(int month) throws BusinessException {
		List<FacturaReporteDto> facturas = facturaDao.getReporte(month);
		
		if(facturas == null) {
			throw new BusinessException("No hay datos para generar el reporte", 401);
		}
		
		return buildExcel(facturas);
	}
	
	private HSSFWorkbook buildExcel(List<FacturaReporteDto> facturas) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Reporte Factura");
			
		String[] headers = new String[] { "Clave colaborador", "RFC", "Nombre",
				"Apellido paterno", "Apellido materno", "Nombre completo",
				"Email", "NSS", "Periodicidad", "Curp", "IVA", "Comision"};
		HSSFRow headerRow = sheet.createRow(0);
		
		style = workbook.createCellStyle();// Create style
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		
		for (int i = 0; i < headers.length; ++i) {
			String header = headers[i];
			HSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(header);
			cell.setCellStyle(style);
		}
		
		crearEstilos( workbook,  sheet);
		
		int i = 0;
		for (FacturaReporteDto factura : facturas) {
			int col = 0;
			HSSFRow dataRow = sheet.createRow(i + 1);
			
			dataRow.createCell(col).setCellValue(factura.getClaveColaborador());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getRfc());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getNombre());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getApellidoP());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getApellidoM());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getNombreC());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getEmail());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getNss());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getPeriodicidad());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getCurp());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getIva());
			dataRow.getCell(col++).setCellStyle(style2);
			dataRow.createCell(col).setCellValue(factura.getComision());
			dataRow.getCell(col++).setCellStyle(style2);
			
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			sheet.autoSizeColumn(7);
			sheet.autoSizeColumn(8);
			sheet.autoSizeColumn(9);
			sheet.autoSizeColumn(10);
			sheet.autoSizeColumn(11);
			
			i++;
		}

		return workbook;
	}
	
	public void crearEstilos(HSSFWorkbook workbook, HSSFSheet sheet) {
		byte[] rgb = new byte[]{(byte)0, (byte)55, (byte)158};
		// Estilos de encabezados
		style = workbook.createCellStyle();// Create style
		font = workbook.createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		font.setColor(HSSFColor.HSSFColorPredefined.LIME.getIndex());
		palette = workbook.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.LIME.getIndex(), rgb[0], rgb[1], rgb[2]);
		HSSFColor myColor = palette.findSimilarColor(244, 169, 16); 
		// get the palette index of that color 
		short palIndex = myColor.getIndex(); 
		// code to get the style for the cell goes here
		style.setFillForegroundColor(palIndex);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		font.setFontName("Monserrat");
		style.setFont(font);
		
		style2 = workbook.createCellStyle();// Create style
		font2 = workbook.createFont();// Create font
		rgb = new byte[]{(byte)255, (byte)164, (byte)0};
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		font2.setFontName("Monserrat");
		style2.setFont(font2);
		
		style3 = workbook.createCellStyle();// Create style
		style3.setBorderBottom(BorderStyle.THIN);
		style3.setBorderTop(BorderStyle.THIN);
		style3.setBorderRight(BorderStyle.THIN);
		style3.setBorderLeft(BorderStyle.THIN);
		style3.setAlignment(HorizontalAlignment.RIGHT);
		style3.setFont(font2);
		
		
	}
	
	public static int getMonths(LocalDate date) {
		return (int) ChronoUnit.MONTHS.between(
			     YearMonth.from(date), 
			     YearMonth.from(LocalDate.now()));
	}
	
	public static int getMonths(Date input) {	
		LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return (int) ChronoUnit.MONTHS.between(
			     YearMonth.from(date), 
			     YearMonth.from(LocalDate.now()));
	}

}
