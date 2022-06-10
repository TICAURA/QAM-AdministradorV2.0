package com.aura.admin.adminqamm.dto.request;


import lombok.Getter;
import lombok.Setter;

public @Getter @Setter
class CatalogueRequestDto {
    private int id;
    private String descripcion;
    private String nombre;
    private boolean activo;
}
