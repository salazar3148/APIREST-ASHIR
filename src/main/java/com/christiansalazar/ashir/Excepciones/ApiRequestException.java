package com.christiansalazar.ashir.Excepciones;

public class ApiRequestException extends RuntimeException{
    public ApiRequestException(String mensajeError) {
        super(mensajeError);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
