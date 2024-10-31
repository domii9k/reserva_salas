package com.edu.reserva_salas.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.edu.reserva_salas.api.infrastructure.entity.Room;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

}
