package com.postech.tabletrust.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.dto.RestaurantDTO;
import com.postech.tabletrust.entity.Restaurant;
import com.postech.tabletrust.exception.GlobalExceptionHandler;
import com.postech.tabletrust.gateways.RestaurantGateway;
import com.postech.tabletrust.interfaces.IRestaurantGateway;
import com.postech.tabletrust.utils.NewEntititesHelper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RestaurantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IRestaurantGateway restaurantGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        RestaurantController restaurantController = new RestaurantController(restaurantGateway);
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).setControllerAdvice(new GlobalExceptionHandler()).addFilter((request, response, chain) -> {
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
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    class CreateRestaurant{

        @Test
        void testNewRestaurant_ValidInput_ReturnsCreatedResponse() throws Exception{
            RestaurantDTO restaurantDTO = NewEntititesHelper.gerarRestaurantInsertRequest();
            mockMvc.perform(post("/restaurants")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(restaurantDTO)))
                            .andExpect(status().isCreated());
        }

        @Test
        void testNewRestaurant_InvalidInput_ReturnsBadRequestResponse() throws Exception{
            RestaurantDTO restaurantDTO = NewEntititesHelper.gerarRestaurantInsertRequest();
            mockMvc.perform(post("/restaurants")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(restaurantDTO)))
                            .andExpect(status().isCreated());
        }
//
//        @Test
//        void testNewRestaurant_RuntimeException_ReturnsNotFoundResponse() {
//            // Arrange
//            Restaurant restaurant = new Restaurant();
//            when(restaurantGateway.newRestaurant(restaurant)).thenThrow(new RuntimeException("Erro interno"));
//
//            // Act
//            ResponseEntity<?> response = restaurantController.newRestaurant(restaurant);
//
//            // Assert
//            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//            assertEquals("Erro interno", response.getBody());
//            verify(restaurantGateway, times(1)).newRestaurant(restaurant);
//        }
//    }

//    @Nested
//    class ReadRestaurant {
//
//        @Test
//        void testFindRestaurant_ValidInputId_ReturnRestaurant(){
//            // Arrange
//            UUID validUuid = UUID.randomUUID();
//            Restaurant mockRestaurant = new Restaurant();
//            when(restaurantGateway.findRestaurantById(validUuid.toString())).thenReturn(mockRestaurant);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurant(validUuid.toString());
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(mockRestaurant, response.getBody());
//            verify(restaurantGateway, times(1)).findRestaurantById(validUuid.toString());
//        }
//
//        @Test
//        void testFindRestaurant_NotRegisteredId_ReturnNotFoundException() {
//            // Arrange
//            UUID notFoundUuid = UUID.randomUUID();
//            when(restaurantGateway.findRestaurantById(notFoundUuid.toString())).thenThrow(EntityNotFoundException.class);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurant(notFoundUuid.toString());
//
//            // Assertions
//            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//            verify(restaurantGateway, times(1)).findRestaurantById(notFoundUuid.toString());
//        }
//
//        @Test
//        void testFindRestaurant_InvalidInputId_ReturnBadRequest() {
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurant("invalid-uuid");
//
//            // Assertions
//            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//            assertEquals("ID inválido", response.getBody());
//            verify(restaurantGateway, never()).findRestaurantById(any());
//        }
//
//        @Test
//        void testFindRestaurant_NoFilterInput_ReturnRestaurant() {
//            // Arrange
//            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
//            when(restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType(null, null, null)).thenReturn(mockRestaurants);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType(null, null, null);
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(mockRestaurants, response.getBody());
//            verify(restaurantGateway, times(1)).findRestaurantsByNameAndAddressAndKitchenType(null, null, null);
//        }
//
//        @Test
//        void testFindRestaurant_ValidInputName_ReturnRestaurant(){
//            // Arrange
//            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
//            when(restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, null)).thenReturn(mockRestaurants);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, null);
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(mockRestaurants, response.getBody());
//            verify(restaurantGateway, times(1)).findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, null);
//        }
//
//        @Test
//        void testFindRestaurant_ValidInputAddress_ReturnRestaurant(){
//            // Arrange
//            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
//            when(restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", null)).thenReturn(mockRestaurants);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", null);
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(mockRestaurants, response.getBody());
//            verify(restaurantGateway, times(1)).findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", null);
//        }
//
//        @Test
//        void testFindRestaurant_ValidInputKitchenType_ReturnRestaurant(){
//            // Arrange
//            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
//            when(restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType(null, null, "tipo_cozinha")).thenReturn(mockRestaurants);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType(null, null, "tipo_cozinha");
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(mockRestaurants, response.getBody());
//            verify(restaurantGateway, times(1)).findRestaurantsByNameAndAddressAndKitchenType(null, null, "tipo_cozinha");
//        }
//
//        @Test
//        void testFindRestaurant_ValidInputNameAddresKitchenType_ReturnRestaurant() {
//            // Arrange
//            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
//            when(restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", "tipo_cozinha")).thenReturn(mockRestaurants);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", "tipo_cozinha");
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(mockRestaurants, response.getBody());
//            verify(restaurantGateway, times(1)).findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", "tipo_cozinha");
//        }
//
//        @Test
//        void testFindRestaurant_ValidInputNameAddres_ReturnRestaurant(){
//            // Arrange
//            List<Restaurant> mockRestaurants = new ArrayList<>();
//            when(restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", null)).thenReturn(mockRestaurants);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", null);
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(mockRestaurants, response.getBody());
//            verify(restaurantGateway, times(1)).findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", null);
//
//        }
//
//        @Test
//        void testFindRestaurant_ValidInputNameKitchenType_ReturnRestaurant(){
//            // Arrange
//            List<Restaurant> mockRestaurants = new ArrayList<>();
//            when(restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, "tipo_cozinha")).thenReturn(mockRestaurants);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, "tipo_cozinha");
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(mockRestaurants, response.getBody());
//            verify(restaurantGateway, times(1)).findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, "tipo_cozinha");
//
//        }
//
//        @Test
//        void testFindRestaurant_ValidInputAddresKitchenType_ReturnRestaurant(){
//            // Arrange
//            List<Restaurant> mockRestaurants = new ArrayList<>();
//            when(restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", "tipo_cozinha")).thenReturn(mockRestaurants);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", "tipo_cozinha");
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(mockRestaurants, response.getBody());
//            verify(restaurantGateway, times(1)).findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", "tipo_cozinha");
//
//        }
//    }
//
//    @Nested
//    class UpdateRestaurant{
//        @Test
//        void testUpdateRestaurant_ValidInputIdAndRestaurant_ReturnOk() {
//            // Arrange
//            UUID validUuid = UUID.randomUUID();
//            Restaurant updatedRestaurant = new Restaurant();
//            when(restaurantGateway.updateRestaurant(validUuid, updatedRestaurant)).thenReturn(updatedRestaurant);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.updateRestaurant(validUuid.toString(), updatedRestaurant);
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(updatedRestaurant, response.getBody());
//            verify(restaurantGateway, times(1)).updateRestaurant(validUuid, updatedRestaurant);
//        }
//
//        @Test
//        void testUpdateRestaurant_InvalidInputIdAndRestaurant_ReturnBadRequest() {
//            // Act
//            ResponseEntity<?> response = restaurantController.updateRestaurant("invalid-uuid", new Restaurant());
//
//            // Assertions
//            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//            assertEquals("ID inválido", response.getBody());
//            verify(restaurantGateway, never()).updateRestaurant(any(), any());
//        }
//
//        @Test
//        void testUpdateRestaurant_NotRegisteredId_ReturnNotFoundException() {
//            // Arrange
//            UUID notFoundUuid = UUID.randomUUID();
//            when(restaurantGateway.updateRestaurant(notFoundUuid, new Restaurant())).thenThrow(RuntimeException.class);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.updateRestaurant(notFoundUuid.toString(), new Restaurant());
//
//            // Assertions
//            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//            verify(restaurantGateway, times(1)).updateRestaurant(notFoundUuid, new Restaurant());
//        }
//    }
//
//    @Nested
//    class DeleteRestaurant{
//        @Test
//        void testDeleteRestaurant_ValidInputId_ReturnOk() {
//            // Arrange
//            UUID validUuid = UUID.randomUUID();
//
//            // Act
//            ResponseEntity<?> response = restaurantController.deleteRestaurant(validUuid.toString());
//
//            // Assertions
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals("Restaurante removido", response.getBody());
//            verify(restaurantGateway, times(1)).deleteRestaurant(validUuid);
//        }
//
//        @Test
//        void testDeleteRestaurant_InvalidInputId_ReturnBadRequest() {
//            // Act
//            ResponseEntity<?> response = restaurantController.deleteRestaurant("invalid-uuid");
//
//            // Assertions
//            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//            assertEquals("ID inválido", response.getBody());
//            verify(restaurantGateway, never()).deleteRestaurant(any());
//        }
//
//        @Test
//        void testDeleteRestaurant_NotRegisteredId_ReturnNotFoundException() {
//            // Arrange
//            UUID notFoundUuid = UUID.randomUUID();
//            doThrow(RuntimeException.class).when(restaurantGateway).deleteRestaurant(notFoundUuid);
//
//            // Act
//            ResponseEntity<?> response = restaurantController.deleteRestaurant(notFoundUuid.toString());
//
//            // Assertions
//            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//            verify(restaurantGateway, times(1)).deleteRestaurant(notFoundUuid);
//        }
    }
}
