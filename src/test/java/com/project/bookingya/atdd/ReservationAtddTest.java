package com.project.bookingya.atdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookingya.dtos.GuestDto;
import com.project.bookingya.dtos.ReservationDto;
import com.project.bookingya.dtos.RoomDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationAtddTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateReservationSuccessfully() throws Exception {

        // 🔹 1. Crear Guest
        GuestDto guestDto = new GuestDto();
        guestDto.setIdentification("88281397");
        guestDto.setName("Luis Antonio Serrano");
        guestDto.setEmail("LuisAntonioSerrano@gmail.com");

        String guestResponse = mockMvc.perform(post("/guest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guestDto)))
                .andReturn().getResponse().getContentAsString();

        UUID guestId = extractId(guestResponse);

        // 🔹 2. Crear Habitación
        RoomDto roomDto = new RoomDto();
        roomDto.setCode("06"); // 👈 obligatorio
        roomDto.setName("Habitación 06");
        roomDto.setCity("Ocaña");
        roomDto.setMaxGuests(9);
        roomDto.setNightlyPrice((BigDecimal.valueOf(530053.23)));
        roomDto.setAvailable(true);

        String roomResponse = mockMvc.perform(post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andReturn().getResponse().getContentAsString();

        UUID roomId = extractId(roomResponse);

        // Creas el traductor
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // 🔹 3. Crear Reservation
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setGuestId(guestId);
        reservationDto.setRoomId(roomId);
        reservationDto.setCheckIn(LocalDateTime.parse("15/05/2026 10:30", formatter));
        reservationDto.setCheckOut(LocalDateTime.parse("15/06/2026 10:30", formatter));
        reservationDto.setGuestsCount(3);
        reservationDto.setNotes("Notas Adicionales");

        mockMvc.perform(post("/reservation") // 👈 revisa si es singular
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDto)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.guestId").value(guestId.toString()))
                .andExpect(jsonPath("$.roomId").value(roomId.toString()))
                .andExpect(jsonPath("$.checkIn").value("2026-05-15T10:30:00"))
                .andExpect(jsonPath("$.checkOut").value("2026-06-15T10:30:00"))
                .andExpect(jsonPath("$.guestsCount").value(3))
                .andExpect(jsonPath("$.notes").value("Notas Adicionales"));

    }
    private UUID extractId(String json) throws Exception {
        return UUID.fromString(objectMapper.readTree(json).get("id").asText()
        );
    }
}
