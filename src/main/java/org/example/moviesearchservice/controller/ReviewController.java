package org.example.moviesearchservice.controller;

import org.example.moviesearchservice.model.Review;
import org.example.moviesearchservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public void saveReview(@RequestParam("text") String text,
                           @RequestParam("title") String title) {
        reviewService.createReview(text, title);
    }

    @GetMapping("/{id}")
    public Optional<Review> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
    }

    @GetMapping("/")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PatchMapping("/update/{id}")
    public void updateReview(@PathVariable Long id, @RequestParam("text") String text) {
        reviewService.updateReview(id, text);
    }

}
