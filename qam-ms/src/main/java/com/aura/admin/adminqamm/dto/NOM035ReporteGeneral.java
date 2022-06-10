package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

public @Data class NOM035ReporteGeneral {

    private int idColaborador;
    private String colaborador;
    private int idCuestionario;
    private String cuestionario;
    private String evaluacion;
    private int riesgo;

    public static NOM035ReporteGeneral build(ResultSet rs) throws SQLException{
        NOM035ReporteGeneral reporteGeneral = new NOM035ReporteGeneral();
        reporteGeneral.setIdColaborador(rs.getInt("clave_colaborador"));
        reporteGeneral.setColaborador(rs.getString("nombre"));
        reporteGeneral.setIdCuestionario(rs.getInt("idCuestionario"));
        reporteGeneral.setCuestionario(rs.getString("cuestionario"));
        reporteGeneral.setEvaluacion(rs.getString("evaluacionRiesgo"));
        reporteGeneral.setRiesgo(rs.getInt("riesgoCantidad"));
        return reporteGeneral;
    }

}
