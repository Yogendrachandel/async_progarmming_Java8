package com.learn.service;

import com.learn.domain.Review;

import static com.learn.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}
