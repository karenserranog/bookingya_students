package com.project.bookingya.tdd;

import com.project.bookingya.dtos.ReservationDto;
import com.project.bookingya.models.Guest;
import com.project.bookingya.models.Reservation;
import com.project.bookingya.models.Room;
import com.project.bookingya.services.GuestService;
import com.project.bookingya.services.ReservationService;
import com.project.bookingya.services.RoomService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationTest {
    @Autowired
    private ReservationService  reservationService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private GuestService guestService;

    // Usamos esta variable estática para mantener el ID de la reservación entre tests
    private static UUID savedReservationId;

    @Test
    @Order(1)
    void testCreateReservation() throws Exception {
        // 1. Obtenemos el huesped y habitación creados en los otros tests
        Guest guest = guestService.getByIdentification("1091677332");
        Room room = roomService.getByCode("01");

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCheckIn(LocalDateTime.now());
        reservationDto.setCheckOut(LocalDateTime.now().plusDays(4));
        reservationDto.setGuestId(guest.getId());
        reservationDto.setGuestsCount(1);
        reservationDto.setNotes("Familia Serrano");
        reservationDto.setRoomId(room.getId());

        // 3. Creamos la reserva
        Reservation reservation = reservationService.create(reservationDto);

        assertNotNull(reservation);
        assertNotNull(reservation.getId());
        assertEquals(reservationDto.getCheckIn(), reservation.getCheckIn());
        assertEquals(reservationDto.getCheckOut(), reservation.getCheckOut());
        assertEquals(reservationDto.getGuestId(), reservation.getGuestId());
        assertEquals(reservationDto.getGuestsCount(), reservation.getGuestsCount());
        assertEquals(reservationDto.getNotes(), reservation.getNotes());
        assertEquals(reservationDto.getRoomId(), reservation.getRoomId());

        // Guardamos el ID para usarlo en los siguientes métodos @Test
        savedReservationId = reservation.getId();
    }

    @Test
    @Order(2)
    void testGetReservationById() throws Exception {
        // Buscamos la reserva usando el ID que guardamos en el paso anterior
        Reservation foundReservation = reservationService.getById(savedReservationId);
        assertNotNull(foundReservation);
        assertEquals(savedReservationId, foundReservation.getId());
        assertEquals("Familia Serrano",  foundReservation.getNotes());
        assertEquals(1, foundReservation.getGuestsCount());
        System.out.println("Id de la reservación: " + foundReservation.getId());
    }

    @Test
    @Order(3)
    void testUpdateReservation() throws Exception {
        Reservation foundUpdate = reservationService.getById(savedReservationId);
        ReservationDto updateDto = new ReservationDto();
        updateDto.setCheckIn(foundUpdate.getCheckIn());
        updateDto.setCheckOut(foundUpdate.getCheckOut());
        updateDto.setGuestId(foundUpdate.getGuestId());
        updateDto.setGuestsCount(2);
        updateDto.setNotes("Familia García");
        updateDto.setRoomId(foundUpdate.getRoomId());

        Reservation updated = reservationService.update(updateDto, savedReservationId);

        assertNotNull(updated);
        assertEquals(updateDto.getNotes(), updated.getNotes());
        assertEquals(updateDto.getCheckIn(), updated.getCheckIn());
        assertEquals(updateDto.getCheckOut(), updated.getCheckOut());
        assertEquals(updateDto.getGuestsCount(), updated.getGuestsCount());
    }
}
