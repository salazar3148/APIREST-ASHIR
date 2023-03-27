package com.christiansalazar.ashir.Service;

import com.christiansalazar.ashir.Excepciones.ApiRequestException;
import com.christiansalazar.ashir.Modelo.Cliente;
import com.christiansalazar.ashir.Modelo.Habitacion;
import com.christiansalazar.ashir.Modelo.Reserva;
import com.christiansalazar.ashir.Modelo.ReservaRequest;
import com.christiansalazar.ashir.Repository.ClienteRepository;
import com.christiansalazar.ashir.Repository.HabitacionRepository;
import com.christiansalazar.ashir.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private ReservaRepository reservaRepository;
    private HabitacionRepository habitacionRepository;
    private ClienteRepository clienteRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, HabitacionRepository habitacionRepository, ClienteRepository clienteRepository) {
        this.reservaRepository = reservaRepository;
        this.habitacionRepository = habitacionRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Habitacion> habitacionesDisponibles(String fecha){
        String validar = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$";

        if(!fecha.matches(validar)) throw new ApiRequestException("invalid date format");
        int[] fecha_dividida = Arrays.stream(fecha.split("-")).mapToInt(Integer::parseInt).toArray();

        LocalDate date;

        try {
            date = LocalDate.of(fecha_dividida[0], fecha_dividida[1], fecha_dividida[2]);
        } catch (DateTimeException e) {
            throw new ApiRequestException(e.getMessage());
        }

        Set<Habitacion> ocupados = new HashSet<>();
        reservaRepository.findAll().forEach(reserva -> {
            if(reserva.getFecha().equals(date)) ocupados.add(reserva.getHabitacion());
        });

        List<Habitacion> libres = new ArrayList<>();

        habitacionRepository.findAll().forEach(habitacion -> {
            if(!ocupados.contains(habitacion)) libres.add(habitacion);
        });
        return libres;
    }
    public List<Habitacion> habitacionesDisponibles(String fecha, String tipo) {
        List<Habitacion> disponibles = habitacionesDisponibles(fecha).
                stream().
                filter(habitacion -> habitacion.getTipo().equals(tipo.toUpperCase()))
                .collect(Collectors.toList());
        return disponibles;
    }

    public Reserva reservar(ReservaRequest reservaRequest){
        Optional<Cliente> cliente = clienteRepository.findById(reservaRequest.getCedulaCliente());
        if(!cliente.isPresent()) throw new ApiRequestException("user not found");

        Optional<Habitacion> habitacion = habitacionRepository.findById(reservaRequest.getNumeroHabitacion());
        if(!habitacion.isPresent()) throw new ApiRequestException("room not found");

        if(reservaRequest.getFecha() == null) throw new ApiRequestException("null date not allowed");
        if(reservaRequest.getFecha().isBefore(LocalDate.now())) throw new ApiRequestException("date before today");


        Optional<Habitacion> habitacionAReservar = habitacionesDisponibles(reservaRequest.getFecha().toString())
                .stream()
                .filter(hab -> hab.getNumero() == reservaRequest.getNumeroHabitacion())
                .findFirst();

        if(!habitacionAReservar.isPresent()) throw new ApiRequestException("room not available");

        Integer total = habitacionAReservar.get().getPrecioBase();

        if(habitacionAReservar.get().getTipo().equalsIgnoreCase("premium")) total = (int) (total * 0.95);
        Reserva reserva = new Reserva(habitacion.get(), cliente.get(), reservaRequest.getFecha(), total);

        reservaRepository.save(reserva);

        return reserva;
    }

    public List<Reserva> reservasCliente(Long cedulaCliente) {
        List<Reserva> reservas = new ArrayList();
        reservaRepository.findAll().forEach(reserva -> {
            if(reserva.getCliente().getCedula().equals(cedulaCliente)) reservas.add(reserva);
        });
        return reservas;
    }
}
