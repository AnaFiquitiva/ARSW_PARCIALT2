Parcial Práctico Corte 2 - REST API Blueprints
===============================================

REST API para la gestión de planos (Blueprints) desarrollada con Java 21 y Spring Boot 3.3.x, implementando buenas prácticas de diseño de APIs REST.

**Escuela Colombiana de Ingeniería - Arquitecturas de Software**

---

## Requisitos

- Java 21 o superior
- Maven 3.9 o superior
- Git

## Instalación y Compilación

1. Clonar el repositorio:
```bash
git clone https://github.com/AnaFiquitiva/ARSW_PARCIALT2.git
cd ARSW_PARCIALT2
```

2. Compilar el proyecto:
```bash
mvn clean install
```

3. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:8080

## Arquitectura y Estructura del Proyecto

```
src/main/java/edu/eci/arsw/blueprints/
├── BlueprintsApplication.java       # Clase principal de Spring Boot
├── config/
│   └── OpenApiConfig.java           # Configuración de OpenAPI/Swagger
├── controllers/
│   ├── BlueprintsAPIController.java # Controlador REST principal
│   └── GlobalExceptionHandler.java  # Manejador global de excepciones
├── dto/
│   └── ApiResponse.java             # Clase genérica de respuesta uniforme
├── model/
│   ├── Blueprint.java               # Entidad de dominio: Plano
│   └── Point.java                   # Entidad de dominio: Punto
├── persistence/
│   ├── BlueprintPersistence.java    # Interfaz de persistencia
│   ├── BlueprintNotFoundException.java
│   ├── BlueprintPersistenceException.java
│   └── InMemoryBlueprintPersistence.java # Implementación en memoria
├── services/
│   └── BlueprintsServices.java      # Lógica de negocio
└── filters/
    ├── BlueprintsFilter.java        # Interfaz de filtros
    ├── IdentityFilter.java          # Filtro identidad
    ├── RedundancyFilter.java        # Filtro de redundancia
    └── UndersamplingFilter.java     # Filtro de submuestreo
```

## Actividades Implementadas

### 1. Buenas Prácticas de API REST - Path Base Versionado

Path base: `/api/v1/blueprints`

Todos los endpoints usan este path base para mantener versionado y consistencia en la API.

```java
@RequestMapping("/api/v1/blueprints")
```

### 2. Códigos HTTP Correctos

Cada operación retorna el código HTTP apropiado:

- **GET**: 200 OK (exitosa) o 404 Not Found (no existe)
- **POST**: 201 Created (creada) o 400 Bad Request (error validación)
- **PUT**: 202 Accepted (aceptada) o 404 Not Found (no existe)

### 3. Respuesta Estándar Uniforme

Todas las respuestas siguen esta estructura:

```java
public record ApiResponse<T>(int code, String message, T data) {}
```

Ejemplo JSON:
```json
{
    "code": 200,
    "message": "execute ok",
    "data": { "author": "john", "name": "house" }
}
```

Se implementaron factory methods para cada código:
- `ok()` - código 200
- `created()` - código 201
- `accepted()` - código 202
- `badRequest()` - código 400
- `notFound()` - código 404

### 4. Documentación OpenAPI/Swagger

Configuración de springdoc-openapi con:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Todos los endpoints están documentados con `@Operation` y `@ApiResponses` mostrando códigos HTTP posibles y descripciones.

## Ejemplos de Uso

### Obtener todos los planos
```bash
curl -X GET http://localhost:8080/api/v1/blueprints
```

### Obtener planos de un autor
```bash
curl -X GET http://localhost:8080/api/v1/blueprints/john
```

### Obtener un plano específico
```bash
curl -X GET http://localhost:8080/api/v1/blueprints/john/house
```

### Crear un nuevo plano
```bash
curl -X POST http://localhost:8080/api/v1/blueprints \
  -H "Content-Type: application/json" \
  -d '{
    "author": "john",
    "name": "kitchen",
    "points": [
      {"x": 1, "y": 1},
      {"x": 2, "y": 2}
    ]
  }'
```

### Agregar un punto a un plano
```bash
curl -X PUT http://localhost:8080/api/v1/blueprints/john/kitchen/points \
  -H "Content-Type: application/json" \
  -d '{"x": 3, "y": 3}'
```

## Manejo de Excepciones

Se ha implementado un manejador global de excepciones (`GlobalExceptionHandler`) que:

1. Captura validaciones fallidas (MethodArgumentNotValidException)
2. Retorna respuestas uniformes con código 400
3. Proporciona detalles de los errores de validación

Ejemplo de respuesta de error:
```json
{
    "code": 400,
    "message": "Validation failed",
    "data": {
        "author": "must not be blank",
        "name": "must not be blank"
    }
}
```

## Tecnologías Utilizadas

- Java 21
- Spring Boot 3.3.9
- Spring Web
- Spring Validation
- Springdoc OpenAPI 2.6.0
- Maven 3.9+

## Dependencias Principales

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

## Configuración

La aplicación se ejecuta por defecto en el puerto 8080. Es posible cambiar este puerto en `src/main/resources/application.properties`:

```properties
server.port=8080
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
```

## Entregables

Este proyecto cumple con los siguientes requisitos:

1. Código fuente actualizado con:
   - Path base versionado: /api/v1/blueprints
   - Códigos HTTP correctos implementados
   - Clase ApiResponse<T> genérica implementada
   - Manejador global de excepciones

2. Documentación OpenAPI/Swagger:
   - Configuración personalizada en OpenApiConfig
   - Anotaciones @Operation y @ApiResponses en todos los métodos
   - UI disponible en /swagger-ui.html

3. Repositorio en GitHub:
   - URL: https://github.com/AnaFiquitiva/ARSW_PARCIALT2
   - Commits limpios y descriptivos
   - Código compilable y ejecutable

## Notas de Implementación

- Se utilizan records de Java para las entidades de respuesta y solicitud
- Se implementa inyección de dependencias a través del constructor
- Se sigue el patrón de capas: Model, Persistence, Services, Controllers
- Validación de entrada con anotaciones de Jakarta Validation
- Respuestas uniformes para facilitar el consumo de la API en clientes

## Autor

Ana Fiquitiva

## Licencia

MIT License

---

**Última actualización:** 27 de marzo de 2026