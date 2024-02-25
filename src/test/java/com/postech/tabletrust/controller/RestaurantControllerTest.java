package com.postech.tabletrust.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.tabletrust.exception.GlobalExceptionHandler;
import com.postech.tabletrust.service.RestaurantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RestaurantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private com.postech.tabletrust.service.RestaurantService RestaurantService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        RestaurantController RestaurantController = new RestaurantController(RestaurantService);
        mockMvc = MockMvcBuilders.standaloneSetup(RestaurantController)
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
    class GetRestaurant {

        @Test
        void deveConsultarRestaurantePorId(){

        }

        @Test
        void deveConsultarRestauranteSemFiltro(){

        }

        @Test
        void deveConsultarRestaurantePorNome(){

        }

        @Test
        void deveConsultarRestaurantePorEndereco(){

        }

        @Test
        void deveConsultarRestaurantePorTipoCozinha(){

        }

        @Test
        void deveConsultarRestaurantePorNomeEEnderecoETipoCozinha(){

        }

        @Test
        void deveConsultarRestaurantePorNomeEEndereco(){

        }

        @Test
        void deveConsultarRestaurantePorNomeETipoCozinha(){

        }

        @Test
        void deveConsultarRestaurantePorEnderecoETipoCozinha(){

        }

    }
}
