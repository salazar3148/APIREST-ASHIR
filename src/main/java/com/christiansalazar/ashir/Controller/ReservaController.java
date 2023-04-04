package com.christiansalazar.ashir.Controller;

import com.christiansalazar.ashir.DTO.HabitacionDTO;
import com.christiansalazar.ashir.DTO.ReservaDTO;
import com.christiansalazar.ashir.Modelo.Cliente;
import com.christiansalazar.ashir.Modelo.Habitacion;
import com.christiansalazar.ashir.Modelo.Reserva;
import com.christiansalazar.ashir.Modelo.ReservaRequest;
import com.christiansalazar.ashir.Service.ReservaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ReservaController {

    private ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Habitaciones obtenidas con exito"),
            @ApiResponse(code = 400, message = "Parametros invalidos"),
            @ApiResponse(code = 500, message = "Ocurrió un error en el servidor")
    })
    @ApiOperation(value = "Este endpoint permite buscar habitaciones disponibles por fecha",
            notes = "Validaciones:\n- La fecha debe ser en formato Aa-Mm-Dd",
            response = Cliente.class)
    @GetMapping("reservas/{fecha}")
    public ResponseEntity<List<HabitacionDTO>> buscarDisponibles(@ApiParam("Este parametro requiere la fecha")@PathVariable String fecha){
        return new ResponseEntity<>(reservaService.habitacionesDisponibles(fecha), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Habitaciones obtenidas con exito"),
            @ApiResponse(code = 400, message = "Parametros invalidos"),
            @ApiResponse(code = 500, message = "Ocurrió un error en el servidor")
    })
    @ApiOperation(value = "Este endpoint permite buscar habitaciones disponibles por fecha y tipo de habitacion",
            notes = "Validaciones:\n1. La fecha debe ser en formato Aa-Mm-Dd\n2. Solo debe ingresar 2 tipos de habitaciones (Estandar - Premium)",
            response = Cliente.class)
    @GetMapping("reservas")
    public ResponseEntity<List<HabitacionDTO>> buscarDisponiblesPorTipo(@ApiParam("Este parametro requiere la fecha")@RequestParam String fecha, @ApiParam(value="Este parametro requiere el tipo de habitacion") @RequestParam String tipo){
        return new ResponseEntity<>(reservaService.habitacionesDisponibles(fecha, tipo), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Reservas obtenidas con exito"),
            @ApiResponse(code = 400, message = "Parametros invalidos"),
            @ApiResponse(code = 500, message = "Ocurrió un error en el servidor")
    })
    @ApiOperation(value = "Este endpoint muestra un listado de todas las reservas que ha hecho un cliente",
            notes = "Validaciones:\n1. La fecha debe ser en formato Aa-Mm-Dd\n2. Solo debe ingresar 2 tipos de habitaciones (Estandar - Premium)",
            response = Cliente.class)
    @GetMapping("clientes/{cedulaCliente}")
    public ResponseEntity<List<ReservaDTO>> reservasCliente(@ApiParam("Este parametro requiere la cedula")@PathVariable Long cedulaCliente){
        return new ResponseEntity<>(reservaService.reservasCliente(cedulaCliente), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value={
            @ApiResponse(code = 201, message = "Reserva realizada con exito"),
            @ApiResponse(code = 400, message = "Parametros invalidos"),
            @ApiResponse(code = 500, message = "Ocurrió un error en el servidor")
    })
    @ApiOperation(value = "Este endpoint permite hacer una reserva en el hotel",
            notes = "Validaciones:\n1. La fecha debe ser en formato Aa-Mm-Dd" +
                    "\n2. El cliente debe estar debidamente registrado" +
                    "\n3. La habitación tiene que existir y debe estar disponible para esa fecha" +
                    "\n4. No se debe ingresar un dia anterior a hoy" +
                    "\n- A continuación se muestra como debe ser el cuerpo de la petición",
            response = Cliente.class)
    @PostMapping("reservas")
    public ResponseEntity<ReservaDTO> reservar(@RequestBody ReservaRequest reservaRequest){
        return new ResponseEntity<>(reservaService.reservar(reservaRequest), HttpStatus.CREATED);
    }


}
