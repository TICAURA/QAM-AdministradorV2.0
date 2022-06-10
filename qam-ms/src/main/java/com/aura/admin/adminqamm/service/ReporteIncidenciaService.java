package com.aura.admin.adminqamm.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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

import com.aura.admin.adminqamm.dao.ReporteIncidenciaDao;
import com.aura.admin.adminqamm.dto.VRepIncidenciasDto;
import com.aura.admin.adminqamm.exception.BusinessException;

/**
 * @author Cesar Agustin Soto
 *
 */
@Service
public class ReporteIncidenciaService {
	
	@Autowired
	private ReporteIncidenciaDao repIncidenciaRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ReporteIncidenciaService.class);
	
	private CellStyle style = null;
	
	private Font font = null;
	
	private HSSFPalette palette = null;
	
	private CellStyle style2 = null;
	
	private CellStyle style3 = null;
	
	private Font font2 = null;

	
	public HSSFWorkbook mostrarVRepIncidencias(String centroCosto, String cliente, String clienteSubcontrato, Date fchTransferenciaFin, Date fchTransferenciaInicio) throws BusinessException {
		
		logger.info("/**** Centro de costos :: "+centroCosto);
		
		fechasValidas(fchTransferenciaFin, fchTransferenciaInicio);
		
		return getIncidencias(centroCosto, cliente, clienteSubcontrato, fchTransferenciaFin, fchTransferenciaInicio);

	}
	
	
	private HSSFWorkbook getIncidencias(String centroCosto, String cliente, String clienteSubcontrato,
		Date fchTransferenciaFin, Date fchTransferenciaInicio) throws BusinessException {
		
		List<VRepIncidenciasDto> incidencias = repIncidenciaRepository.obtenerVRepIncidencia(centroCosto, cliente, clienteSubcontrato, fchTransferenciaInicio, fchTransferenciaFin);
		
		return generaReporte(incidencias);
	}

	private void fechasValidas(Date fchTransferenciaFin, Date fchTransferenciaInicio) throws BusinessException {
		if (fchTransferenciaInicio == null && fchTransferenciaFin != null) {
			throw new BusinessException("Selecciona fecha de transferencia inicio", 401);
		} else if (fchTransferenciaInicio != null && fchTransferenciaFin == null) {
			throw new BusinessException("Selecciona fecha de transferencia fin", 401);
		} else if (fchTransferenciaInicio != null && fchTransferenciaFin != null
				&& fchTransferenciaInicio.after(fchTransferenciaFin)) {
			throw new BusinessException("La fecha de transferencia inicio no puede ser posterior a la fecha de transferencia fin", 401);
		}
	}
	
	private HSSFWorkbook generaReporte(List<VRepIncidenciasDto> incidencias) throws BusinessException {
	
			logger.info("/**** Inicia escritura excel");

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, "Hoja excel");

			crearEstilos(workbook, sheet);

			String[] headers = new String[] { "Centro Costos", "Cliente", "RFC Cliente",
					"Registro Patronal Cliente", "Cliente Subcontrato", "RFC Cliente Subcontrato",
					"Clave Colaborador", "CURP", "Nombre", "Apellido Paterno", "Apellido Materno", "Fecha Nacimiento", 
					"Sexo", "Periodicidad", "Periodo", "Producto", "Banco", "CLABE", "Cuenta", "Tarjeta", "Importe", "Comisi�n", "Total",
					"Saldo Gratis", "Monto Neto", "Fecha Solicitud", "Fecha STP", "Clave Rastreo", 
					"Estado", "Motivo Rechazo"};
			HSSFRow headerRow = sheet.createRow(0);
			for (int i = 0; i < headers.length; ++i) {
				String header = headers[i];
				HSSFCell cell = headerRow.createCell(i);
				cell.setCellValue(header);
				cell.setCellStyle(style);
			}
			
			style = workbook.createCellStyle();// Create style
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);

			int i = 0;
			for (VRepIncidenciasDto itemFila : incidencias) {
				int col = 0;
				HSSFRow dataRow = sheet.createRow(i + 1);
				dataRow.createCell(col).setCellValue(itemFila.getCentroCostos());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getCliente());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getRfcCliente());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getRegistroPatronalCliente());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getClienteSubcontrato());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getRfcClienteSubcontrato());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getClaveColaborador());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getCurp());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getNombre());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getApellidoPat());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getApellidoMat());
				dataRow.getCell(col++).setCellStyle(style2);
				
				if (itemFila.getFechaNacimiento()!=null) {
					dataRow.createCell(col).setCellValue(itemFila.getFechaNacimiento());					
				} else {
					dataRow.createCell(col).setCellValue("");
				}
				dataRow.getCell(col++).setCellStyle(style2);
				
				dataRow.createCell(col).setCellValue(itemFila.getSexo());
				dataRow.getCell(col++).setCellStyle(style2);
				
				dataRow.createCell(col).setCellValue(itemFila.getPeriodicidad());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getPeriodo());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getProducto());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getBanco());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getClave());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getCuenta());
				dataRow.getCell(col++).setCellStyle(style2);										

				if (itemFila.getTarjeta() != null) {
					dataRow.createCell(col).setCellValue(itemFila.getTarjeta());
				} else {
					dataRow.createCell(col).setCellValue("");
				}
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(formatoNumero(new Double(itemFila.getImporte())));
				dataRow.getCell(col++).setCellStyle(style3);
				dataRow.createCell(col).setCellValue(formatoNumero(new Double(itemFila.getComision())));
				dataRow.getCell(col++).setCellStyle(style3);
				dataRow.createCell(col).setCellValue(formatoNumero(new Double(itemFila.getTotal())));
				dataRow.getCell(col++).setCellStyle(style3);
				dataRow.createCell(col).setCellValue(formatoNumero(new Double(itemFila.getSaldoGratis())));
				dataRow.getCell(col++).setCellStyle(style3);
				dataRow.createCell(col).setCellValue(formatoNumero(new Double(itemFila.getMontoNeto())));
				dataRow.getCell(col++).setCellStyle(style3);
				
				if (itemFila.getFchTransferencia() != null) {						
					dataRow.createCell(col).setCellValue(itemFila.getFchTransferencia());
				} else {
					dataRow.createCell(col).setCellValue("");
				}
				dataRow.getCell(col++).setCellStyle(style2);

				if (itemFila.getFechaStp()!=null) {
					dataRow.createCell(col).setCellValue(itemFila.getFechaStp());
				} else {
					dataRow.createCell(col).setCellValue("");
				}
				dataRow.getCell(col++).setCellStyle(style2);
				
				dataRow.createCell(col).setCellValue(itemFila.getClaveAutorizacion());
				dataRow.getCell(col++).setCellStyle(style2);					
				dataRow.createCell(col).setCellValue(itemFila.getEstado());
				dataRow.getCell(col++).setCellStyle(style2);
				dataRow.createCell(col).setCellValue(itemFila.getMotivoRechazo());
				dataRow.getCell(col++).setCellStyle(style2);
				
				i++;
			}
			
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
			sheet.autoSizeColumn(12);
			sheet.autoSizeColumn(13);
			sheet.autoSizeColumn(14);
			sheet.autoSizeColumn(15);
			sheet.autoSizeColumn(16);
			sheet.autoSizeColumn(17);
			sheet.autoSizeColumn(18);
			sheet.autoSizeColumn(19);
			sheet.autoSizeColumn(20);
			sheet.autoSizeColumn(21);
			sheet.autoSizeColumn(22);
			sheet.autoSizeColumn(23);
			sheet.autoSizeColumn(24);
			sheet.autoSizeColumn(25);
			sheet.autoSizeColumn(26);
			sheet.autoSizeColumn(27);
			sheet.autoSizeColumn(28);
			sheet.autoSizeColumn(29);

			HSSFRow dataRow = sheet.createRow(1 + incidencias.size());
			HSSFCell total = dataRow.createCell(1);

			logger.info("/**** Fin escritura excel");
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
	
	public String formatoNumero(Double numero) {
		DecimalFormat formato = new DecimalFormat("$#,###,###.##");
		String valorFormateado = formato.format(numero);
		if(numero == 0) {
			valorFormateado = "-";
		}else {			
			valorFormateado = valorFormateado.contains(".") ? valorFormateado : valorFormateado +".00"; 
		}
		return valorFormateado;
	}
	
	/**
	 * Metodo encargado de convertir a tipo fecha dd/MM/yyyy HH:mm:ss
	 */
	public String formatoFecha(Date fecha, int Formato) {
		SimpleDateFormat formatter;
		if(Formato == 1) {			
			formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		}else {
			formatter = new SimpleDateFormat("dd/MM/yyyy");
		}
		String fechaTexto = formatter.format(fecha); 
		return fechaTexto;
	}
	

}
