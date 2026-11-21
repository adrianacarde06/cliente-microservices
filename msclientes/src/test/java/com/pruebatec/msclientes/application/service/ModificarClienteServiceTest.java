package com.pruebatec.msclientes.application.service;

import com.pruebatec.msclientes.domain.exception.ClienteNotFoundException;
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
class ModificarClienteServiceTest {

    @Mock
    private ClienteRepositoryPort repository;

    @InjectMocks
    private ModificarClienteService service;

    @Test
    void modificarClienteExiste() {
        // Arrange
        Cliente existente = new Cliente();
        existente.setId(1L);
        existente.agregarDireccion(new Direccion()); // Simula que ya tiene una dirección

        Cliente datosNuevos = new Cliente();
        datosNuevos.setNombre("Juan Actualizado");

        when(repository.buscarPorId(1L)).thenReturn(Optional.of(existente));
        when(repository.guardar(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Cliente resultado = service.modificar(1L, datosNuevos);

        // Assert
        assertEquals("Juan Actualizado", resultado.getNombre());
        assertEquals(1, resultado.getDirecciones().size()); // La dirección original se conserva
        verify(repository).guardar(existente);
    }

    @Test
    void modificarClienteNoExisteExcepcion() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class,
                () -> service.modificar(99L, new Cliente()));
        verify(repository, never()).guardar(any());
    }
}
