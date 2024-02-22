package com.postech.tabletrust.service;

import com.postech.tabletrust.entities.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface CommentsService {

    Page<Comments> listComments(Pageable pageable);

    public Comments create(Comments comments);

    public Comments findById(UUID id);

    public void deleteById(UUID id);

}
