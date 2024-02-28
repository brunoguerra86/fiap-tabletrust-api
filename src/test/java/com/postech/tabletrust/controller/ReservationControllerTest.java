package com.postech.tabletrust.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.utils.ReservationHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.postech.tabletrust.service.ReservationService;
import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.exception.GlobalExceptionHandler;

import java.nio.charset.StandardCharsets;

class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReservationService ReservationService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        ReservationController ReservationController = new ReservationController(ReservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(ReservationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    class NewReservation {
        @Test
        void devePermitirRegistrarReservation() throws Exception {
            ReservationDTO reservationDTO = ReservationHelper.gerarReservationRequest();
            when(ReservationService.createReservation(any(ReservationDTO.class)))
                    .thenAnswer(i -> i.getArgument(0));

            mockMvc.perform(post("/reservation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(reservationDTO)))
                    .andExpect(status().isCreated());
            verify(ReservationService, times(1))
                    .createReservation(any(ReservationDTO.class)); //andExpect(content().string(containsString("conteudo")))
        }

        @Test
        void deveGerarExcecao_QuandoRegistrarReservation_DataNula() throws Exception {
            ReservationDTO reservationDTO = ReservationHelper.gerarReservationRequest();
            when(ReservationService.createReservation(any(ReservationDTO.class)))
                    .thenAnswer(i -> i.getArgument(0));
            reservationDTO.setReservationDate(null);
            mockMvc.perform(post("/reservation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(reservationDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> {
                        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        assertThat(json).contains("Validation error");
                        assertThat(json).contains("A data não pode ser nula");
                    });
            verify(ReservationService, never())
                    .createReservation(any(ReservationDTO.class));
        }

        @Test
        void deveGerarExcecao_QuandoRegistrarReservation_QuantidadeNula() throws Exception {
            ReservationDTO reservationDTO = ReservationHelper.gerarReservationRequest();
            when(ReservationService.createReservation(any(ReservationDTO.class)))
                    .thenAnswer(i -> i.getArgument(0));
            //reservationDTO.setQuantity(null);
            mockMvc.perform(post("/reservation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(reservationDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> {
                        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        assertThat(json).contains("Validation error");
                        assertThat(json).contains("A quantidade de lugares não pode ser nula");
                    });
            verify(ReservationService, never())
                    .createReservation(any(ReservationDTO.class));
        }

    }

}
