# 📚 Biblioteca API

> API REST completa para la gestión de una biblioteca, construida con **Spring Boot 3** y **Java 17**.

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen?logo=springboot)
![H2](https://img.shields.io/badge/H2-Database-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## 📋 Tabla de contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Requisitos](#requisitos)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Endpoints de la API](#endpoints-de-la-api)
- [Modelos de datos](#modelos-de-datos)
- [Reglas de negocio](#reglas-de-negocio)
- [Configuración para producción](#configuración-para-producción)
- [Swagger UI](#swagger-ui)

---

## ✨ Características

- ✅ CRUD completo de **libros**, **autores**, **socios** y **préstamos**
- ✅ Control de **ejemplares disponibles** en tiempo real
- ✅ Sistema de **préstamos y devoluciones** con validaciones
- ✅ **Renovación** de préstamos activos
- ✅ Detección automática de **préstamos vencidos**
- ✅ **Búsqueda** por título, autor, ISBN, nombre de socio
- ✅ Filtros por género, disponibilidad, estado y nacionalidad
- ✅ Respuestas estandarizadas con `ApiResponse<T>`
- ✅ Manejo global de excepciones y validaciones
- ✅ Documentación interactiva con **Swagger UI**
- ✅ Base de datos H2 en memoria para desarrollo (sin configuración)
- ✅ Compatible con **MySQL** para producción

---

## 🛠 Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje |
| Spring Boot | 3.2.3 | Framework principal |
| Spring Data JPA | 3.2.3 | Persistencia |
| Spring Validation | 3.2.3 | Validaciones |
| H2 Database | Runtime | Base de datos en memoria (dev) |
| MySQL Connector | Runtime | Base de datos producción |
| Lombok | Latest | Reducción de boilerplate |
| SpringDoc OpenAPI | 2.3.0 | Documentación Swagger |
| Maven | 3.x | Gestión de dependencias |

---

## ⚙️ Requisitos

- **Java 17** o superior
- **Maven 3.6+**
- (Opcional) **MySQL 8** para producción

---

## 🚀 Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/biblioteca-api.git
cd biblioteca-api
```

### 2. Compilar el proyecto

```bash
mvn clean install -DskipTests
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La API arrancará en `http://localhost:8080` con datos de ejemplo precargados automáticamente.

### 4. Verificar que funciona

```bash
curl http://localhost:8080/api/v1/libros
```

---

## 📁 Estructura del proyecto

```
biblioteca-api/
├── src/
│   ├── main/
│   │   ├── java/com/biblioteca/
│   │   │   ├── BibliotecaApiApplication.java   # Punto de entrada
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java          # Configuración Swagger
│   │   │   ├── controller/
│   │   │   │   ├── AutorController.java
│   │   │   │   ├── LibroController.java
│   │   │   │   ├── SocioController.java
│   │   │   │   └── PrestamoController.java
│   │   │   ├── service/
│   │   │   │   ├── AutorService.java
│   │   │   │   ├── LibroService.java
│   │   │   │   ├── SocioService.java
│   │   │   │   └── PrestamoService.java
│   │   │   ├── repository/
│   │   │   │   ├── AutorRepository.java
│   │   │   │   ├── LibroRepository.java
│   │   │   │   ├── SocioRepository.java
│   │   │   │   └── PrestamoRepository.java
│   │   │   ├── model/
│   │   │   │   ├── Autor.java
│   │   │   │   ├── Libro.java
│   │   │   │   ├── Socio.java
│   │   │   │   └── Prestamo.java
│   │   │   ├── dto/
│   │   │   │   ├── AutorDTO.java
│   │   │   │   ├── LibroDTO.java
│   │   │   │   ├── SocioDTO.java
│   │   │   │   ├── PrestamoDTO.java
│   │   │   │   └── ApiResponse.java
│   │   │   └── exception/
│   │   │       ├── ResourceNotFoundException.java
│   │   │       ├── BusinessException.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql                        # Datos iniciales de ejemplo
│   └── test/
│       └── java/com/biblioteca/
│           └── BibliotecaApiApplicationTests.java
└── pom.xml
```

---

## 🔌 Endpoints de la API

### 📖 Autores — `/api/v1/autores`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/autores` | Listar todos los autores |
| GET | `/api/v1/autores/{id}` | Obtener autor por ID |
| GET | `/api/v1/autores/buscar?q={texto}` | Buscar por nombre/apellido |
| GET | `/api/v1/autores/nacionalidad/{nac}` | Filtrar por nacionalidad |
| POST | `/api/v1/autores` | Registrar nuevo autor |
| PUT | `/api/v1/autores/{id}` | Actualizar autor |
| DELETE | `/api/v1/autores/{id}` | Eliminar autor |

### 📚 Libros — `/api/v1/libros`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/libros` | Listar todos los libros |
| GET | `/api/v1/libros/{id}` | Obtener libro por ID |
| GET | `/api/v1/libros/isbn/{isbn}` | Buscar por ISBN |
| GET | `/api/v1/libros/buscar?q={texto}` | Búsqueda global |
| GET | `/api/v1/libros/disponibles` | Libros con stock disponible |
| GET | `/api/v1/libros/genero/{genero}` | Filtrar por género |
| GET | `/api/v1/libros/autor/{autorId}` | Libros de un autor |
| POST | `/api/v1/libros` | Registrar nuevo libro |
| PUT | `/api/v1/libros/{id}` | Actualizar libro |
| DELETE | `/api/v1/libros/{id}` | Eliminar libro |

### 👤 Socios — `/api/v1/socios`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/socios` | Listar todos los socios |
| GET | `/api/v1/socios/{id}` | Obtener socio por ID |
| GET | `/api/v1/socios/buscar?q={texto}` | Buscar por nombre |
| GET | `/api/v1/socios/activos` | Solo socios activos |
| POST | `/api/v1/socios` | Registrar nuevo socio |
| PUT | `/api/v1/socios/{id}` | Actualizar socio |
| PATCH | `/api/v1/socios/{id}/toggle-activo` | Activar/desactivar |
| DELETE | `/api/v1/socios/{id}` | Eliminar socio |

### 🔄 Préstamos — `/api/v1/prestamos`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/prestamos` | Listar todos los préstamos |
| GET | `/api/v1/prestamos/{id}` | Obtener préstamo por ID |
| GET | `/api/v1/prestamos/socio/{socioId}` | Préstamos de un socio |
| GET | `/api/v1/prestamos/libro/{libroId}` | Préstamos de un libro |
| GET | `/api/v1/prestamos/vencidos` | Préstamos vencidos |
| GET | `/api/v1/prestamos/estado/{estado}` | Filtrar por estado |
| POST | `/api/v1/prestamos` | Crear nuevo préstamo |
| PATCH | `/api/v1/prestamos/{id}/devolver` | Registrar devolución |
| PATCH | `/api/v1/prestamos/{id}/renovar` | Renovar préstamo |

---

## 📦 Modelos de datos

### Autor
```json
{
  "nombre": "Gabriel",
  "apellido": "García Márquez",
  "nacionalidad": "Colombiana",
  "fechaNacimiento": "1927-03-06",
  "biografia": "Premio Nobel de Literatura 1982..."
}
```

### Libro
```json
{
  "titulo": "Cien años de soledad",
  "isbn": "978-0-06-088328-7",
  "anioPublicacion": 1967,
  "genero": "FICCION",
  "descripcion": "La novela cumbre del realismo mágico...",
  "totalEjemplares": 5,
  "autorId": 1
}
```

**Géneros disponibles:** `FICCION`, `NO_FICCION`, `CIENCIA_FICCION`, `FANTASIA`, `ROMANCE`, `MISTERIO`, `TERROR`, `BIOGRAFIA`, `HISTORIA`, `CIENCIA`, `TECNOLOGIA`, `ARTE`, `FILOSOFIA`, `REALISMO_MAGICO`, `INFANTIL`, `OTRO`

### Socio
```json
{
  "nombre": "Carlos",
  "apellido": "Martínez",
  "email": "carlos@email.com",
  "telefono": "612345678",
  "direccion": "Calle Mayor 10, Madrid"
}
```

### Préstamo
```json
{
  "libroId": 1,
  "socioId": 2,
  "fechaDevolucionPrevista": "2025-04-15",
  "observaciones": "Estado del libro: bueno"
}
```

**Estados de préstamo:** `ACTIVO`, `DEVUELTO`, `VENCIDO`, `RENOVADO`

---

## 📏 Reglas de negocio

| Regla | Detalle |
|-------|---------|
| Límite de préstamos | Máximo **3 préstamos activos** por socio |
| Plazo por defecto | **15 días** si no se especifica fecha de devolución |
| Socio activo | Solo socios con estado `activo = true` pueden pedir préstamos |
| Stock | El libro debe tener `ejemplaresDisponibles > 0` |
| Duplicado | Un socio no puede tener 2 préstamos activos del mismo libro |
| Eliminación autor | No se puede eliminar un autor con libros registrados |
| Eliminación socio | No se puede eliminar un socio con préstamos activos |
| Eliminación libro | No se puede eliminar un libro con préstamos activos |

---

## 🗄️ Configuración para producción (MySQL)

En `application.properties`, descomenta y ajusta:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=false
```

Crea la base de datos en MySQL:
```sql
CREATE DATABASE biblioteca_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

---

## 📖 Swagger UI

Una vez la aplicación esté corriendo, accede a la documentación interactiva:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/api-docs
- **H2 Console (dev):** http://localhost:8080/h2-console

---

## 📄 Licencia

Este proyecto está bajo la licencia [MIT](LICENSE).
