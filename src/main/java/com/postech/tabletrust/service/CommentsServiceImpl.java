package com.postech.tabletrust.service;

import com.postech.tabletrust.entities.Comments;
import com.postech.tabletrust.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService{

    private final CommentRepository commentRepository;

    @Override
    public Page<Comments> listComments(Pageable pageable) {
        return commentRepository.listComments(pageable);
    }

    @Override
    public Comments create(Comments comments) {
        return this.commentRepository.save(comments);
    }

    @Override
    public Comments findById(UUID id) {
        return this.commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("ID não encontrado"));
    }

    @Override
    public void deleteById(UUID id) {
        this.commentRepository.deleteById(id);
    }

}
