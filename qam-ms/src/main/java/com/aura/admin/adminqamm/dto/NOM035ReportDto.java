package com.aura.admin.adminqamm.dto;

import lombok.Data;

import java.util.List;

public @Data
class NOM035ReportDto {
    private int id;
    private String nombre;
   private NOM035RegistroDto cuestionario;
   private List<NOM035RegistroDto> categorias;
   private List<NOM035RegistroDto> dominios;
}
