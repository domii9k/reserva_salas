package com.edu.reserva_salas.api.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.reserva_salas.api.infrastructure.entity.Room;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

    Page<Room> findAllByStatus(@Param("status") char  status, Pageable pageable);


}
