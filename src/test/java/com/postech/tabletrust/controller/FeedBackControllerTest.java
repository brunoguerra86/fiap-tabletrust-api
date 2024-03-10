package com.postech.tabletrust.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.tabletrust.dto.FeedBackCreateDTO;
import com.postech.tabletrust.entities.FeedBack;
import com.postech.tabletrust.exception.GlobalExceptionHandler;
import com.postech.tabletrust.service.FeedBackService;
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

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedBackControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FeedBackService feedBackService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        FeedBackController feedBackController = new FeedBackController(feedBackService);
        mockMvc = MockMvcBuilders.standaloneSetup(feedBackController)
                .setControllerAdvice(new GlobalExceptionHandler()).addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*").build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class createAFeedBack {

        @Test
        void shouldCreateAFeedBack() throws Exception {
            FeedBack feedBack = NewEntititesHelper.createAFeedBack();
            FeedBackCreateDTO feedBackCreateDTO = feedBack.convertToDTO();
            when(feedBackService.create(feedBackCreateDTO)).thenReturn(feedBack);

            // Convertendo FeedBackCreateDTO para JSON
            String feedBackCreateDTOJson = new ObjectMapper().writeValueAsString(feedBackCreateDTO);

            mockMvc.perform(
                    post("/feedback")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(feedBackCreateDTOJson))
                            .andExpect(status().isOk());

            verify(feedBackService, times(1)).create(feedBackCreateDTO);
        }

        void shouldReturnExceptionIfReservationNotFound(){
            fail("shouldReturnExceptionIfReservationNotFound nao implementado");
        }

        void shouldReturnExceptionIfReservationNotValid(){
            fail("shouldThrowExceptionIfReservationNotValid nao implementado");
        }
    }

    @Nested
    class listAFeedBack {

        void shouldListAFeedBack(){
            fail("shouldListAFeedBack nao implementado");
        }

        void shouldThrowExceptionIfRestaurantIdNotFound(){
            fail("shouldThrowExceptionIfRestaurantIdNotFound nao implementado");
        }

        void souldFoundFeedBackById() {
            fail("souldFoundFeedBackById nao implementado");
        }
    }

    @Nested
    class deleteAFeedBack {
        void souldDeleteAFeedback() {
            fail("deleteAFeedBack nao implementado"); // Somente o customer pode excluir seu comentario
        }
    }
}
