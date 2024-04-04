package org.example.moviesearchservice.service;

import org.example.moviesearchservice.model.Review;
import org.example.moviesearchservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieService movieService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, MovieService movieService) {
        this.reviewRepository = reviewRepository;
        this.movieService = movieService;
    }

    public Optional<Review> getReviewById(Long id) {
        return Optional.ofNullable(reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ERROR 500 (not found)")));
    }

    public Long createReview(String text, Long id) {
        Review review = new Review();
        review.setText(text);
        review.setMovie(movieService.getMovieById(id));
        return reviewRepository.save(review).getId();
    }

    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Long updateReview(Long id, String text) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + id));
        review.setText(text);
        Review updatedReview = reviewRepository.save(review);
        return updatedReview.getId();
    }
}
