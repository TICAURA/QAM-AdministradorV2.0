package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

public @Data
class NOM035RegistroDto {
    private int id;
    private String nombre;
    private String riesgo;
    private int total;

    public static  NOM035RegistroDto build(ResultSet rs) throws SQLException{
        NOM035RegistroDto nom035RegistroDto = new NOM035RegistroDto();
        nom035RegistroDto.setId(rs.getInt("id"));
        nom035RegistroDto.setNombre(rs.getString("nombre"));
        nom035RegistroDto.setRiesgo(rs.getString("riesgo"));
        nom035RegistroDto.setTotal(rs.getInt("riesgoCantidad"));
        return nom035RegistroDto;
    }
}
