import com.postech.tabletrust.controller.RestaurantController;
import com.postech.tabletrust.dto.RestaurantDTO;
import com.postech.tabletrust.entity.Restaurant;
import com.postech.tabletrust.exception.NotFoundException;
import com.postech.tabletrust.interfaces.IRestaurantGateway;
import com.postech.tabletrust.utils.NewEntititesHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    private RestaurantController restaurantController;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        restaurantController = new RestaurantController(restaurantGateway);
    }

    @Nested
    class CreateRestaurant {
        @Test
        void testNewRestaurant_Success() {
            // Arrange
            RestaurantDTO restaurantDTO = NewEntititesHelper.gerarRestaurantInsertRequest();
            Restaurant restaurantCreated = new Restaurant();
            restaurantCreated.setId(UUID.randomUUID());

            when(restaurantGateway.newRestaurant(any(RestaurantDTO.class))).thenReturn(restaurantCreated);

            // Act
            ResponseEntity<?> response = restaurantController.newRestaurant(restaurantDTO);

            // Assert
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(restaurantCreated, response.getBody());
        }

        @Test
        void testNewRestaurant_ValidationException() {
            // Arrange
            RestaurantDTO restaurantDTO = NewEntititesHelper.gerarRestaurantInsertRequest();

            when(restaurantGateway.newRestaurant(any(RestaurantDTO.class))).thenThrow(new RuntimeException("Some error message"));

            // Act
            ResponseEntity responseEntity = restaurantController.newRestaurant(restaurantDTO);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
            assertEquals("Some error message", responseEntity.getBody());
        }
    }

    @Nested
    class ReadRestaurant {
        @Test
        void testFindRestaurantsByNameAndAddressAndKitchenType_Success() {
            // Arrange
            String name = "Pizza Place";
            String address = "123 Main St";
            String kitchenType = "Italian";

            List<Restaurant> expectedRestaurants = new ArrayList<>();

            when(restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType(name, address, kitchenType))
                    .thenReturn(expectedRestaurants);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurantsByNameAndAddressAndKitchenType(name, address, kitchenType);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedRestaurants, response.getBody());
        }

        @Test
        void testFindRestaurant_Success() {
            // Arrange
            UUID restaurantId = UUID.randomUUID();
            Restaurant expectedRestaurant = new Restaurant();
            when(restaurantGateway.findRestaurantById(restaurantId.toString())).thenReturn(expectedRestaurant);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurant(restaurantId.toString());

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedRestaurant, response.getBody());
        }

        @Test
        void testFindRestaurant_NotRegisteredId_ReturnNotFoundException() {
            // Arrange
            UUID notFoundUuid = UUID.randomUUID();
            when(restaurantGateway.findRestaurantById(notFoundUuid.toString())).thenThrow(NotFoundException.class);

            // Act
            ResponseEntity<?> response = restaurantController.findRestaurant(notFoundUuid.toString());

            // Assertio
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(restaurantGateway, times(1)).findRestaurantById(notFoundUuid.toString());
        }

        @Test
        void testFindRestaurant_InvalidId() {
            // Act
            ResponseEntity<?> response = restaurantController.findRestaurant("invalid-id");

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("ID inválido", response.getBody());
        }
    }

    @Nested
    class UpdeteRestaurant {
        @Test
        void testUpdateRestaurant_Success () {
            // Arrange
            UUID existingRestaurantId = UUID.randomUUID();
            Restaurant existingRestaurant = new Restaurant();
            existingRestaurant.setId(existingRestaurantId);

            Restaurant updatedRestaurant = new Restaurant();

            when(restaurantGateway.updateRestaurant(existingRestaurantId, updatedRestaurant)).thenReturn(updatedRestaurant);

            // Act
            ResponseEntity<?> response = restaurantController.updateRestaurant(existingRestaurantId.toString(), updatedRestaurant);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(updatedRestaurant, response.getBody());
        }

        @Test
        void testUpdateRestaurant_InvalidInputIdAndRestaurant_ReturnBadRequest() {
            // Act
            ResponseEntity<?> response = restaurantController.updateRestaurant("invalid-uuid", new Restaurant());

            // Assertions
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("ID inválido", response.getBody());
            verify(restaurantGateway, never()).updateRestaurant(any(), any());
        }

        @Test
        void testUpdateRestaurant_NotRegisteredId_ReturnNotFoundException() {
            // Arrange
            UUID notFoundUuid = UUID.randomUUID();
            when(restaurantGateway.updateRestaurant(notFoundUuid, new Restaurant())).thenThrow(RuntimeException.class);

            // Act
            ResponseEntity<?> response = restaurantController.updateRestaurant(notFoundUuid.toString(), new Restaurant());

            // Assertions
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(restaurantGateway, times(1)).updateRestaurant(notFoundUuid, new Restaurant());
        }
    }

    @Nested
    class DeleteRestaurant {
        @Test
        void testDeleteRestaurant_Success() {
            // Arrange
            UUID validUuid = UUID.randomUUID();

            // Act
            ResponseEntity<?> response = restaurantController.deleteRestaurant(validUuid.toString());

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Restaurante removido", response.getBody());
            verify(restaurantGateway, times(1)).deleteRestaurant(validUuid);
        }

        @Test
        void testDeleteRestaurant_InvalidInputId_ReturnBadRequest() {
            // Act
            ResponseEntity<?> response = restaurantController.deleteRestaurant("invalid-uuid");

            // Assertions
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("ID inválido", response.getBody());
            verify(restaurantGateway, never()).deleteRestaurant(any());
        }

        @Test
        void testDeleteRestaurant_NotRegisteredId_ReturnNotFoundException() {
            // Arrange
            UUID notFoundUuid = UUID.randomUUID();
            doThrow(RuntimeException.class).when(restaurantGateway).deleteRestaurant(notFoundUuid);

            // Act
            ResponseEntity<?> response = restaurantController.deleteRestaurant(notFoundUuid.toString());

            // Assertions
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(restaurantGateway, times(1)).deleteRestaurant(notFoundUuid);
        }
    }

}
