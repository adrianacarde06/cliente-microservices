package com.pruebatec.msclientes.infrastructure.adapter.in.web.exception;

import com.pruebatec.msclientes.domain.exception.ClienteNotFoundException;
import com.pruebatec.msclientes.domain.exception.DireccionNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleClienteNotFound() {
        ProblemDetail pd = handler.handleClienteNotFound(new ClienteNotFoundException(1L));
        assertEquals(HttpStatus.NOT_FOUND.value(), pd.getStatus());
        assertTrue(pd.getDetail().contains("Cliente no encontrado"));
    }

    @Test
    void handleDireccionNotFound() {
        ProblemDetail pd = handler.handleDireccionNotFound(new DireccionNotFoundException(1L));
        assertEquals(HttpStatus.NOT_FOUND.value(), pd.getStatus());
        assertTrue(pd.getDetail().contains("Dirección no encontrada"));
    }

    @Test
    void handleIllegalArgumentDetail400() {
        ProblemDetail pd = handler.handleIllegalArgument(new IllegalArgumentException("Dato malo"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), pd.getStatus());
        assertEquals("Dato malo", pd.getDetail());
    }

    @Test
    void handleGenericDetail500() {
        ProblemDetail pd = handler.handleGeneric(new RuntimeException("Error inesperado"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), pd.getStatus());
        assertEquals("Ocurrió un error inesperado", pd.getDetail());
    }
}
