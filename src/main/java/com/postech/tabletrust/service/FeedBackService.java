package com.postech.tabletrust.service;

import com.postech.tabletrust.dto.FeedBackCreateDTO;
import com.postech.tabletrust.entities.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface FeedBackService {

    public Page<FeedBack> listFeedback(Pageable pageable);
    public FeedBack create(FeedBackCreateDTO feedBackCreateDTO) throws Exception;
    public FeedBack findById(UUID id);
    public void deleteById(UUID id);

}
