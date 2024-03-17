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
        return reviewRepository.findById(id);
    }

    public void createReview(String text, String title) {
        Review review = new Review();
        review.setText(text);
        review.setMovie(movieService.getMovieByTitle(title));
        reviewRepository.save(review);
    }

    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void updateReview(Long id, String text) {
        Review review = reviewRepository.getReviewById(id);
        review.setText(text);
        reviewRepository.save(review);
    }
}
