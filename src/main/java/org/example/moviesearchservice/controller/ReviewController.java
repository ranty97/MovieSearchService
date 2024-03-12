package org.example.moviesearchservice.controller;


import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.model.Review;
import org.example.moviesearchservice.service.MovieService;
import org.example.moviesearchservice.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    public final MovieService movieService;
    public final ReviewService reviewService;

    public ReviewController(MovieService movieService, ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public void saveReview(@RequestParam("text") String text,
                           @RequestParam("title") String title) {
        Review review = new Review();
        review.setText(text);
        Movie movie = movieService.getMovieByTitle(title);

        review.setMovie(movie);

        reviewService.createReview(review);
    }
}
