package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.*;
import com.aura.admin.adminqamm.exception.BusinessException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class NOM035ReporteService {
    private static final Logger logger = LoggerFactory.getLogger(ReporteIncidenciaService.class);

    private XSSFFont headerFont;

    private CellStyle style = null;

    private Font font = null;


    private CellStyle style2 = null;

    private CellStyle style3 = null;

    private Font font2 = null;



    public XSSFWorkbook buildExcelReporteGeneral(List<NOM035ReporteGeneral> reporteGeneral) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "Reporte Cliente");

        String[] headers = new String[] { "Clave colaborador", "Colaborador", "ID Cuestionario",
                "Cuestionario", "Evaluación", "Riesgo"};


        XSSFRow headerRow = sheet.createRow(0);

        style = workbook.createCellStyle();// Create style
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        //set headers
        for (int i = 0; i < headers.length; ++i) {
            String header = headers[i];
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(header);
            cell.setCellStyle(style);
        }

        crearEstilos( workbook);

        int i = 0;
        for (NOM035ReporteGeneral respuesta : reporteGeneral) {
            int col = 0;
            XSSFRow dataRow = sheet.createRow(i + 1);

            dataRow.createCell(col).setCellValue(respuesta.getIdColaborador());
            dataRow.getCell(col++).setCellStyle(style2);
            dataRow.createCell(col).setCellValue(respuesta.getColaborador());
            dataRow.getCell(col++).setCellStyle(style2);
            dataRow.createCell(col).setCellValue(respuesta.getIdCuestionario());
            dataRow.getCell(col++).setCellStyle(style2);
            dataRow.createCell(col).setCellValue(respuesta.getCuestionario());
            dataRow.getCell(col++).setCellStyle(style2);
            dataRow.createCell(col).setCellValue(respuesta.getEvaluacion());
            dataRow.getCell(col++).setCellStyle(style2);
            dataRow.createCell(col).setCellValue(respuesta.getRiesgo());
            dataRow.getCell(col++).setCellStyle(style2);


            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);

            i++;
        }

        return workbook;
    }


    public XSSFWorkbook buildExcelReporteIndividual(NOM035ReportDto reportDto) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFSheet sheetCategoria = workbook.createSheet();
        XSSFSheet sheetDominio = workbook.createSheet();

        workbook.setSheetName(0, "Reporte Cuestionario");
        workbook.setSheetName(1, "Reporte Categorias");
        workbook.setSheetName(2, "Reporte Dominio");

        String[] headers = new String[] { "Clave colaborador", "Nombre","ID Cuestionario", "Cuestionario", "Evaluación",
                "Riesgo"};
        String[] headersCategoria = new String[] { "ID Categoría", "Categoría", "Evaluación", "Riesgo"};
        String[] headersDominio = new String[] { "ID Dominio", "Dominio", "Evaluación",
                "Riesgo"};

        XSSFRow headerRow = sheet.createRow(0);
        XSSFRow headerRowCategoria = sheetCategoria.createRow(0);
        XSSFRow headerRowDominio = sheetDominio.createRow(0);

        style = workbook.createCellStyle();// Create style
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        //set headers
        for (int i = 0; i < headers.length; ++i) {
            String header = headers[i];
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(header);
            cell.setCellStyle(style);
        }
        for (int i = 0; i < headersCategoria.length; ++i) {
            String header = headersCategoria[i];
            XSSFCell cell = headerRowCategoria.createCell(i);
            cell.setCellValue(header);
            cell.setCellStyle(style);
        }
        for (int i = 0; i < headersDominio.length; ++i) {
            String header = headersDominio[i];
            XSSFCell cell = headerRowDominio.createCell(i);
            cell.setCellValue(header);
            cell.setCellStyle(style);
        }

        crearEstilos( workbook);

        XSSFRow dataRow = sheet.createRow( 1);

        dataRow.createCell(0).setCellValue(reportDto.getId());
        dataRow.getCell(0).setCellStyle(style2);
        dataRow.createCell(1).setCellValue(reportDto.getNombre());
        dataRow.getCell(1).setCellStyle(style2);
        dataRow.createCell(2).setCellValue(reportDto.getCuestionario().getId());
        dataRow.getCell(2).setCellStyle(style2);
        dataRow.createCell(3).setCellValue(reportDto.getCuestionario().getNombre());
        dataRow.getCell(3).setCellStyle(style2);
        dataRow.createCell(4).setCellValue(reportDto.getCuestionario().getRiesgo());
        dataRow.getCell(4).setCellStyle(style2);
        dataRow.createCell(5).setCellValue(reportDto.getCuestionario().getTotal());
        dataRow.getCell(5).setCellStyle(style2);

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);


        int i = 0;
        for (NOM035RegistroDto registro : reportDto.getCategorias()) {
            int col = 0;
            XSSFRow dataRowCategoria = sheetCategoria.createRow(i + 1);

            dataRowCategoria.createCell(col).setCellValue(registro.getId());
            dataRowCategoria.getCell(col++).setCellStyle(style2);
            dataRowCategoria.createCell(col).setCellValue(registro.getNombre());
            dataRowCategoria.getCell(col++).setCellStyle(style2);
            dataRowCategoria.createCell(col).setCellValue(registro.getRiesgo());
            dataRowCategoria.getCell(col++).setCellStyle(style2);
            dataRowCategoria.createCell(col).setCellValue(registro.getTotal());
            dataRowCategoria.getCell(col++).setCellStyle(style2);


            sheetCategoria.autoSizeColumn(0);
            sheetCategoria.autoSizeColumn(1);
            sheetCategoria.autoSizeColumn(2);
            sheetCategoria.autoSizeColumn(3);


            i++;
        }

        i = 0;
        for (NOM035RegistroDto registro : reportDto.getDominios()) {
            int col = 0;
            XSSFRow dataRowDominio = sheetDominio.createRow(i + 1);

            dataRowDominio.createCell(col).setCellValue(registro.getId());
            dataRowDominio.getCell(col++).setCellStyle(style2);
            dataRowDominio.createCell(col).setCellValue(registro.getNombre());
            dataRowDominio.getCell(col++).setCellStyle(style2);
            dataRowDominio.createCell(col).setCellValue(registro.getRiesgo());
            dataRowDominio.getCell(col++).setCellStyle(style2);
            dataRowDominio.createCell(col).setCellValue(registro.getTotal());
            dataRowDominio.getCell(col++).setCellStyle(style2);


            sheetDominio.autoSizeColumn(0);
            sheetDominio.autoSizeColumn(1);
            sheetDominio.autoSizeColumn(2);
            sheetDominio.autoSizeColumn(3);


            i++;
        }

        return workbook;
    }
    public XSSFWorkbook buildExcelReporteRespuestas(NOM035ReporteRespuestas respuestas) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "Reporte Respuestas");

        String[] headers = new String[] { "Clave colaborador", "Colaborador", "ID Cuestionario",
                "Cuestionario","","","ID Pregunta","Pregunta","Respuesta"};
        XSSFRow headerRow = sheet.createRow(0);

        style = workbook.createCellStyle();// Create style
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        //set headers
        for (int i = 0; i < headers.length; ++i) {
            String header = headers[i];
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(header);
            cell.setCellStyle(style);
        }

        crearEstilos( workbook);

        int i = 0;
        for (NOM035ReporteRespuestaRegistro respuesta : respuestas.getPreguntas()) {
            int col = 6;

            XSSFRow dataRow = sheet.createRow(i + 1);

            if(i==0){
                dataRow.createCell(0).setCellValue(respuestas.getIdColaborador());
                dataRow.getCell(0).setCellStyle(style2);
                dataRow.createCell(1).setCellValue(respuestas.getNombre());
                dataRow.getCell(1).setCellStyle(style2);
                dataRow.createCell(2).setCellValue(respuestas.getIdCuestionario());
                dataRow.getCell(2).setCellStyle(style2);
                dataRow.createCell(3).setCellValue(respuestas.getCuestionario());
                dataRow.getCell(3).setCellStyle(style2);

                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
            }

            dataRow.createCell(col).setCellValue(respuesta.getId());
            dataRow.getCell(col++).setCellStyle(style2);
            dataRow.createCell(col).setCellValue(respuesta.getPregunta());
            dataRow.getCell(col++).setCellStyle(style2);
            dataRow.createCell(col).setCellValue(respuesta.getRespuesta());
            dataRow.getCell(col++).setCellStyle(style2);


            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);


            i++;
        }

        return workbook;
    }

    public void crearEstilos(XSSFWorkbook workbook) {
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


        // code to get the style for the cell goes here

        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
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


}
