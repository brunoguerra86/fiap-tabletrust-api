package com.postech.tabletrust.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.postech.tabletrust.gateways.ReservationGateway;
import com.postech.tabletrust.service.CustomerService;
import com.postech.tabletrust.utils.NewEntititesHelper;
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
    private ReservationGateway reservationGateway;
    @Mock
    private ReservationService reservationService;
    @Mock
    private CustomerService customerService;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {

        openMocks = MockitoAnnotations.openMocks(this);
        ReservationController ReservationController = new ReservationController(reservationService, customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(ReservationController).setControllerAdvice(new GlobalExceptionHandler()).addFilter((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }, "/*").build();
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
            ReservationDTO reservationDTO = NewEntititesHelper.gerarReservationInsertRequest();
            mockMvc.perform(post("/reservations")
                    .contentType(MediaType.APPLICATION_JSON).content(asJsonString(reservationDTO)))
                    .andExpect(status().isCreated());
        }

        @Test
        void deveGerarExcecao_QuandoRegistrarReservation_DataNula() throws Exception {
            ReservationDTO reservationDTO = NewEntititesHelper.gerarReservationInsertRequest();
            reservationDTO.setReservationDate(null);
            mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(reservationDTO))).andExpect(status().isBadRequest()).andExpect(result -> {
                String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                assertThat(json).contains("Validation error");
                assertThat(json).contains("A data não pode ser nula");
            });
        }

        @Test
        void deveGerarExcecao_QuandoRegistrarReservation_QuantidadeNula() throws Exception {
            ReservationDTO reservationDTO = NewEntititesHelper.gerarReservationInsertRequest();
            reservationDTO.setQuantity(null);
            mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(reservationDTO))).andExpect(status().isBadRequest()).andExpect(result -> {
                String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                assertThat(json).contains("Validation error");
                assertThat(json).contains("A quantidade de lugares não pode ser nula");
            });
        }

    }

}
