package com.postech.tabletrust.repository;

import com.postech.tabletrust.entities.FeedBack;
import com.postech.tabletrust.utils.NewEntititesHelper;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        FeedBack feedback = NewEntititesHelper.createAFeedBack();
        FeedBack feedback2 = NewEntititesHelper.createAFeedBack();
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

    @Test
    void shouldCreateAFeedBack(){
        var feedback = NewEntititesHelper.createAFeedBack();

        when(feedBackRepository.save(feedback)).thenReturn(feedback);

        var fbFound = feedBackRepository.save(feedback);

        assertThat(fbFound).isNotNull().isInstanceOf(FeedBack.class);
        assertThat(fbFound).isEqualTo(feedback);
    }

    @Test
    void shouldFindById(){
        var feedback = NewEntititesHelper.createAFeedBack();
        UUID id = feedback.getId();

        when(feedBackRepository.findById(id)).thenReturn(Optional.of(feedback));

        var fbFound = feedBackRepository.findById(id);

        assertThat(fbFound).isPresent().containsSame(feedback);
    }

    @Test
    void shouldDeleteById(){
        var feedback = NewEntititesHelper.createAFeedBack();
        UUID id = feedback.getId();

        doNothing().when(feedBackRepository).deleteById(any(UUID.class));

        feedBackRepository.deleteById(id);

        verify(feedBackRepository, times(1)).deleteById(any(UUID.class));
    }
}
