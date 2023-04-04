package com.christiansalazar.ashir.DTO;

public class HabitacionDTO {

    private Integer numero;
    private String tipo;

    public HabitacionDTO() {
    }

    public HabitacionDTO(Integer numero, String tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

}
