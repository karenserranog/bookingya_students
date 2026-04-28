package com.project.bookingya.atdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookingya.dtos.RoomDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomAtddTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateRoomSuccessfully() throws Exception {

        RoomDto roomDto = new RoomDto();
        roomDto.setCode("05"); // 👈 obligatorio
        roomDto.setName("Habitación 05");
        roomDto.setCity("Ocaña");
        roomDto.setMaxGuests(6);
        roomDto.setNightlyPrice((BigDecimal.valueOf(250053.23)));
        roomDto.setAvailable(true);


        mockMvc.perform(post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.name").value("Habitación 05"))
                .andExpect(jsonPath("$.city").value("Ocaña"))
                .andExpect(jsonPath("$.maxGuests").value(6))
                .andExpect(jsonPath("$.nightlyPrice").value(250053.23))
                .andExpect(jsonPath("$.available").value(true));
    }
}
