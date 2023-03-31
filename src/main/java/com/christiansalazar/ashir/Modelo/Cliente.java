package com.christiansalazar.ashir.Modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CLIENTES")
public class Cliente {
    @Id
    @Column(name="CEDULA")
    private Long cedula;

    @Column(name="NOMBRE", nullable = false)
    private String nombre;

    @Column(name="APELLIDO", nullable = false)
    private String apellido;

    @Column(name="DIRECCION")
    private String direccion;

    @Column(name="EDAD")
    private Integer edad;

    @Column(name="EMAIL")
    private String email;

    public Cliente() {
    }

    public Cliente(Long cedula, String nombre, String apellido, String direccion, Integer edad, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.edad = edad;
        this.email = email;
    }

    public Long getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getEmail() {
        return email;
    }
}
