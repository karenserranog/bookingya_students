package com.project.bookingya.tdd;

import com.project.bookingya.services.GuestService;
import com.project.bookingya.services.ReservationService;
import com.project.bookingya.services.RoomService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

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

    }
}
