package org.example.moviesearchservice.controller;


import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    public final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{title}")
    public Optional<Movie> getMovieByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }

    @PostMapping
    public Movie saveMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @DeleteMapping("/{title}")
    public void deleteMovie(@PathVariable String title) {
        movieService.deleteMovieByTitle(title);
    }
}
