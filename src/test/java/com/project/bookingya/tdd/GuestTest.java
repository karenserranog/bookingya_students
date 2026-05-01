package com.project.bookingya.tdd;

import com.project.bookingya.dtos.GuestDto;
import com.project.bookingya.exceptions.EntityNotExistsException;
import com.project.bookingya.models.Guest;
import com.project.bookingya.services.GuestService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GuestTest {
    @Autowired
    GuestService  guestService;

    @Test
    @Order(1)
    void testCreateGuest() throws Exception{
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

    @Test
    @Order(2)
    void testGetGuestByIdentification() throws Exception{
        //Buscamos el huésped directamente por su identificación
        String identificacionBuscada = "1091677888";
        Guest guest = guestService.getByIdentification(identificacionBuscada);

        //¡Aquí ponemos tu mensaje de éxito para la consola!
        System.out.println("¡Búsqueda exitosa! Se encontró al huésped: " + guest.getName() + " con ID: " + guest.getIdentification());

        //Validación final: Comparamos que el ID encontrado sea exactamente el que pedimos
        assertEquals(identificacionBuscada, guest.getIdentification());
    }

    @Test
    @Order(3)
    void testGetGuestById() throws Exception{
        String identificacionBuscada = "1091677888";
        Guest guestPorIdentificacion = guestService.getByIdentification(identificacionBuscada);
        Guest guestPorId = guestService.getById(guestPorIdentificacion.getId());
        System.out.println("¡Búsqueda exitosa! Se encontró al huésped por Id: " + guestPorId.getName() + " con ID: " + guestPorId.getId());
        assertEquals(guestPorIdentificacion.getIdentification(), guestPorId.getIdentification());
        assertEquals(guestPorIdentificacion.getId(), guestPorId.getId());
    }

    @Test
    @Order(4)
    void testUpdateGuest() throws Exception{
        String identificacionBuscada = "1091677888";
        Guest guest = guestService.getByIdentification(identificacionBuscada);
        GuestDto guestDto = new GuestDto();
        guestDto.setName("Camilo Sarabia");
        Guest guestUpdate = guestService.update(guestDto,guest.getId());
        assertEquals(guestDto.getName(), guestUpdate.getName());
    }

    @Test
    @Order(5)
    void testDeleteGuest() throws Exception{
        String identificacionBuscada = "1091677888";
        Guest guest = guestService.getByIdentification(identificacionBuscada);
        guestService.delete(guest.getId());

        // Intentamos buscarlo después de borrado
        assertThrows(EntityNotExistsException.class, () -> {
            guestService.getById(guest.getId());
        });
    }
    }
