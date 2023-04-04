package com.christiansalazar.ashir.Service;

import com.christiansalazar.ashir.DTO.HabitacionDTO;
import com.christiansalazar.ashir.DTO.ReservaDTO;
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

    public List<HabitacionDTO> habitacionesDisponibles(String fecha){
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
        reservaRepository.findAll().stream()
                        .filter(reserva -> reserva.getFecha().equals(date))
                        .forEach(reserva -> ocupados.add(reserva.getHabitacion()));

        List<HabitacionDTO> libres = habitacionRepository.findAll()
                        .stream()
                        .filter(habitacion -> !ocupados.contains(habitacion))
                        .map(habitacion -> new HabitacionDTO(habitacion.getNumero(), habitacion.getTipo()))
                        .collect(Collectors.toList());
        return libres;
    }
    public List<HabitacionDTO> habitacionesDisponibles(String fecha, String tipo) {
        Set<String> tipos = Set.of("ESTANDAR", "PREMIUM");

        if(!tipos.contains(tipo.toUpperCase())) throw new ApiRequestException("type room not valid");

        List<HabitacionDTO> disponibles = habitacionesDisponibles(fecha).
                stream().
                filter(habitacion -> habitacion.getTipo().equals(tipo.toUpperCase()))
                .collect(Collectors.toList());
        return disponibles;
    }

    public ReservaDTO reservar(ReservaRequest reservaRequest){
        Optional<Cliente> cliente = clienteRepository.findById(reservaRequest.getCedulaCliente());
        if(!cliente.isPresent()) throw new ApiRequestException("user not found");

        Optional<Habitacion> habitacion = habitacionRepository.findById(reservaRequest.getNumeroHabitacion());
        if(!habitacion.isPresent()) throw new ApiRequestException("room not found");

        if(reservaRequest.getFecha() == null) throw new ApiRequestException("null date not allowed");
        if(reservaRequest.getFecha().isBefore(LocalDate.now())) throw new ApiRequestException("date before today");

        Optional<HabitacionDTO> habitacionAReservar = habitacionesDisponibles(reservaRequest.getFecha().toString())
                .stream()
                .filter(hab -> hab.getNumero().equals(reservaRequest.getNumeroHabitacion()))
                .findFirst();

        if(!habitacionAReservar.isPresent()) throw new ApiRequestException("room not available");

        Integer total = habitacion.get().getPrecioBase();

        if(habitacionAReservar.get().getTipo().equalsIgnoreCase("premium")) total = (int) (total * 0.95);
        Reserva reserva = new Reserva(habitacion.get(), cliente.get(), reservaRequest.getFecha(), total);

        ReservaDTO reservaDTO = new ReservaDTO(
                reserva.getCodigo(),
                reserva.getHabitacion().getNumero(),
                reserva.getCliente().getCedula(),
                reserva.getFecha(),
                reserva.getTotal()
        );

        reservaRepository.save(reserva);
        return reservaDTO;
    }

    public List<ReservaDTO> reservasCliente(Long cedulaCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(cedulaCliente);
        if(!cliente.isPresent()) throw new ApiRequestException("user not found");

        List<ReservaDTO> reservas = reservaRepository.findAll()
                .stream()
                .filter(reserva -> reserva.getCliente().getCedula().equals(cedulaCliente))
                .map(reserva -> new ReservaDTO(
                        reserva.getCodigo(),
                        reserva.getHabitacion().getNumero(),
                        reserva.getCliente().getCedula(),
                        reserva.getFecha(),
                        reserva.getTotal()
                ))
                .collect(Collectors.toList());
        return reservas;
    }
}
