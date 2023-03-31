package com.christiansalazar.ashir;

import com.christiansalazar.ashir.Excepciones.ApiRequestException;
import com.christiansalazar.ashir.Modelo.Cliente;
import com.christiansalazar.ashir.Modelo.Habitacion;
import com.christiansalazar.ashir.Modelo.Reserva;
import com.christiansalazar.ashir.Modelo.ReservaRequest;
import com.christiansalazar.ashir.Repository.ClienteRepository;
import com.christiansalazar.ashir.Repository.HabitacionRepository;
import com.christiansalazar.ashir.Repository.ReservaRepository;
import com.christiansalazar.ashir.Service.ReservaService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservaServiceTest {

    ReservaService reservaService;
    ClienteRepository clienteRepository;
    ReservaRepository reservaRepository;
    HabitacionRepository habitacionRepository;
    @Before
    public void setUp(){

        this.clienteRepository = mock(ClienteRepository.class);
        this.reservaRepository = mock(ReservaRepository.class);
        this.habitacionRepository = mock(HabitacionRepository.class);
        this.reservaService = new ReservaService(reservaRepository, habitacionRepository, clienteRepository);
    }

    @Test(expected = ApiRequestException.class)
    public void habitacionesDisponiblesIngresandoFechaFormatoInvalido(){
        //Arrange
        String fecha = "02-02-2023";

        //Act & Assert
        reservaService.habitacionesDisponibles(fecha);
    }

    @Test(expected = ApiRequestException.class)
    public void habitacionesDisponiblesIngresandoFechaRangoInvalido(){
        //Arrange
        String fecha = "2023-02-30";

        //Act & Assert
        reservaService.habitacionesDisponibles(fecha);
    }

    @Test
    public void habitacionDisponibleConFechaValida(){
        //Arrange
        String fecha = "2023-05-02";
        Habitacion habitacion1 = new Habitacion(1, "premium", 5000);
        Habitacion habitacion2 = new Habitacion(2, "premium", 5000);
        Habitacion habitacion3 = new Habitacion(3, "normal", 5000);

        Integer idUnicaHabitacionDisponible = 3;

        Iterable<Reserva> reservas = Arrays.asList(
                new Reserva(habitacion1, null, LocalDate.of(2023, 5, 2), null),
                new Reserva(habitacion2, null, LocalDate.of(2023, 5, 2), null),
                new Reserva(habitacion3, null, LocalDate.of(2023, 4, 2), null)
        );

        Iterable<Habitacion> habitaciones = Arrays.asList(
                habitacion1,
                habitacion2,
                habitacion3
        );

        when(reservaRepository.findAll()).thenReturn(reservas);
        when(habitacionRepository.findAll()). thenReturn(habitaciones);

        //Act

        List<Habitacion> habitacionsDisponibles = this.reservaService.habitacionesDisponibles(fecha);

        //Assert
        assertSame(habitacionsDisponibles.get(0).getNumero(), idUnicaHabitacionDisponible);
    }
    @Test(expected = ApiRequestException.class)
    public void habitacionDisponibleConFechaValidaYTipoInvalido(){
        //Arrange
        String fecha = "2023-05-02";
        String tipo = "suite";

        //Act & Assert
        this.reservaService.habitacionesDisponibles(fecha, tipo);
    }

    @Test
    public void habitacionDisponibleConTodoCorrecto(){
        //Arrange
        String fecha = "2023-05-02";
        String tipoEstandar = "estandar";
        String tipoPremium = "premium";

        Habitacion habitacion1 = new Habitacion(1, "PREMIUM", 5000);
        Habitacion habitacion2 = new Habitacion(2, "PREMIUM", 5000);
        Habitacion habitacion3 = new Habitacion(3, "ESTANDAR", 5000);
        Habitacion habitacion4 = new Habitacion(4, "ESTANDAR", 5000);


        Iterable<Reserva> reservas = Arrays.asList(
                new Reserva(habitacion1, null, LocalDate.of(2023, 5, 2), null),
                new Reserva(habitacion2, null, LocalDate.of(2023, 5, 2), null),
                new Reserva(habitacion3, null, LocalDate.of(2023, 4, 2), null),
                new Reserva(habitacion4, null, LocalDate.of(2023, 4, 2), null)
        );

        Iterable<Habitacion> habitaciones = Arrays.asList(
                habitacion1,
                habitacion2,
                habitacion3,
                habitacion4
        );

        when(reservaRepository.findAll()).thenReturn(reservas);
        when(habitacionRepository.findAll()). thenReturn(habitaciones);

        //Act

        List<Habitacion> habitacionsDisponiblesEstandar = this.reservaService.habitacionesDisponibles(fecha, tipoEstandar);
        List<Habitacion> habitacionsDisponiblesPremium = this.reservaService.habitacionesDisponibles(fecha, tipoPremium);

        //Assert
        assertEquals(2, habitacionsDisponiblesEstandar.size());
        assertEquals(0, habitacionsDisponiblesPremium.size());
    }

    @Test(expected = ApiRequestException.class)
    public void reservarConClienteNuloONoexistente(){
        //Arrange
        ReservaRequest reservaRequest = new ReservaRequest(null, null, LocalDate.of(2023, 5, 3));

        when(clienteRepository.findById(any())).thenReturn(Optional.empty());
        when(habitacionRepository.findById(any())).thenReturn(Optional.of(new Habitacion()));
        //Act
        this.reservaService.reservar(reservaRequest);
    }

    @Test(expected = ApiRequestException.class)
    public void reservarConHabitacionNulaONoexistente(){
        //Arrange
        ReservaRequest reservaRequest = new ReservaRequest(null, null, LocalDate.of(2023, 5, 3));

        when(clienteRepository.findById(any())).thenReturn(Optional.of(new Cliente()));
        when(habitacionRepository.findById(any())).thenReturn(Optional.empty());
        //Act
        this.reservaService.reservar(reservaRequest);
    }

    @Test(expected = ApiRequestException.class)
    public void reservarConFechaNula(){
        //Arrange
        ReservaRequest reservaRequest = new ReservaRequest(null, null, null);

        when(clienteRepository.findById(any())).thenReturn(Optional.of(new Cliente()));
        when(habitacionRepository.findById(any())).thenReturn(Optional.of(new Habitacion()));

        //Act
        this.reservaService.reservar(reservaRequest);
    }

    @Test(expected = ApiRequestException.class)
    public void reservarConFechaAntesDeHoy() {
        //Arrange
        Habitacion habitacion1 = new Habitacion(1, "PREMIUM", 5000);
        Habitacion habitacion2 = new Habitacion(2, "PREMIUM", 5000);
        Habitacion habitacion3 = new Habitacion(3, "ESTANDAR", 5000);
        Habitacion habitacion4 = new Habitacion(4, "ESTANDAR", 5000);


        Iterable<Reserva> reservas = Arrays.asList(
                new Reserva(habitacion1, null, LocalDate.of(2023, 5, 2), null),
                new Reserva(habitacion2, null, LocalDate.of(2023, 5, 2), null),
                new Reserva(habitacion3, null, LocalDate.of(2023, 4, 2), null),
                new Reserva(habitacion4, null, LocalDate.of(2023, 4, 2), null)
        );

        Iterable<Habitacion> habitaciones = Arrays.asList(
                habitacion1,
                habitacion2,
                habitacion3,
                habitacion4
        );

        ReservaRequest reservaRequest = new ReservaRequest(null, 4, LocalDate.of(2023, 3, 29));

        when(reservaRepository.findAll()).thenReturn(reservas);
        when(habitacionRepository.findAll()). thenReturn(habitaciones);

        when(clienteRepository.findById(any())).thenReturn(Optional.of(new Cliente()));
        when(habitacionRepository.findById(any())).thenReturn(Optional.of(habitacion4));

        //Act
        this.reservaService.reservar(reservaRequest);
    }

    @Test(expected = ApiRequestException.class)
    public void reservarUnaHabitacionOcupada() {
        //Arrange
        Habitacion habitacion1 = new Habitacion(1, "PREMIUM", 5000);
        Habitacion habitacion2 = new Habitacion(2, "PREMIUM", 5000);
        Habitacion habitacion3 = new Habitacion(3, "ESTANDAR", 5000);
        Habitacion habitacion4 = new Habitacion(4, "ESTANDAR", 5000);


        Iterable<Reserva> reservas = Arrays.asList(
                new Reserva(habitacion1, null, LocalDate.of(2023, 5, 2), null),
                new Reserva(habitacion2, null, LocalDate.of(2023, 5, 2), null),
                new Reserva(habitacion3, null, LocalDate.of(2023, 4, 2), null),
                new Reserva(habitacion4, null, LocalDate.of(2023, 4, 2), null)
        );

        Iterable<Habitacion> habitaciones = Arrays.asList(
                habitacion1,
                habitacion2,
                habitacion3,
                habitacion4
        );

        ReservaRequest reservaRequest = new ReservaRequest(null, 1, LocalDate.of(2023, 5, 2));

        when(reservaRepository.findAll()).thenReturn(reservas);
        when(habitacionRepository.findAll()). thenReturn(habitaciones);

        when(clienteRepository.findById(any())).thenReturn(Optional.of(new Cliente()));
        when(habitacionRepository.findById(any())).thenReturn(Optional.of(habitacion1));

        //Act
        this.reservaService.reservar(reservaRequest);
    }

    @Test(expected = ApiRequestException.class)
    public void reservasdeClienteConCedulaNoExistente() {
        //Arrange
        Long cedulaNoExistente = 4561003L;
        when(clienteRepository.findById(any())).thenReturn(Optional.empty());

        //Act
        List<Reserva> reservasFrancisco = this.reservaService.reservasCliente(cedulaNoExistente);
    }

    @Test
    public void reservasCliente() {
        //Arrange
        Cliente francisco = new Cliente(100L, "francisco", null, null, null, null);

        Cliente fernando = new Cliente(200L, "fernando", null, null, null, null);

        Iterable<Reserva> reservas = Arrays.asList(
                new Reserva(null, francisco, LocalDate.of(2023, 5, 2), null),
                new Reserva(null, francisco, LocalDate.of(2023, 5, 3), null),
                new Reserva(null, francisco, LocalDate.of(2023, 4, 4), null),
                new Reserva(null, fernando, LocalDate.of(2023, 4, 2), null)
        );

        when(clienteRepository.findById(100L)).thenReturn(Optional.of(francisco));
        when(clienteRepository.findById(200L)).thenReturn(Optional.of(fernando));

        when(reservaRepository.findAll()).thenReturn(reservas);

        //Act
        List<Reserva> reservasFrancisco = this.reservaService.reservasCliente(100L);

        List<Reserva> reservasFernando = this.reservaService.reservasCliente(200L);

        //Assert
        assertEquals(3, reservasFrancisco.size());
        assertEquals(1, reservasFernando.size());
    }
}
