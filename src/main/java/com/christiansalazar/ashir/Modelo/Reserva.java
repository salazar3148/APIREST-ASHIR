package com.christiansalazar.ashir.Modelo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;

@ApiModel(description = "Modelo para representar una RESERVA")
@Entity
@Table(name="RESERVAS")
public class Reserva {

    @ApiModelProperty(value = "Clave unica de la reserva")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CODIGO")
    private Integer codigo;

    @ManyToOne
    @JoinColumn(name = "NUMERO_HABITACION")
    private Habitacion habitacion;

    @ManyToOne
    @JoinColumn(name = "CEDULA_CLIENTE")
    private Cliente cliente;

    @Column(name="FECHA")
    private LocalDate fecha;

    @Column(name="total")
    private Integer total;

    public Reserva() {
    }

    public Reserva(Habitacion habitacion, Cliente cliente, LocalDate fecha, Integer total) {
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Integer getTotal() {
        return total;
    }
}
