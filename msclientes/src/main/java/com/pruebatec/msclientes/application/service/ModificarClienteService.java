package com.pruebatec.msclientes.application.service;

import com.pruebatec.msclientes.domain.exception.ClienteNotFoundException;
import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.ports.in.ModificarClienteUseCase;
import com.pruebatec.msclientes.domain.ports.out.ClienteRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ModificarClienteService implements ModificarClienteUseCase {

    private final ClienteRepositoryPort repository;

    public ModificarClienteService(ClienteRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Cliente modificar(Long id, Cliente datosNuevos) {
        Cliente existente = repository.buscarPorId(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        existente.setNombre(datosNuevos.getNombre());
        existente.setApellido(datosNuevos.getApellido());
        existente.setNumeroDocumento(datosNuevos.getNumeroDocumento());
        existente.setTipoDocumento(datosNuevos.getTipoDocumento());
        existente.setEdad(datosNuevos.getEdad());

        return repository.guardar(existente);
    }
}
