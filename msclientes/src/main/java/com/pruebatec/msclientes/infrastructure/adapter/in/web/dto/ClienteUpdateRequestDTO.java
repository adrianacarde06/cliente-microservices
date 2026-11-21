package com.pruebatec.msclientes.infrastructure.adapter.in.web.dto;

import com.pruebatec.msclientes.domain.model.valueobject.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteUpdateRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank(message = "El apellido es obligatorio")
        String apellido,
        @NotBlank(message = "El numero de documento es obligatorio")
        String numeroDocumento,
        @NotNull(message = "El tipo de documento es obligatorio")
        TipoDocumento tipoDocumento,
        Long edad
) {}