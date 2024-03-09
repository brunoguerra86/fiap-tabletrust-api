package com.postech.tabletrust.service;

import com.postech.tabletrust.dto.FeedBackCreateDTO;
import com.postech.tabletrust.entity.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface FeedBackService {

    public Page<FeedBack> listFeedbackByRestaurant(Pageable pageable, UUID restaurantId);
    public FeedBack create(FeedBackCreateDTO feedBackCreateDTO) throws Exception;
    public FeedBack findById(UUID id);
    public Boolean deleteById(UUID id);
    public List<FeedBack> getFeedBackByRestaurantId(UUID id);
    //Nao pode update um feedback
}
