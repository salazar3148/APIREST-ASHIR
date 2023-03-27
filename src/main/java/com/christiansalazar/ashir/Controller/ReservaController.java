package com.christiansalazar.ashir.Controller;

import com.christiansalazar.ashir.Modelo.Habitacion;
import com.christiansalazar.ashir.Modelo.Reserva;
import com.christiansalazar.ashir.Modelo.ReservaRequest;
import com.christiansalazar.ashir.Service.ReservaService;
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

    @GetMapping("reservas/{fecha}")
    public ResponseEntity<List<Habitacion>> buscarDisponibles(@PathVariable String fecha){
        return new ResponseEntity<>(reservaService.habitacionesDisponibles(fecha), HttpStatus.ACCEPTED);
    }

    @GetMapping("reservas")
    public ResponseEntity<List<Habitacion>> buscarDisponiblesPorTipo(@RequestParam String fecha, @RequestParam String tipo){
        return new ResponseEntity<>(reservaService.habitacionesDisponibles(fecha, tipo), HttpStatus.ACCEPTED);
    }

    @PostMapping("reservas")
    public ResponseEntity<Reserva> reservar(@RequestBody ReservaRequest reservaRequest){
        return new ResponseEntity<>(reservaService.reservar(reservaRequest), HttpStatus.ACCEPTED);
    }

    @GetMapping("clientes/{cedulaCliente}")
    public ResponseEntity<List<Reserva>> reservasCliente(@PathVariable Long cedulaCliente){
        return new ResponseEntity<>(reservaService.reservasCliente(cedulaCliente), HttpStatus.ACCEPTED);
    }

}
