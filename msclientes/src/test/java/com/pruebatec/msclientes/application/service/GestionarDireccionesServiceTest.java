package com.pruebatec.msclientes.application.service;

import com.pruebatec.msclientes.domain.exception.ClienteNotFoundException;
import com.pruebatec.msclientes.domain.exception.DireccionNotFoundException;
import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.model.Direccion;
import com.pruebatec.msclientes.domain.ports.out.ClienteRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionarDireccionesServiceTest {

    @Mock private ClienteRepositoryPort repository;
    @InjectMocks private GestionarDireccionesService service;

    @Test
    void agregarDireccionClienteExiste() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(repository.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        Direccion nueva = new Direccion();
        nueva.setDireccionCompleta("Calle 1");
        nueva.setCiudad("Bogotá");
        nueva.setDepartamento("Cundinamarca");

        Direccion resultado = service.agregarDireccion(1L, nueva);

        assertNotNull(resultado);
        assertEquals(1, cliente.getDirecciones().size());
        verify(repository).guardar(cliente);
    }

    @Test
    void agregarDireccionClienteNoExiste() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class,
                () -> service.agregarDireccion(99L, new Direccion()));
    }

    @Test
    void eliminarDireccion() {
        Cliente cliente = new Cliente();
        Direccion d = new Direccion();
        d.setId(1L);
        cliente.getDirecciones().add(d);
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(repository.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        service.eliminarDireccion(1L, 1L);

        assertTrue(cliente.getDirecciones().isEmpty());
        verify(repository).guardar(cliente);
    }

    @Test
    void eliminarDireccionNoExiste() {
        Cliente cliente = new Cliente();
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        assertThrows(DireccionNotFoundException.class,
                () -> service.eliminarDireccion(1L, 99L));
    }
}