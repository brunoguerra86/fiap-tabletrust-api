package com.postech.tabletrust.service;

import com.postech.tabletrust.dto.FeedBackCreateDTO;
import com.postech.tabletrust.entity.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface FeedBackService {

    Page<FeedBack> listFeedBackByRestaurantId(Pageable pageable, UUID restaurantId);
    FeedBack create(FeedBackCreateDTO feedBackCreateDTO) throws Exception;
    FeedBack findById(UUID id);
    Boolean deleteById(UUID id);
    //Nao pode update um feedback
}
