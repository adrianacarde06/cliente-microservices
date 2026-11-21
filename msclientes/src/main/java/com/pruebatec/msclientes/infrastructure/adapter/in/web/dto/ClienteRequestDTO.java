package com.pruebatec.msclientes.infrastructure.adapter.in.web.dto;

import com.pruebatec.msclientes.domain.model.valueobject.TipoDocumento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClienteRequestDTO(
        Long id,
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank(message = "El apellido es obligatorio")
        String apellido,
        @NotBlank(message = "El numero de documento es obligatorio")
        String numeroDocumento,
        @NotNull(message = "El tipo de documento es obligatorio")
        TipoDocumento tipoDocumento,
        Long edad,
        @NotEmpty(message = "El cliente debe tener al menos una dirección")
        @Valid
        List<DireccionRequestDTO> direcciones) {
}
