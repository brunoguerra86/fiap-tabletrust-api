package com.postech.tabletrust.repository;

import com.postech.tabletrust.entities.FeedBack;
import com.postech.tabletrust.entities.Restaurant;
import org.hibernate.type.descriptor.java.LocalTimeJavaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FeedBackRepositoryTest {

    @Mock //Deja dans les dependances
    private FeedBackRepository feedBackRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldListAllFeedbacks(){
        //Arrange
        FeedBack feedback = createAFeedBack();
        FeedBack feedback2 = createAFeedBack();
        feedback2.setId(UUID.randomUUID());

        Pageable pageable = PageRequest.of(0, 10);
        List<FeedBack> feedBackList = Arrays.asList(feedback, feedback2); // Adicione objetos Feedback reais aqui
        Page<FeedBack> feedBackPage = new PageImpl<>(feedBackList, pageable, feedBackList.size());

        when(feedBackRepository.listFeedback(pageable)).thenReturn(feedBackPage);

        //Act
        Page<FeedBack> result = feedBackRepository.listFeedback(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(feedBackList.size(), result.getContent().size());
        assertTrue(result.getContent().containsAll(feedBackList));
    }

    private FeedBack createAFeedBack(){
        UUID customerID = UUID.fromString("b732236c-3c25-4290-bfe2-93ec920bcfa9");
        UUID restaurantID = UUID.fromString("c68b4872-6073-4dff-8199-a24c74d4c763");
        UUID reservationID = UUID.fromString("38f6df39-9118-4610-a435-7572648540a0");
        UUID feedbackID = UUID.fromString("7cad184d-6b00-4e20-bdeb-d4e224cf3bbd");

        return FeedBack.builder()
                .id(feedbackID)
                .comment("OTIMO")
                .restaurantId(restaurantID)
                .customerId(customerID)
                .reservationId(reservationID)
                .stars(5)
                .build();
    }

}
