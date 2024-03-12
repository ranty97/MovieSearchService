package org.example.moviesearchservice.service;

import org.example.moviesearchservice.model.Review;
import org.example.moviesearchservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void createReview(Review review) {reviewRepository.save(review);}
}
