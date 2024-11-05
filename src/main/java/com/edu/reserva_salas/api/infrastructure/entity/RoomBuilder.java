package com.edu.reserva_salas.api.infrastructure.entity;

import java.util.List;

//classe builder para instancia de salas
public class RoomBuilder {

    private Room room;

    public static RoomBuilder builder() {
        RoomBuilder builder = new RoomBuilder();
        builder.room = new Room(); // Inicializa a instância de Room
        return builder;
    }

    public RoomBuilder id(String id) {
        this.room.setId(id);
        return this;
    }

    public RoomBuilder name(String name) {
        this.room.setName(name);
        return this;
    }

    public RoomBuilder capacity(int capacity) {
        this.room.setCapacity(capacity);
        return this;
    }

    public RoomBuilder resources(List<String> resources) {
        this.room.setResources(resources);
        return this;
    }

    public RoomBuilder status(char status) {
        this.room.setStatus(status);
        return this;

    }

    public Room build() {
        return this.room;
    }
}
