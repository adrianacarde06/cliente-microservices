package com.pruebatec.msclientes.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Direccion {

    private Long id;
    private String direccionCompleta;
    private String ciudad;
    private String departamento;
    private Cliente cliente;
}
