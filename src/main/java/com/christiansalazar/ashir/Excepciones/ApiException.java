package com.christiansalazar.ashir.Excepciones;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String mensaje;
    private final HttpStatus status;
    private final ZonedDateTime time;

    public ApiException(String mensaje, HttpStatus status, ZonedDateTime time) {
        this.mensaje = mensaje;
        this.status = status;
        this.time = time;
    }

    public String getMensaje() {
        return mensaje;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ZonedDateTime getTime() {
        return time;
    }
}
