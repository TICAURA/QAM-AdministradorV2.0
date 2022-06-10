package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public @Data class NOM035ReporteRespuestas {
    private int idColaborador;
    private String nombre;
    private int idCuestionario;
    private String cuestionario;
    private List<NOM035ReporteRespuestaRegistro> preguntas;

    public static NOM035ReporteRespuestas build(ResultSet rs) throws SQLException {
        NOM035ReporteRespuestas nom035ReporteRespuestas = new NOM035ReporteRespuestas();
        nom035ReporteRespuestas.setIdColaborador(rs.getInt("clave_colaborador"));
        nom035ReporteRespuestas.setNombre(rs.getString("nombre"));
        nom035ReporteRespuestas.setIdCuestionario(rs.getInt("idCuestionario"));
        nom035ReporteRespuestas.setCuestionario(rs.getString("cuestionario"));
        return nom035ReporteRespuestas;
    }
}
