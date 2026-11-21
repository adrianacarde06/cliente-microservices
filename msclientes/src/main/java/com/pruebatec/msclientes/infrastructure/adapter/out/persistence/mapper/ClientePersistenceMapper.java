package com.pruebatec.msclientes.infrastructure.adapter.out.persistence.mapper;

import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.model.Direccion;
import com.pruebatec.msclientes.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import com.pruebatec.msclientes.infrastructure.adapter.out.persistence.entity.DireccionEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ClientePersistenceMapper {

    public ClienteEntity toEntity(Cliente domain) {
        ClienteEntity entity = new ClienteEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setApellido(domain.getApellido());
        entity.setNumeroDocumento(domain.getNumeroDocumento());
        entity.setTipoDocumento(domain.getTipoDocumento());
        entity.setEdad(domain.getEdad());
        entity.setActivo(domain.isActivo());
        entity.setDirecciones(
                domain.getDirecciones().stream()
                        .map(this::toDireccionEntity)
                        .collect(Collectors.toCollection(ArrayList::new)));
        return entity;
    }

    public Cliente toDomain(ClienteEntity entity) {
        Cliente domain = new Cliente();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        domain.setApellido(entity.getApellido());
        domain.setNumeroDocumento(entity.getNumeroDocumento());
        domain.setTipoDocumento(entity.getTipoDocumento());
        domain.setEdad(entity.getEdad());
        domain.setDirecciones(
                entity.getDirecciones().stream()
                    .map(this::toDireccionDomain)
                    .collect(Collectors.toCollection(ArrayList::new)));
        return domain;
    }

    private DireccionEntity toDireccionEntity(Direccion domain) {
        DireccionEntity entity = new DireccionEntity();
        entity.setId(domain.getId());
        entity.setDireccionCompleta(domain.getDireccionCompleta());
        entity.setCiudad(domain.getCiudad());
        entity.setDepartamento(domain.getDepartamento());
        return entity;
    }
    private Direccion toDireccionDomain(DireccionEntity entity) {
        Direccion domain = new Direccion();
        domain.setId(entity.getId());
        domain.setDireccionCompleta(entity.getDireccionCompleta());
        domain.setCiudad(entity.getCiudad());
        domain.setDepartamento(entity.getDepartamento());
        return domain;
    }
}