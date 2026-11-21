package com.pruebatec.msclientes.application.service;

import com.pruebatec.msclientes.domain.exception.ClienteNotFoundException;
import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.ports.out.ClienteRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarClienteServiceTest {

    @Mock private ClienteRepositoryPort repository;
    @InjectMocks private ConsultarClienteService service;


    @Test
    void obtenerPorIdExisteCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        Cliente resultado = service.obtenerPorId(1L);

        assertEquals(1L, resultado.getId());
        verify(repository).buscarPorId(1L);
    }

    @Test
    void obtenerPorIdNoExiste() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> service.obtenerPorId(99L));
    }


    @Test
    void listarTodosHayClientes() {
        Cliente c1 = new Cliente(); c1.setId(1L);
        Cliente c2 = new Cliente(); c2.setId(2L);
        when(repository.buscarTodos()).thenReturn(List.of(c1, c2));

        List<Cliente> resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        verify(repository).buscarTodos();
    }

    @Test
    void listarTodosNoHayClientes() {
        when(repository.buscarTodos()).thenReturn(List.of());

        List<Cliente> resultado = service.listarTodos();

        assertTrue(resultado.isEmpty());
    }
}
