package com.postech.tabletrust.gateway;

import com.postech.tabletrust.entity.Restaurant;
import com.postech.tabletrust.gateways.RestaurantGateway;
import com.postech.tabletrust.interfaces.IRestaurantGateway;
import com.postech.tabletrust.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantGatewayTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    //@InjectMocks
    private IRestaurantGateway restaurantGateway; // Aqui é onde você coloca @InjectMocks

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        restaurantGateway = new RestaurantGateway(restaurantRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

//    @Test
//    void shouldAllowToCreateARestaurant(){
//        //Arrange
//        var restaurant = createARestaurant();
//        when(restaurantRepository.save(any(Restaurant.class))).thenAnswer( i -> i.getArgument(0)); //Quando ele recebe um restaurante deve responder com um parametro que é o restaurante salvo
//
//        // Act
//        var restaurantSave = restaurantGateway.newRestaurant(restaurant);
//
//        // Assert
//        assertThat(restaurantSave)
//                .isInstanceOf(Restaurant.class)
//                .isNotNull();
//        assertThat(restaurantSave.getId()).isNotNull();
//        //verify(restaurantGateway, times(1)).newRestaurant(any(Restaurant.class));
//    }
//
//    @Test
//    void shouldBeFindARestaurantById(){ //Diagrama de fluxo - o id é valido ? case Sim e case Non
//        //Arrange
//        UUID id = UUID.fromString("da965093-b853-498b-bfe5-b1298630a7c4");
//        var restaurant = createARestaurant();
//        restaurant.setId(id);
//
//        when(restaurantRepository.findById(any(UUID.class))).thenReturn(Optional.of(restaurant)); // Assegure que o findById está mockado corretamente
//
//        // Act
//        Restaurant restaurantFound = restaurantGateway.findRestaurantById(id.toString());
//
//        //Assert
//        assertThat(restaurantFound).isNotNull().isInstanceOf(Restaurant.class);
//        assertThat(restaurantFound).isEqualTo(restaurant);
//        verify(restaurantRepository, times(1)).findById(any(UUID.class));
//    }
//
//    @Test
//    void shouldBeGenerateAExceptionWhenRestaurantIdNotFound(){
//        //Arrange
//        UUID id = UUID.fromString("57ac89e1-ead8-46bb-afef-5c01c0941e17");
//
//        //Act
//        when(restaurantRepository.findById(id)).thenReturn(Optional.empty()); // Assegure que o findById está mockado corretamente
//
//        //Assert
//        assertThatThrownBy(() -> restaurantGateway.findRestaurantById(id.toString()))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessage("Restaurante não encontrado");
//
//        verify(restaurantRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void shouldFindARestaurantByNameAddressKitchenType(){
//        //Arrange
//        UUID id = UUID.randomUUID(); //TODO https://www.uuidgenerator.net/version4
//        var restaurant = createARestaurant();
//        restaurant.setId(id);
//        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant)); // Assegure que o findById está mockado corretamente
//
//        // Act
//        List restaurantFound = restaurantGateway.findRestaurantsByNameAndAddressAndKitchenType(restaurant.getName(), restaurant.getAddress(), restaurant.getKitchenType());
//
//        //Assert
//        assertThat(restaurantFound).isNotNull().isInstanceOf(List.class);
//        assertThat(restaurantFound.contains(restaurant));
//    }
//
//    @Test
//    void shouldAllowUpdateRestaurant(){
//        //Arrange
//        UUID id = UUID.randomUUID();
//        var restaurant = createARestaurant();
//        restaurant.setId(id);
//
//        var restaurantUp = restaurant.clone(); // Senao ele pega a referencia para o mesmo objeto e os dois 'restaurant' serao setName
//        restaurantUp.setName("UPDATED OK");
//
//        //Act
//        when(restaurantRepository.findById(id))
//                .thenReturn(Optional.of(restaurantUp));
//        when(restaurantRepository.save(restaurantUp)).thenAnswer(i -> i.getArgument(0));
//
//        Restaurant restaurantUpdated = restaurantGateway.updateRestaurant(id, restaurantUp);
//
//        //Assert
//        assertThat(restaurantUpdated).isNotNull().isInstanceOf(Restaurant.class);
//        assertThat(restaurantUpdated.getId()).isEqualTo(id);
//        assertThat(restaurantUpdated).isNotEqualTo(restaurant);
//        verify(restaurantRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void shouldGenerateAExceptionWhenWrongIdAndRestaurantNotUpdated(){
//        //Arrange
//        UUID id = UUID.randomUUID();
//        var restaurant = createARestaurant();
//        restaurant.setId(id);
//
//        //Act
//        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());
//
//        //Assert
//        assertThatThrownBy(() -> restaurantGateway.updateRestaurant(id, restaurant))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessage("Restaurante não encontrado");
//
//        verify(restaurantRepository, times(1)).findById(id);
//        verify(restaurantRepository, never()).save(any(Restaurant.class)); //Garantir que o save nao foi executado porque teve a exception antes
//    }
//
//    @Test
//    void shouldAllowDeleteRestaurant(){
//        //Assert
//        UUID id = UUID.fromString("57ac89e1-ead8-46bb-afef-5c01c0941e17");
//        var restaurant = createARestaurant();
//        restaurant.setId(id);
//
//        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));
//        doNothing().when(restaurantRepository).deleteById(id);
//
//        var removed = restaurantGateway.deleteRestaurant(id);
//        assertThat(removed).isTrue();
//
//        verify(restaurantRepository, times(1)).deleteById(any(UUID.class));
//    }
//
//    private Restaurant createARestaurant(){
//        LocalTime open = new LocalTimeJavaType().fromString("19:00:00");
//        LocalTime close = new LocalTimeJavaType().fromString("23:30:00");
//
//        return Restaurant.builder()
//                .id(UUID.randomUUID())
//                .address("Fragonard")
//                .kitchenType("Tapioca")
//                .name("Restaurante-teste")
//                .openingTime(open)
//                .closingTime(close)
//                .availableCapacity(100)
//                .build();
//    }
}
