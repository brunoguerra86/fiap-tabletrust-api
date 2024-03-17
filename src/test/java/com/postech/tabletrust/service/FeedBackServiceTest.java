package com.postech.tabletrust.service;

import com.postech.tabletrust.dto.FeedBackCreateDTO;
import com.postech.tabletrust.entity.FeedBack;
import com.postech.tabletrust.entity.Reservation;
import com.postech.tabletrust.entity.Restaurant;
import com.postech.tabletrust.exception.InvalidReservationException;
import com.postech.tabletrust.repository.FeedBackRepository;
import com.postech.tabletrust.repository.ReservationRepository;
import com.postech.tabletrust.repository.RestaurantRepository;
import com.postech.tabletrust.utils.NewEntititesHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FeedBackServiceTest {
    @Mock
    private FeedBackRepository feedBackRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    private FeedBackService feedBackService;
    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        feedBackService = new FeedBackServiceImpl(feedBackRepository, reservationRepository, restaurantRepository); // Tem que fazer o mock do feedbackRepository
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class ListFeedbacks {
        @Test
        void shouldListAllFeedBacksByARestaurant(){
            Restaurant restaurant = NewEntititesHelper.createARestaurant();
            FeedBack fb = NewEntititesHelper.createAFeedBack();
            Pageable pageable = PageRequest.of(0,10);
            List<FeedBack> fbList = Arrays.asList(fb);

            Page<FeedBack> fbPage = new PageImpl<>(fbList,pageable,fbList.size());

            // Configuração dos mocks para o repositório
            when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));
            when(feedBackRepository.findByRestaurantId(restaurant.getId(), pageable)).thenReturn(fbPage);

            var listOfFB = feedBackService.listFeedBackByRestaurantId(pageable, restaurant.getId());

            assertThat(listOfFB).contains(fb);
        }

        @Test
        void shouldFindByID(){
            //Assert
            var fb = NewEntititesHelper.createAFeedBack();
            UUID id = fb.getId();

            when(feedBackRepository.findById(id)).thenReturn(Optional.of(fb));

            // Act
            FeedBack fbFound = feedBackService.findById(id);

            //Assert
            assertThat(fbFound).isNotNull().isInstanceOf(FeedBack.class);
            assertThat(fbFound).isEqualTo(fb);
            verify(feedBackRepository, times(1)).findById(any(UUID.class));
        }
    }


    @Nested
    class CreateFeedbacks{
        @Test
        void shouldCreateANewFeedBack() throws Exception {
            Reservation reservation = NewEntititesHelper.createAReservation();
            FeedBackCreateDTO feedBackDTO = NewEntititesHelper.createAFeedBack().convertToDTO();

            when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.of(reservation)); //Mock le findById
            when(feedBackRepository.save(any(FeedBack.class))).thenAnswer( i -> i.getArgument(0)); // Mock o save

            //Act
            var feedback = feedBackService.create(feedBackDTO);

            //Assert
            assertThat(feedback).isNotNull().isInstanceOf(FeedBack.class);
        }

        @Test
        void shouldReturnAnExceptionNotFoundReservation() throws Exception {
            Reservation reservation = NewEntititesHelper.createAReservation();
            reservation.setId(UUID.randomUUID()); //change id
            FeedBackCreateDTO feedBackDTO = NewEntititesHelper.createAFeedBack().convertToDTO();

            when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.empty()); //Not found

            assertThatThrownBy(() -> feedBackService.create(feedBackDTO))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("A reserva informada nao foi encontrada");
        }

        @Test
        void shouldReturnAnExceptionForReservationNotApprouved() throws Exception {
            Reservation reservation = NewEntititesHelper.createAReservation();
            reservation.setApproved(false); // Change 'approved'
            FeedBackCreateDTO feedBackDTO = NewEntititesHelper.createAFeedBack().convertToDTO();

            when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.of(reservation)); //OK

            assertThatThrownBy(() -> feedBackService.create(feedBackDTO))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("A reserva informada foi anulada ou ainda nao terminou");
        }

        @Test
        void shouldReturnAnExceptionForReservationInThreeHours() throws Exception {
            Reservation reservation = NewEntititesHelper.createAReservation();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime less1Hour = LocalDateTime.now().minusHours(1);
            reservation.setReservationDate(less1Hour); // Change hour by reservation - invalid
            FeedBackCreateDTO feedBackDTO = NewEntititesHelper.createAFeedBack().convertToDTO();

            when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.of(reservation)); //OK

            assertThatThrownBy(() -> feedBackService.create(feedBackDTO))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("A reserva informada foi anulada ou ainda nao terminou");
        }
    }

    @Nested
    class DeleteFeedbacks{
        @Test
        void shouldDeleteByID(){
            //Assert
            var fb = NewEntititesHelper.createAFeedBack();
            UUID id = fb.getId();

            when(feedBackRepository.findById(id)).thenReturn(Optional.of(fb));
            doNothing().when(feedBackRepository).deleteById(id);

            var removed = feedBackService.deleteById(id);
            assertThat(removed).isTrue();

            verify(feedBackRepository, times(1)).deleteById(any(UUID.class));
        }
    }
}
