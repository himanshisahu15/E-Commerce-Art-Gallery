package com.art.service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.art.dao.ArtworkDao;
import com.art.dao.ReviewDao;
import com.art.dao.UserDao;
import com.art.dto.ReviewDto;
import com.art.exception.ResourceNotFoundException;
import com.art.pojos.Artwork;
import com.art.pojos.Review;
import com.art.pojos.User;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewRepository;
    
    @Autowired
    private UserDao userRepository;
    
    @Autowired
    private ArtworkDao artworkRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ReviewDto> getAllReviews() {
//        return reviewRepository.findAll().stream()
//                .map(review -> mapper.map(review, ReviewDto.class))
//                .collect(Collectors.toList());
        
        return reviewRepository.findAll().stream()
                .map(review -> {
                    ReviewDto reviewDto = mapper.map(review, ReviewDto.class);
                
                    reviewDto.setUserId(review.getUser().getUserId());
                    reviewDto.setArtworkId(review.getArtwork().getArtId());
                    return reviewDto;
                })
                .collect(Collectors.toList());

        
        
    }

    @Override
    public String addReview(ReviewDto reviewDTO) {
    	System.out.println(reviewDTO.getReviewId());
    	Long userId=reviewDTO.getUserId();
    	Long artworkId=reviewDTO.getArtworkId();
    	
    	User user=userRepository.findById(userId)
    			.orElseThrow(()->new ResourceNotFoundException("user not found"));
    	Artwork artwork=artworkRepository.findById(artworkId)
    			.orElseThrow(()->new ResourceNotFoundException("Artwork not found"));
        Review review = mapper.map(reviewDTO, Review.class);
        review.setUser(user);
        review.setArtwork(artwork);
        Review savedReview = reviewRepository.save(review);
        return "New Review added with id " + savedReview.getReviewId();
    }

    @Override
    public String deleteReview(Long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return "Review deleted";
        }
        throw new ResourceNotFoundException("Invalid Review ID!");
    }

    @Override
    public ReviewDto getReviewDetails(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Review ID"));
        Long userId=review.getUser().getUserId();
        Long artworkId=review.getArtwork().getArtId();
        ReviewDto reviewDto=mapper.map(review, ReviewDto.class);
        reviewDto.setUserId(userId);
        reviewDto.setArtworkId(artworkId);
        
        return reviewDto;
    }

    @Override
    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDTO) {
    	Review review=reviewRepository.findById(reviewId)
    			.orElseThrow(()->new ResourceNotFoundException("invalid review id"));
    	review.setComment(reviewDTO.getComment());
    	review.setRating(reviewDTO.getRating());
    	review.setReviewDate(reviewDTO.getReviewDate());
    	
       if(reviewDTO.getUserId()!=null) {
    	   User user=userRepository.findById(reviewDTO.getUserId())
    			   .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    	   review.setUser(user);
       }
       if(reviewDTO.getArtworkId()!=null) {
    	   Artwork artwork=artworkRepository.findById(reviewDTO.getArtworkId())
    			   .orElseThrow(() -> new ResourceNotFoundException("artwork not found"));
    	   review.setArtwork(artwork);
       }
       
       Review updateReview=reviewRepository.save(review);
       
       ReviewDto updatedReviewDTO=mapper.map(updateReview, ReviewDto.class);
       
       updatedReviewDTO.setUserId(updateReview.getUser().getUserId());
       updatedReviewDTO.setArtworkId(updateReview.getArtwork().getArtId());
       
       
       return updatedReviewDTO;
    }
}