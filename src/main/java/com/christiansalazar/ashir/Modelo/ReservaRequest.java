package com.christiansalazar.ashir.Modelo;

import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;

@ApiIgnore
public class ReservaRequest {
    private Long cedulaCliente;
    private Integer numeroHabitacion;
    private LocalDate fecha;

    public ReservaRequest() {
    }

    public ReservaRequest(Long cedulaCliente, Integer numeroHabitacion, LocalDate fecha) {
        this.cedulaCliente = cedulaCliente;
        this.numeroHabitacion = numeroHabitacion;
        this.fecha = fecha;
    }

    public Long getCedulaCliente() {
        return cedulaCliente;
    }

    public Integer getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
