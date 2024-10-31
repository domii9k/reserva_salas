package com.edu.reserva_salas.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.repositories.RoomRepository;

@Service
@Validated
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    //salva uma nova sala
    public Room createRoom(Room room) {
        return roomRepository.save(room); 
    }

    //retorna uma lista de salas
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    

}
