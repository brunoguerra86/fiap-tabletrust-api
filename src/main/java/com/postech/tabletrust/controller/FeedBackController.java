package com.postech.tabletrust.controller;

import com.postech.tabletrust.entities.FeedBack;
import com.postech.tabletrust.service.FeedBackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/feedBack")
@RequiredArgsConstructor
public class FeedBackController {

    private final FeedBackService feedBackService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FeedBack>> listFeedback(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, UUID restaurantId) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Requisição para listar comentários foi efetuada: Página={}, Tamanho={}", page, size);
        Page<FeedBack> FeedBack = feedBackService.listFeedbackByRestaurant(pageable, restaurantId);
        return new ResponseEntity<>(FeedBack, HttpStatus.OK);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public FeedBack create(@RequestBody FeedBack FeedBack){
        //return this.feedBackService.create(FeedBack);
        return null;
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FeedBack findById(@PathVariable UUID id){
        return this.feedBackService.findById(id);
    }

    @DeleteMapping(value="/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        this.feedBackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
