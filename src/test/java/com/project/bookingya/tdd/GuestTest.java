package com.project.bookingya.tdd;

import com.project.bookingya.dtos.GuestDto;
import com.project.bookingya.models.Guest;
import com.project.bookingya.services.GuestService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GuestTest {
    @Autowired
    GuestService  guestService;

    @Test
    @Order(1)
    void TestCreateGuest() throws Exception{
        GuestDto guestDto = new GuestDto();
        guestDto.setIdentification("1091677888");
        guestDto.setName("Camila Sarabia");
        guestDto.setEmail("PruebaQA@gmail.com");
        Guest guest = guestService.create(guestDto);
        assertNotNull(guest);
        assertEquals(guest.getIdentification(), guestDto.getIdentification());
        assertEquals(guest.getName(), guestDto.getName());
        assertEquals(guest.getEmail(), guestDto.getEmail());
    }

    }
