package com.christiansalazar.ashir.Modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HABITACIONES")
public class Habitacion {
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

    @Override
    public boolean equals(Object obj) {
        if(this.numero == ((Habitacion)obj).getNumero()) return true;
        return false;
    }
}
