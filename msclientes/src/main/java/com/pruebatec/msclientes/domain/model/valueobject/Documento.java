package com.pruebatec.msclientes.domain.model.valueobject;

public record Documento(String numeroDocumento) {

    public Documento {
        if (numeroDocumento == null || !numeroDocumento.matches("^[0-9]{6,15}$")) {
            throw new IllegalArgumentException(
                    "El documento debe contener entre 6 y 15 dígitos");
        }
    }
}
