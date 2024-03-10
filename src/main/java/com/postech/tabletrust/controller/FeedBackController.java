package com.postech.tabletrust.controller;

import com.postech.tabletrust.dto.FeedBackCreateDTO;
import com.postech.tabletrust.entities.FeedBack;
import com.postech.tabletrust.service.FeedBackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    private final FeedBackService feedBackService;

    @GetMapping(value = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FeedBack>> listFeedBackByRestaurantId(
            @PageableDefault(size = 10) Pageable pageable,
            @PathVariable UUID restaurantId) {
        Page<FeedBack> feedBackPage = feedBackService.listFeedBackByRestaurantId(pageable, restaurantId);
        return new ResponseEntity<>(feedBackPage, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody FeedBackCreateDTO feedBackCreateDTO) throws Exception {
        FeedBack feedBackCreated = this.feedBackService.create(feedBackCreateDTO);
        return ResponseEntity.ok(feedBackCreated);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable UUID id){
        FeedBack feedBackFound = this.feedBackService.findById(id);
        return ResponseEntity.ok(feedBackFound);
    }

    @DeleteMapping(value="/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        this.feedBackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
