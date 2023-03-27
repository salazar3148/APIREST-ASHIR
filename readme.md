# API REST - Hotel Ashir

Este es un proyecto de API REST para el Hotel Ashir. La API permite a los clientes buscar y reservar habitaciones en el hotel, así como registrarse en la base de datos del hotel. La API tiene tres entidades principales: Habitación, Cliente y Reserva.

## Entidades
### Habitación
Una habitación está definida por:

- Número
- Tipo de habitación (estándar o premium)
- Precio base

### Cliente
Un cliente está definido por:

- Nombre
- Apellido
- Cédula
- Dirección
- Edad

### Reserva
Una reserva está definida por:

- Código de reserva (generado automáticamente por el sistema)
- Fecha de reserva (solo se permite reservar por un día)
- Habitación
- Cliente
- Total a pagar

## Endpoints
La API tiene varios endpoints para manejar las operaciones necesarias.

### Registro de cliente

Este endpoint permite registrar un nuevo cliente en la base de datos.

```java
String: URL = api/v1/clientes 
```
Ejemplo de peticion:

```http
POST api/v1/clientes
Content-Type: application/json`
```

```json
{
  "cedula": "123456789",
  "nombre": "Juan",
  "apellido": "Perez",
  "direccion": "Calle 123",
  "edad": 30,
  "correo": "juanperez@mail.com"
}
```
<br>

> **Validaciones:** La cédula debe ser numérica y el nombre y los apellidos no pueden ser nulos.
