package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public @Data
class Promocion {
    int idPromocion;
    String nombre;
    String descripcion;
    Integer tipoPromocion;
    String codigoPromo;
    Date fechaInicio;
    Date fechaFin;
    Integer montoBeneficio;
    Double porcentajeBeneficio;
    Integer montoPorcentajeMaximo;
    Date fechaCreacion;
    boolean activo;

    boolean cobrado;
    Date fechaCanjeo;
    Date fechaCobro;


    public static Promocion build(ResultSet resultSet) throws SQLException {
        Promocion promocion = new Promocion();
        promocion.setIdPromocion(resultSet.getInt("promocion_id"));
        promocion.setNombre(resultSet.getString("nombre"));
        promocion.setDescripcion(resultSet.getString("descripcion"));
        promocion.setTipoPromocion(resultSet.getInt("tipo_promocion_id"));
        promocion.setCodigoPromo(resultSet.getString("codigo_promocion"));
        promocion.setFechaInicio(resultSet.getDate("inicio"));
        promocion.setFechaFin(resultSet.getDate("final"));
        promocion.setMontoBeneficio(resultSet.getInt("monto_beneficio"));
        promocion.setPorcentajeBeneficio(resultSet.getDouble("porcentaje_beneficio"));
        promocion.setMontoPorcentajeMaximo(resultSet.getInt("monto_porcentaje_maximo"));
        promocion.setFechaCreacion(resultSet.getDate("fecha_creacion"));
        promocion.setActivo(resultSet.getBoolean("es_activo"));

        return promocion;
    }

}
