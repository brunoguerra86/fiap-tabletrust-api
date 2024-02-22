package com.postech.tabletrust.controller;

import com.postech.tabletrust.entities.Comments;
import com.postech.tabletrust.service.CommentsService;
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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentsService commentsService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Comments>> listComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Requisição para listar comentários foi efetuada: Página={}, Tamanho={}", page, size);
        Page<Comments> comments = commentsService.listComments(pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Comments create(@RequestBody Comments comments){
        return this.commentsService.create(comments);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Comments findById(@PathVariable UUID id){
        return this.commentsService.findById(id);
    }

    @DeleteMapping(value="/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        this.commentsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
