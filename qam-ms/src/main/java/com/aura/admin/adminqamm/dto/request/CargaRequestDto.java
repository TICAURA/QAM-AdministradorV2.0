package com.aura.admin.adminqamm.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public @Getter @Setter class CargaRequestDto {

	private int idCarga;
	private MultipartFile archivo;
	
}
