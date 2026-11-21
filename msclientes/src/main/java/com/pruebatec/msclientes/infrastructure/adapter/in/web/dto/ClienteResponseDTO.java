package com.pruebatec.msclientes.infrastructure.adapter.in.web.dto;

import com.pruebatec.msclientes.domain.model.valueobject.TipoDocumento;

import java.util.List;

public record ClienteResponseDTO(
        Long id,
        String nombre,
        String apellido,
        String numeroDocumento,
        TipoDocumento tipoDocumento,
        Long edad,
        boolean activo,
        List<DireccionResponseDTO> direcciones
) { }
