package com.edu.reserva_salas.api.infrastructure.entity.factories;

import com.edu.reserva_salas.api.infrastructure.entity.builders.ReservationBuilder;
import com.edu.reserva_salas.api.infrastructure.entity.builders.RoomBuilder;

public class RoomsFactory {

    private RoomsFactory() {

    }

    public enum RoomType {
        ROOM, RSV
    }

    public static Object createOrReserve(RoomType type) {
        switch (type) {
            case ROOM:
                return new RoomBuilder();
            case RSV:
                return new ReservationBuilder();
        }
        return new IllegalArgumentException("Opcao invalida");
    }

}
