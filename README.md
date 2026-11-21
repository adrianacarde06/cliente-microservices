# Microservicio de Clientes (msclientes)

Microservicio desarrollado en Java y Spring Boot para la gestión de clientes y sus direcciones, construido bajo los principios de **Arquitectura Hexagonal**, **Domain-Driven Design (DDD)** y **código limpio**. Está preparado para ser desplegado en orquestadores de contenedores como Kubernetes.

## 🏗️ Arquitectura y Decisiones de Diseño

El proyecto implementa **Arquitectura Hexagonal (Ports and Adapters)**, lo que garantiza que la lógica de negocio (dominio) esté completamente aislada de las tecnologías externas (frameworks, bases de datos, REST).

- **Dominio (`domain`)**: Contiene los modelos (`Cliente`, `Direccion`) y los Value Objects inmutables (`Documento`, `Tipo de documento`). No tiene dependencias de Spring ni de JPA.
- **Aplicación (`application`)**: Implementa los casos de uso (puertos de entrada) y orquesta el flujo llamando a los puertos de salida.
- **Infraestructura (`infrastructure`)**: Contiene los adaptadores. El adaptador de entrada (`web`) maneja los controladores REST y DTOs. El adaptador de salida (`persistence`) maneja JPA/Hibernate y la base de datos H2.

**Características destacadas:**
- **Value Objects inmutables**: Uso de `records` de Java para DTOs y VOs, garantizando validaciones en el constructor y evitando datos corruptos.
- **Manejo global de errores**: Uso de `@RestControllerAdvice` y el estándar `ProblemDetail` para respuestas de error consistentes.
- **Documentación interactiva**: Swagger/OpenAPI integrado para probar los endpoints.

## 🛠️ Requisitos Previos
Para ejecutar este proyecto, necesitas tener instalado:

JDK 21 (o superior).

Maven 3.8+.

Docker y Docker Compose (opcional, para despliegue en contenedores).

## 🚀 Ejecución Local (Maven)
Clona el repositorio y accede a la carpeta raíz del proyecto.

Compila el proyecto y descarga las dependencias:

	mvn clean install


Ejecuta la aplicación:

	mvn spring-boot:run


La aplicación estará disponible en

	http://localhost:8080.

Puedes acceder a la documentación interactiva de Swagger en:

	http://localhost:8080/swagger-ui.html


## 🐳 Construcción y Ejecución con Docker

El proyecto incluye un Dockerfile optimizado con Multi-Stage Build y un docker-compose.yml para levantar el entorno local.

Construye la imagen y levanta el contenedor en segundo plano:

	docker compose up --build -d

Verifica que el contenedor esté saludable (el healthcheck consulta Actuator):

	docker compose ps

Deberías ver el estado Up (healthy) después de unos 30 segundos.

Para ver los logs de la aplicación:

	docker compose logs -f ms-clientes

Para detener y eliminar el contenedor:

	docker compose down


## 🧪 Pruebas y Cobertura
El proyecto incluye pruebas unitarias (JUnit 5 + Mockito) para los casos de uso y pruebas de integración (@SpringBootTest + MockMvc) para los endpoints REST.

Para ejecutar las pruebas y generar el reporte de cobertura con JaCoCo:

	mvn clean test jacoco:report

El reporte HTML estará disponible en:

	target/site/jacoco/index.html. El build fallará si la cobertura de líneas baja del 80%.

## 📂 Estructura de Paquetes

```text
src/main/java/com/pruebatec/msclientes/
│
├── application/                  # Casos de uso (Orquestación)
│   └── service/                  # Implementación de los puertos de entrada
│
├── domain/                       # Núcleo de negocio (Sin dependencias de frameworks)
│   ├── exception/                # Excepciones de negocio (ej. ClienteNotFoundException)
│   ├── model/                    # Entidades de dominio (Cliente, Direccion)
│   │   └── valueobject/          # Value Objects inmutables (Documento, Email, TipoDocumento)
│   └── ports/                    # Interfaces (Contratos)
│       ├── in/                   # Puertos de entrada (Casos de Uso)
│       └── out/                  # Puertos de salida (Repositorios)
│
└── infrastructure/             # Adaptadores (Tecnología concreta)
    └── adapter/
        │
        ├── in/                 # Adaptadores de Entrada (Driving)
        │   └── web/            # API REST
        │       ├── dto/        # Records: ClienteRequestDTO, ClienteResponseDTO, etc.
        │       ├── exception/  # GlobalExceptionHandler (Manejo RFC 7807)
        │       ├── mapper/     # ClienteWebMapper (Conversión DTO <-> Dominio)
        │       └── ClienteController.java
        │
        └── out/                # Adaptadores de Salida (Driven)
            └── persistence/    # Base de datos H2
                ├── converter/  # DocumentoConverter (VO <-> String)
                ├── entity/     # ClienteEntity, DireccionEntity (Anotaciones JPA)
                ├── mapper/     # ClientePersistenceMapper (Dominio <-> Entity)
                ├── repository/ # JpaClienteRepository (Spring Data)
                └── ClienteRepositoryAdapter.java # Implementa el Puerto de Salida
```

## 📝 Ejemplo de Crear un Cliente

Para probar la creación de un cliente con sus direcciones, puedes usar el siguiente comando `curl` desde tu terminal:

```bash
curl -X 'POST' \
  'http://localhost:8080/api/clientes' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
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
}'
```

## 📝 Ejemplo de Listar Clientes

Para probar la creación de los cliente con sus direcciones, puedes usar el siguiente comando `curl` desde tu terminal y verificar lo guardado:

```bash
curl -X 'GET' \
  'http://localhost:8080/api/clientes' \
  -H 'accept: */*'
```

## 📝 Ejemplo de Consultar Cliente por ID

Puedes usar el siguiente comando `curl` desde tu terminal y verificar lo guardado:

```bash
curl -X 'GET' \
  'http://localhost:8080/api/clientes/1' \
  -H 'accept: */*'
```

## 📝 Ejemplo de Modificar Cliente 

Puedes usar el siguiente comando `curl` desde tu terminal y modificar lo guardado:

```bash
curl -X 'PUT' \
  'http://localhost:8080/api/clientes/1' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "nombre": "Juan2",
  "apellido": "Perez2",
  "numeroDocumento": "123456789",
  "tipoDocumento": "CC",
  "edad": 19
}'
```

## 📝 Ejemplo de Adicionar dirección al Cliente 

Puedes usar el siguiente comando `curl` desde tu terminal:

```bash
curl -X 'POST' \
  'http://localhost:8080/api/clientes/1/direcciones' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "direccionCompleta": "Calle 10 Nro 1-10 nueva",
  "ciudad": "Pereira",
  "departamento": "Risaralda"
}'
```

## 📝 Ejemplo de Eliminar dirección al Cliente 

Puedes usar el siguiente comando `curl` desde tu terminal:

```bash
curl -X 'DELETE' \
  'http://localhost:8080/api/clientes/1/direcciones/1' \
  -H 'accept: */*'
```
