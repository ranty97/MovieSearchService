package com.moviesearceservice.api.service;

import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.model.Review;
import org.example.moviesearchservice.repository.ReviewRepository;
import org.example.moviesearchservice.service.MovieService;
import org.example.moviesearchservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieService movieService;

    @Test
    void testGetReviewById_ReviewExists() {
        // Arrange
        Long id = 1L;
        Review expectedReview = new Review(id, "Great movie!", null);
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        when(reviewRepository.findById(id)).thenReturn(Optional.of(expectedReview));

        ReviewService reviewService = new ReviewService(reviewRepository, movieService);

        // Act
        Optional<Review> result = reviewService.getReviewById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedReview, result.get());
    }

    @Test
    void testCreateReview() {
        // Arrange
        String text = "Great movie!";
        Long movieId = 1L;
        Long reviewId = 123L;

        // Mocking movie service
        MovieService movieService = mock(MovieService.class);
        Movie movie = new Movie(movieId, "Movie Title", "2022-01-01", "English", 120, 8.5, null, null);
        when(movieService.getMovieById(movieId)).thenReturn(movie);

        // Mocking review repository
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        Review savedReview = new Review(reviewId, text, null);
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // Creating review service instance
        ReviewService reviewService = new ReviewService(reviewRepository, movieService);

        // Act
        Long result = reviewService.createReview(text, movieId);

        // Assert
        assertEquals(reviewId, result);
    }

    @Test
    void testDeleteReviewById() {
        // Arrange
        Long reviewId = 1L;

        // Mocking review repository
        ReviewRepository reviewRepository = mock(ReviewRepository.class);

        // Creating review service instance
        ReviewService reviewService = new ReviewService(reviewRepository, movieService);

        // Act
        reviewService.deleteReviewById(reviewId);

        // Assert
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    void testGetAllReviews_WhenReviewsExist() {
        // Setup
        ReviewRepository reviewRepository = Mockito.mock(ReviewRepository.class);
        List<Review> mockReviews = Arrays.asList(new Review(), new Review());
        Mockito.when(reviewRepository.findAll()).thenReturn(mockReviews);

        ReviewService reviewService = new ReviewService(reviewRepository, movieService);

        // Verify
        List<Review> result = reviewService.getAllReviews();
        assertEquals(mockReviews, result);
    }

    @Test
    void testUpdateReview() {
        // Arrange
        Long reviewId = 1L;
        String newText = "Updated text";
        Review originalReview = new Review();
        originalReview.setId(reviewId);
        originalReview.setText("Original text");

        Review updatedReview = new Review();
        updatedReview.setId(reviewId);
        updatedReview.setText(newText);

        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(originalReview));
        when(reviewRepository.save(any())).thenReturn(updatedReview);

        ReviewService reviewService = new ReviewService(reviewRepository, movieService);

        // Act
        Long resultId = reviewService.updateReview(reviewId, newText);

        // Assert
        assertEquals(reviewId, resultId);
        assertEquals(newText, updatedReview.getText());
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).save(originalReview);
    }
}
