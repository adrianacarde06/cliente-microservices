package com.pruebatec.msclientes.infrastructure.adapter.in.web;

import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.model.Direccion;
import com.pruebatec.msclientes.domain.ports.in.ConsultarClienteUseCase;
import com.pruebatec.msclientes.domain.ports.in.CrearClienteUseCase;
import com.pruebatec.msclientes.domain.ports.in.GestionarDireccionesUseCase;
import com.pruebatec.msclientes.domain.ports.in.ModificarClienteUseCase;
import com.pruebatec.msclientes.infrastructure.adapter.in.web.dto.*;
import com.pruebatec.msclientes.infrastructure.adapter.in.web.mapper.ClienteWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final CrearClienteUseCase crearUseCase;
    private final ModificarClienteUseCase modificarUseCase;
    private final ConsultarClienteUseCase consultarUseCase;
    private final GestionarDireccionesUseCase gestionarDireccionesUseCase;
    private final ClienteWebMapper mapper;

    public ClienteController(CrearClienteUseCase crearUseCase,
                             ModificarClienteUseCase modificarUseCase,
                             ConsultarClienteUseCase consultarUseCase,
                             GestionarDireccionesUseCase gestionarDireccionesUseCase,
                             ClienteWebMapper mapper) {
        this.crearUseCase = crearUseCase;
        this.modificarUseCase = modificarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.gestionarDireccionesUseCase = gestionarDireccionesUseCase;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Crear un nuevo cliente",
            description = """
                    Crea un cliente con sus direcciones asociadas.
                    
                    El cliente se crea siempre en estado activo = true.
                    
                    **Tipos de documento permitidos:**
                    - `CC` → Cédula de ciudadanía 
                    - `CE` → Cédula de extranjería 
                    - `NIT` → Número de identificación tributaria 
                    - `PASAPORTE` → Pasaporte 
                    - `TI` → Tarjeta de identidad 
                    
                    **Nro de documento:**
                    Alfanumérico, 6-15
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos (validación de DTO o regla del VO Documento)",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO crear(@Valid @RequestBody ClienteRequestDTO dto) {
        Cliente cliente = mapper.toDomain(dto);
        return mapper.toResponse(crearUseCase.crear(cliente));
    }

    @Operation(
            summary = "Obtener un cliente por su ID",
            description = """
        Devuelve un cliente con todas sus direcciones asociadas.
        """
    )
    @GetMapping("/{id}")
    public ClienteResponseDTO obtener(@PathVariable Long id) {
        return mapper.toResponse(consultarUseCase.obtenerPorId(id));
    }

    @Operation(
            summary = "Listar todos los clientes",
            description = """
        Devuelve la lista completa de clientes con sus direcciones.
        """
    )
    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return consultarUseCase.listarTodos().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Operation(
            summary = "Modificar un cliente existente",
            description = """
        Actualiza los datos del cliente (nombre, apellido, documento, tipo documento, edad).
        
        Importante: este endpoint **no** modifica las direcciones.
        Para agregar o eliminar direcciones usa:
        - POST /api/clientes/{id}/direcciones
        - DELETE /api/clientes/{id}/direcciones/{direccionId}
        
        El documento debe seguir las mismas reglas que en la creación.
        """
    )
    @PutMapping("/{id}")
    public ClienteResponseDTO modificar(@PathVariable Long id,
                                        @Valid @RequestBody ClienteUpdateRequestDTO dto) {
        Cliente cliente = mapper.toDomainForUpdate(dto);
        return mapper.toResponse(modificarUseCase.modificar(id, cliente));
    }

    @Operation(
            summary = "Agregar una dirección a un cliente",
            description = """
        Agrega una nueva dirección al cliente indicado.
        El cliente debe existir.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Dirección agregada exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping("/{clienteId}/direcciones")
    @ResponseStatus(HttpStatus.CREATED)
    public DireccionResponseDTO agregarDireccion(
            @PathVariable Long clienteId,
            @Valid @RequestBody DireccionRequestDTO dto) {

        Direccion direccion = mapper.toDireccionDomain(dto);
        Direccion creada = gestionarDireccionesUseCase
                .agregarDireccion(clienteId, direccion);
        return mapper.toDireccionResponse(creada);
    }

    @Operation(
            summary = "Eliminar una dirección de un cliente",
            description = """
        Elimina una dirección específica del cliente indicado.
        Si el cliente o la dirección no existen, devuelve error.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Dirección eliminada exitosamente."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente o dirección no encontrada."
            )
    })
    @DeleteMapping("/{clienteId}/direcciones/{direccionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarDireccion(
            @PathVariable Long clienteId,
            @PathVariable Long direccionId) {

        gestionarDireccionesUseCase.eliminarDireccion(clienteId, direccionId);
    }
}
