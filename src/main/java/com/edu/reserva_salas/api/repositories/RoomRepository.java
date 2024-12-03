package com.edu.reserva_salas.api.repositories;

import com.edu.reserva_salas.api.infrastructure.entity.enums.RoomStatus;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.reserva_salas.api.infrastructure.entity.Room;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

    List<Room> findByName(String name);
    Page<Room> findAllByStatus(@Param("status") RoomStatus status, Pageable pageable);


}
