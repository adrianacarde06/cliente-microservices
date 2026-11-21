package com.pruebatec.msclientes.infrastructure.adapter.in.web.dto;

public record DireccionResponseDTO(
        Long id,
        String direccionCompleta,
        String ciudad,
        String departamento
) {}
