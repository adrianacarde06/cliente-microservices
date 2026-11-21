package com.pruebatec.msclientes.domain.model;

import com.pruebatec.msclientes.domain.exception.DireccionNotFoundException;
import com.pruebatec.msclientes.domain.model.valueobject.Documento;
import com.pruebatec.msclientes.domain.model.valueobject.TipoDocumento;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cliente {

    private Long id;
    private String nombre;
    private String apellido;
    private Documento numeroDocumento;
    private TipoDocumento tipoDocumento;
    private Long edad;
    boolean activo;
    private List<Direccion> direcciones = new ArrayList<>();

    public Cliente() {
        this.activo = true;   //Se crea activo por defecto
    }

    public void agregarDireccion(Direccion direccion) {

        this.direcciones.add(direccion);
    }

    public void eliminarDireccion(Long direccionId) {
        boolean eliminada = this.direcciones.removeIf(d -> d.getId().equals(direccionId));
        if (!eliminada) {
            throw new DireccionNotFoundException(direccionId);
        }
    }

}
