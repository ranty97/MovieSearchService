package com.moviesearceservice.api.controller;

import org.example.moviesearchservice.controller.ReviewController;
import org.example.moviesearchservice.model.Review;
import org.example.moviesearchservice.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewControllerTest {
    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetReviewById_ValidId() {
        Long validId = 1L;
        Review expectedReview = new Review();
        when(reviewService.getReviewById(validId)).thenReturn(Optional.of(expectedReview));

        Optional<Review> actualReview = reviewController.getReviewById(validId);

        assertEquals(expectedReview, actualReview.get());
    }

    @Test
    void testGetAllReviews_NonEmptyList() {
        List<Review> mockReviews = Collections.singletonList(new Review());
        when(reviewService.getAllReviews()).thenReturn(mockReviews);

        List<Review> result = reviewController.getAllReviews();

        assertEquals(mockReviews, result);
    }

    @Test
    void testCreateReview_ValidInput() {
        when(reviewService.createReview("Great movie!", 1L)).thenReturn(1L);

        ReviewController reviewController = new ReviewController(reviewService);
        Long result = reviewController.createReview("Great movie!", 1L);

        assertEquals(1L, result);
    }

    @Test
    void testUpdateReview_ValidIdAndText() {
        Long validId = 1L;
        String validText = "Updated text";
        when(reviewService.updateReview(validId, validText)).thenReturn(validId);

        Long result = reviewController.updateReview(validId, validText);

        assertEquals(validId, result);
    }

    @Test
    void testDeleteReviewById() {
        Long id = 1L;

        doNothing().when(reviewService).deleteReviewById(id);
        Long result = reviewController.deleteReview(id);

        verify(reviewService).deleteReviewById(id);
        assertEquals(id, result);
    }
}
