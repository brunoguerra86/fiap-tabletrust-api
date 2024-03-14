package com.postech.tabletrust.entity;

import com.postech.tabletrust.controller.RestaurantController;
import com.postech.tabletrust.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RestaurantTest {

    @Test
    public void testClone() {
        // Arrange
        Restaurant original = new Restaurant();
        original.setId(UUID.randomUUID());
        original.setName("Restaurante A");

        // Act
        Restaurant cloned = original.clone();

        // Assert
        assertEquals(original.getId(), cloned.getId());
        assertEquals(original.getName(), cloned.getName());
    }

    @Test
    void testCloneThrowsException() {
        Restaurant original = new Restaurant();
        assertThrows(CloneNotSupportedException.class, () -> original.clone(),
                "Expected CloneNotSupportedException was not thrown.");
    }
}
