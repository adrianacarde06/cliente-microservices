package com.pruebatec.msclientes.infrastructure.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void crearClienteDatosValidos() throws Exception {
        String jsonRequest = """
            {
              "nombre": "Juan",
                     "apellido": "Perez",
                     "numeroDocumento": "123456789",
                     "tipoDocumento": "CC",
                     "edad": 18,
                     "direcciones": [
                       {
                         "direccionCompleta": "Calle 10 Nro 1-10",
                         "ciudad": "Pereira",
                         "departamento": "Risaralda"
                       },
                   	{
                         "direccionCompleta": "Calle 20 Nro 2-20",
                         "ciudad": "Manizales",
                         "departamento": "Caldas"
                       }
                     ]
            }
            """;

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    void crearClienteSinDirecciones() throws Exception {
        String jsonRequest = """
            {
              "nombre": "Juan",
               "apellido": "Perez",
               "numeroDocumento": "123456789",
               "tipoDocumento": "CC",
               "edad": 18
            }
            """;

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.direcciones").exists());
    }


}
