package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

public @Data
class NOM035ReporteRespuestaRegistro {
    private int id;
    private String pregunta;
    private String respuesta;
    public static NOM035ReporteRespuestaRegistro build(ResultSet rs) throws SQLException{
        NOM035ReporteRespuestaRegistro registro = new NOM035ReporteRespuestaRegistro();
        registro.setId(rs.getInt("id"));
        registro.setPregunta(rs.getString("pregunta"));
        registro.setRespuesta(rs.getString("respuesta"));
        return registro;
    }
}
