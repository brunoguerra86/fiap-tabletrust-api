package com.postech.tabletrust.controller;

import com.postech.tabletrust.entity.FeedBack;
import com.postech.tabletrust.dto.FeedBackCreateDTO;
import com.postech.tabletrust.exception.InvalidReservationException;
import com.postech.tabletrust.gateways.FeedBackGateway;
import com.postech.tabletrust.gateways.ReservationGateway;
import com.postech.tabletrust.usecases.FeedBackUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedBackController {

    private final ReservationGateway reservationGateway;
    private final FeedBackGateway feedbackGateway;

    @GetMapping(value = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FeedBack>> listFeedBackByRestaurantId(
            @PageableDefault(size = 10) Pageable pageable,
            @PathVariable UUID restaurantId) {
        Page<FeedBack> feedBackPage = feedbackGateway.feedBackByRestaurantIdPageable(pageable, restaurantId);
        return new ResponseEntity<>(feedBackPage, HttpStatus.OK);
    }
    @GetMapping(value = "/stars/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity numberOfStarsByRestaurant(@PathVariable UUID restaurantId) {
        double numberOfStars = FeedBackUseCase.numberOfStarsByRestaurant(restaurantId, feedbackGateway);
        return new ResponseEntity<>(numberOfStars, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody FeedBackCreateDTO feedBackCreateDTO) throws Exception {
        try {
            FeedBack newFeedback = FeedBackUseCase.registerFeedBack(feedBackCreateDTO, reservationGateway);
            feedbackGateway.createFeedback(newFeedback);
            return new ResponseEntity<>(newFeedback, HttpStatus.OK);
        } catch(InvalidReservationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable UUID id){
        FeedBack feedBackFound = this.feedbackGateway.findById(id);
        return ResponseEntity.ok(feedBackFound);
    }

    @DeleteMapping(value="/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        this.feedbackGateway.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
