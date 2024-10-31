package com.edu.reserva_salas.api.infrastructure.entity;

//classe que representa a lista de recursos disponiveis nas salas
public class Resource {

    private String name;

    @SuppressWarnings("unused")
    private Resource() {
        // construtor vazio
    }

    protected Resource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;

    }
}
