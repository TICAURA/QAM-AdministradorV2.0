package com.aura.admin.adminqamm.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public @Getter @Setter class InterfazRequestDto {
    private int idInterfaz;

    private String nombre;

    private String titulo;

    private MultipartFile urlLogoIcono;

    private String colorGlobalPrimario;

    private String colorGlobalSecundario;

    private String colorHomePrimario;

    private String colorHomeSecundario;

    private String colorTexto;

    private String colorError;

    private String colorBackground;


    private boolean activo;
}
