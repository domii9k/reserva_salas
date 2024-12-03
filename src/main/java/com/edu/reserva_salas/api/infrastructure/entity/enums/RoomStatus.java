package com.edu.reserva_salas.api.infrastructure.entity.enums;

import lombok.Getter;

@Getter
public enum RoomStatus {
    ACTIVE('A'),
    INACTIVE('I');

    private final char status;

    RoomStatus(char status) {
        this.status = status;
    }

}
