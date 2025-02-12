package com.art.service;

import java.util.List;

import com.art.dto.ReviewDto;

public interface ReviewService {
	 List<ReviewDto> getAllReviews();
	    String addReview(ReviewDto reviewDTO);
	    String deleteReview(Long reviewId);
	    ReviewDto getReviewDetails(Long reviewId);
	    ReviewDto updateReview(Long reviewId, ReviewDto reviewDTO);
}
