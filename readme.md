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

#### Toda la documentación en Swagger
