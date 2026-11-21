package com.pruebatec.msclientes.infrastructure.adapter.in.web.mapper;

import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.model.Direccion;
import com.pruebatec.msclientes.domain.model.valueobject.Documento;
import com.pruebatec.msclientes.infrastructure.adapter.in.web.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ClienteWebMapper {

    public Cliente toDomain(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.id());
        cliente.setNombre(dto.nombre());
        cliente.setApellido(dto.apellido());
        cliente.setNumeroDocumento(new Documento(dto.numeroDocumento()));
        cliente.setTipoDocumento(dto.tipoDocumento());
        cliente.setEdad(dto.edad());
        cliente.setDirecciones(
                dto.direcciones().stream()
                        .map(this::toDireccionDomain)
                        .collect(Collectors.toCollection(ArrayList::new))
        );
        return cliente;
    }

    public Cliente toDomainForUpdate(ClienteUpdateRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.nombre());
        cliente.setApellido(dto.apellido());
        cliente.setNumeroDocumento(new Documento(dto.numeroDocumento()));
        cliente.setTipoDocumento(dto.tipoDocumento());
        cliente.setEdad(dto.edad());
        return cliente;
    }

    public Direccion toDireccionDomain(DireccionRequestDTO dto) {
        Direccion d = new Direccion();
        d.setId(dto.id());
        d.setDireccionCompleta(dto.direccionCompleta());
        d.setCiudad(dto.ciudad());
        d.setDepartamento(dto.departamento());
        return d;
    }

    public ClienteResponseDTO toResponse(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getNumeroDocumento().numeroDocumento(),
                cliente.getTipoDocumento(),
                cliente.getEdad(),
                cliente.isActivo(),
                cliente.getDirecciones().stream().map(this::toDireccionResponse).toList());
    }

    public DireccionResponseDTO toDireccionResponse(Direccion d) {
        return new DireccionResponseDTO(d.getId(), d.getDireccionCompleta(), d.getCiudad(), d.getDepartamento());
    }
}
