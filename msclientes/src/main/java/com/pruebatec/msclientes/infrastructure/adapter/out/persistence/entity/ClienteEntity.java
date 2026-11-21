package com.pruebatec.msclientes.infrastructure.adapter.out.persistence.entity;

import com.pruebatec.msclientes.domain.model.valueobject.Documento;
import com.pruebatec.msclientes.domain.model.valueobject.TipoDocumento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
@Getter
@Setter
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private Documento numeroDocumento;
    private TipoDocumento tipoDocumento;
    private Long edad;
    private boolean activo = true;   // valor por defecto

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DireccionEntity> direcciones = new ArrayList<>();


}