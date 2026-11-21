package com.pruebatec.msclientes.application.service;

import com.pruebatec.msclientes.domain.exception.ClienteNotFoundException;
import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.model.Direccion;
import com.pruebatec.msclientes.domain.ports.in.GestionarDireccionesUseCase;
import com.pruebatec.msclientes.domain.ports.out.ClienteRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionarDireccionesService implements GestionarDireccionesUseCase {

    private final ClienteRepositoryPort repository;

    public GestionarDireccionesService(ClienteRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Direccion agregarDireccion(Long clienteId, Direccion direccion) {
        Cliente cliente = repository.buscarPorId(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException(clienteId));

        cliente.agregarDireccion(direccion);
        Cliente guardado = repository.guardar(cliente);

        return guardado.getDirecciones().stream()
                .filter(d -> d.getDireccionCompleta().equalsIgnoreCase(direccion.getDireccionCompleta())
                        && d.getCiudad().equalsIgnoreCase(direccion.getCiudad()))
                .findFirst()
                .orElseThrow();
    }

    @Override
    @Transactional
    public void eliminarDireccion(Long clienteId, Long direccionId) {
        Cliente cliente = repository.buscarPorId(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException(clienteId));

        cliente.eliminarDireccion(direccionId);
        repository.guardar(cliente);
    }
}
