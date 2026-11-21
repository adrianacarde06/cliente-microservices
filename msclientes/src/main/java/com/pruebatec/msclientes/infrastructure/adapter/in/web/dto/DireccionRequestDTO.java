package com.pruebatec.msclientes.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record DireccionRequestDTO(
        Long id,
        @NotBlank(message = "La direccion es obligatoria")
        String direccionCompleta,
        @NotBlank(message = "La ciudad es obligatoria")
        String ciudad,
        @NotBlank(message = "El departamento es obligatorio")
        String departamento) {
}