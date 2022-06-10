package com.aura.admin.adminqamm.dto;


import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

public @Data class NOM035ListDto {
    private int colaborador;
    private String nombre;
    private int c0Respondidas;
    private int c1Respondidas;

    public static NOM035ListDto build(ResultSet rs) throws SQLException{
        NOM035ListDto nom035ListDto = new NOM035ListDto();
        nom035ListDto.setColaborador(rs.getInt("id"));
        nom035ListDto.setNombre(rs.getString("nombre"));
        nom035ListDto.setC0Respondidas(rs.getInt("c0"));
        nom035ListDto.setC1Respondidas(rs.getInt("c1"));
        return nom035ListDto;
    }
}
