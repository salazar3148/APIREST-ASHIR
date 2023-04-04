package com.christiansalazar.ashir.Modelo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(description = "Modelo para representar una HABITACION")
@Entity
@Table(name="HABITACIONES")
public class Habitacion {

    @ApiModelProperty(value = "Clave unica de la habitación")
    @Id
    @Column(name="NUMERO")
    private Integer numero;

    @Column(name="TIPO", nullable = false)
    private String tipo;

    @Column(name="PRECIO_BASE", nullable = false)
    private Integer precioBase;

    public Habitacion() {
    }

    public Habitacion(Integer numero, String tipo, Integer precioBase) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioBase = precioBase;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getPrecioBase() {
        return precioBase;
    }
}
