package com.edu.reserva_salas.api.infrastructure.entity.builders;

import java.util.List;

import com.edu.reserva_salas.api.infrastructure.entity.Resource;
import com.edu.reserva_salas.api.infrastructure.entity.Room;

//classe builder para instancia de salas
public class RoomBuilder {

    private Room room;

    public static RoomBuilder builder() {
        return new RoomBuilder();
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

    public RoomBuilder resources(List<Resource> resources) {
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
