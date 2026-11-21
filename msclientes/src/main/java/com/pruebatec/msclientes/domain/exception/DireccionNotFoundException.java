package com.pruebatec.msclientes.domain.exception;

public class DireccionNotFoundException extends RuntimeException {
    public DireccionNotFoundException(Long id) {
        super("Dirección no encontrada con id: " + id);
    }
}

