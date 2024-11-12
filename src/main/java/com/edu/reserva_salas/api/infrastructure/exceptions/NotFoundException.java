package com.edu.reserva_salas.api.infrastructure.exceptions;

public class NotFoundException extends RuntimeException{

    private static final String msg = "";

    public NotFoundException(String msg){
        super(msg);
    }
}
