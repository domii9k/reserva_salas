package com.edu.reserva_salas.api.infrastructure.entity.interfaces;

import com.edu.reserva_salas.api.dto.request.RoomRequestDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Room;

public interface RoomInterface {
    Room createRoom(RoomRequestDTO roomRequestDTO);
}
