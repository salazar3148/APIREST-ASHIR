package com.christiansalazar.ashir.DTO;

public class ClienteDTO {

    private Long cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private Integer edad;
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Long cedula, String nombre, String apellido, String direccion, Integer edad, String email) {
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