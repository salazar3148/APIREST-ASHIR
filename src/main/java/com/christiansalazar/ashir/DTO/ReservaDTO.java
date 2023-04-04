package com.christiansalazar.ashir.DTO;
import java.time.LocalDate;

public class ReservaDTO {
    private Integer codigo;
    private Integer numeroHabitacion;
    private Long cedulaCliente;
    private LocalDate fecha;
    private Integer total;

    public ReservaDTO() {
    }

    public ReservaDTO(Integer codigo, Integer numeroHabitacion, Long cedulaCliente, LocalDate fecha, Integer total) {
        this.codigo = codigo;
        this.numeroHabitacion = numeroHabitacion;
        this.cedulaCliente = cedulaCliente;
        this.fecha = fecha;
        this.total = total;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Integer getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public Long getCedulaCliente() {
        return cedulaCliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Integer getTotal() {
        return total;
    }
}
