package com.edu.reserva_salas.api.infrastructure.entity.builders;

import java.time.LocalDate;

import com.edu.reserva_salas.api.infrastructure.entity.Reservation;

//classe builder para instancia de reservas
public class ReservationBuilder {

    private Reservation reservation;

    public static ReservationBuilder builder() {
        return new ReservationBuilder();

    }

    public ReservationBuilder id(String id) {
        this.reservation.setId(id);
        return this;
    }

    public ReservationBuilder userId(String userId) {
        this.reservation.setUserId(userId);
        return this;
    }

    public ReservationBuilder roomId(String roomId) {
        this.reservation.setRoomId(roomId);
        return this;
    }

    public ReservationBuilder startDate(LocalDate startDate) {
        this.reservation.setReservationDate(startDate);
        return this;
    }

    public ReservationBuilder endDate(LocalDate endDate) {
        this.reservation.setReservationEndDate(endDate);
        return this;
    }

    public Reservation build() {
        return this.reservation;
    }

}
