package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

public @Data class NOM035CuestionarioDto {
    private int idCuestionario;
    private String nombre;
    private int cTotal;


    public static NOM035CuestionarioDto build(ResultSet rs) throws SQLException{
        NOM035CuestionarioDto nom035CuestionarioDto= new NOM035CuestionarioDto();
        nom035CuestionarioDto.setIdCuestionario(rs.getInt("id"));
        nom035CuestionarioDto.setNombre(rs.getString("nombre"));
        nom035CuestionarioDto.setCTotal(rs.getInt("total"));
        return nom035CuestionarioDto;
    }

}
