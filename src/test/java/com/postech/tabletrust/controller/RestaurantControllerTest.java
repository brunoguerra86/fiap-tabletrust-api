package com.postech.tabletrust.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.tabletrust.entity.Restaurant;
import com.postech.tabletrust.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class RestaurantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        //openMocks.close();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    class CreateRestaurant{
        @Test
        void testNewRestaurant_ValidInput_ReturnsCreatedResponse(){
            // Arrange
            Restaurant restaurant = new Restaurant();
            when(restaurantService.newRestaurant(restaurant)).thenReturn(restaurant);

            // Act
            ResponseEntity<?> response = restaurantController.newRestaurant(restaurant);

            // Assert
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(restaurant, response.getBody());
            verify(restaurantService, times(1)).newRestaurant(restaurant);
        }

        @Test
        void testNewRestaurant_InvalidInput_ReturnsBadRequestResponse() {
            // Arrange
            Restaurant restaurant = new Restaurant();
            when(restaurantService.newRestaurant(restaurant)).thenThrow(new IllegalArgumentException("ID inválido"));

            // Act
            ResponseEntity<?> response = restaurantController.newRestaurant(restaurant);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("ID inválido", response.getBody());
            verify(restaurantService, times(1)).newRestaurant(restaurant);
        }

        @Test
        void testNewRestaurant_RuntimeException_ReturnsNotFoundResponse() {
            // Arrange
            Restaurant restaurant = new Restaurant();
            when(restaurantService.newRestaurant(restaurant)).thenThrow(new RuntimeException("Erro interno"));

            // Act
            ResponseEntity<?> response = restaurantController.newRestaurant(restaurant);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Erro interno", response.getBody());
            verify(restaurantService, times(1)).newRestaurant(restaurant);
        }
    }

    @Nested
    class ReadRestaurant {

        @Test
        void testFindRestaurant_ValidInputId_ReturnRestaurant(){
            // Arrange
            UUID validUuid = UUID.randomUUID();
            Restaurant mockRestaurant = new Restaurant();
            when(restaurantService.findRestaurant(validUuid)).thenReturn(mockRestaurant);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurant(validUuid.toString());

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockRestaurant, response.getBody());
            verify(restaurantService, times(1)).findRestaurant(validUuid);
        }

        @Test
        void testFindRestaurant_NotRegisteredId_ReturnNotFoundException() {
            // Arrange
            UUID notFoundUuid = UUID.randomUUID();
            when(restaurantService.findRestaurant(notFoundUuid)).thenThrow(EntityNotFoundException.class);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurant(notFoundUuid.toString());

            // Assertions
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(restaurantService, times(1)).findRestaurant(notFoundUuid);
        }

        @Test
        void testFindRestaurant_InvalidInputId_ReturnBadRequest() {
            // Act
            ResponseEntity<?> response = restaurantController.findRestaurant("invalid-uuid");

            // Assertions
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("ID inválido", response.getBody());
            verify(restaurantService, never()).findRestaurant(any());
        }

        @Test
        void testFindRestaurant_NoFilterInput_ReturnRestaurant() {
            // Arrange
            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
            when(restaurantService.findRestaurantsByNameAndAddressAndKitchenType(null, null, null)).thenReturn(mockRestaurants);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType(null, null, null);

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockRestaurants, response.getBody());
            verify(restaurantService, times(1)).findRestaurantsByNameAndAddressAndKitchenType(null, null, null);
        }

        @Test
        void testFindRestaurant_ValidInputName_ReturnRestaurant(){
            // Arrange
            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
            when(restaurantService.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, null)).thenReturn(mockRestaurants);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, null);

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockRestaurants, response.getBody());
            verify(restaurantService, times(1)).findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, null);
        }

        @Test
        void testFindRestaurant_ValidInputAddress_ReturnRestaurant(){
            // Arrange
            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
            when(restaurantService.findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", null)).thenReturn(mockRestaurants);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", null);

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockRestaurants, response.getBody());
            verify(restaurantService, times(1)).findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", null);
        }

        @Test
        void testFindRestaurant_ValidInputKitchenType_ReturnRestaurant(){
            // Arrange
            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
            when(restaurantService.findRestaurantsByNameAndAddressAndKitchenType(null, null, "tipo_cozinha")).thenReturn(mockRestaurants);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType(null, null, "tipo_cozinha");

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockRestaurants, response.getBody());
            verify(restaurantService, times(1)).findRestaurantsByNameAndAddressAndKitchenType(null, null, "tipo_cozinha");
        }

        @Test
        void testFindRestaurant_ValidInputNameAddresKitchenType_ReturnRestaurant() {
            // Arrange
            List<Restaurant> mockRestaurants = new ArrayList<Restaurant>();
            when(restaurantService.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", "tipo_cozinha")).thenReturn(mockRestaurants);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", "tipo_cozinha");

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockRestaurants, response.getBody());
            verify(restaurantService, times(1)).findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", "tipo_cozinha");
        }

        @Test
        void testFindRestaurant_ValidInputNameAddres_ReturnRestaurant(){
            // Arrange
            List<Restaurant> mockRestaurants = new ArrayList<>();
            when(restaurantService.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", null)).thenReturn(mockRestaurants);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", null);

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockRestaurants, response.getBody());
            verify(restaurantService, times(1)).findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", "endereco_restaurante", null);

        }

        @Test
        void testFindRestaurant_ValidInputNameKitchenType_ReturnRestaurant(){
            // Arrange
            List<Restaurant> mockRestaurants = new ArrayList<>();
            when(restaurantService.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, "tipo_cozinha")).thenReturn(mockRestaurants);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, "tipo_cozinha");

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockRestaurants, response.getBody());
            verify(restaurantService, times(1)).findRestaurantsByNameAndAddressAndKitchenType("nome_restaurante", null, "tipo_cozinha");

        }

        @Test
        void testFindRestaurant_ValidInputAddresKitchenType_ReturnRestaurant(){
            // Arrange
            List<Restaurant> mockRestaurants = new ArrayList<>();
            when(restaurantService.findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", "tipo_cozinha")).thenReturn(mockRestaurants);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", "tipo_cozinha");

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockRestaurants, response.getBody());
            verify(restaurantService, times(1)).findRestaurantsByNameAndAddressAndKitchenType(null, "endereco_restaurante", "tipo_cozinha");

        }
    }

    @Nested
    class UpdateRestaurant{
        @Test
        void deveAtualizarRestauranteComIdValidoERestauranteValido() {
            // Arrange
            UUID validUuid = UUID.randomUUID();
            Restaurant updatedRestaurant = new Restaurant();
            when(restaurantService.updateRestaurant(validUuid, updatedRestaurant)).thenReturn(updatedRestaurant);

            // Act
            ResponseEntity<?> response = restaurantController.updateRestaurant(validUuid.toString(), updatedRestaurant);

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(updatedRestaurant, response.getBody());
            verify(restaurantService, times(1)).updateRestaurant(validUuid, updatedRestaurant);
        }

        @Test
        void deveGerarExcecao_QuandoAtualizarRestaurante_IdInvalido() {
            // Act
            ResponseEntity<?> response = restaurantController.updateRestaurant("invalid-uuid", new Restaurant());

            // Assertions
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("ID inválido", response.getBody());
            verify(restaurantService, never()).updateRestaurant(any(), any());
        }

        @Test
        void deveGerarExcecao_QuandoAtualizarRestaurante_IdNaoEncontrado() {
            // Arrange
            UUID notFoundUuid = UUID.randomUUID();
            when(restaurantService.updateRestaurant(notFoundUuid, new Restaurant())).thenThrow(RuntimeException.class);

            // Act
            ResponseEntity<?> response = restaurantController.updateRestaurant(notFoundUuid.toString(), new Restaurant());

            // Assertions
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(restaurantService, times(1)).updateRestaurant(notFoundUuid, new Restaurant());
        }
    }

    @Nested
    class DeleteRestaurant{
        @Test
        void deveExcluirRestauranteComIdValido() {
            // Arrange
            UUID validUuid = UUID.randomUUID();

            // Act
            ResponseEntity<?> response = restaurantController.deleteRestaurant(validUuid.toString());

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Restaurante removido", response.getBody());
            verify(restaurantService, times(1)).deleteRestaurant(validUuid);
        }

        @Test
        void deveGerarExcecao_QuandoExcluirRestaurante_IdInvalido() {
            // Act
            ResponseEntity<?> response = restaurantController.deleteRestaurant("invalid-uuid");

            // Assertions
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("ID inválido", response.getBody());
            verify(restaurantService, never()).deleteRestaurant(any());
        }

        @Test
        void deveGerarExcecao_QuandoExcluirRestaurante_IdNaoEncontrado() {
            // Arrange
            UUID notFoundUuid = UUID.randomUUID();
            doThrow(RuntimeException.class).when(restaurantService).deleteRestaurant(notFoundUuid);

            // Act
            ResponseEntity<?> response = restaurantController.deleteRestaurant(notFoundUuid.toString());

            // Assertions
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(restaurantService, times(1)).deleteRestaurant(notFoundUuid);
        }
    }
}
