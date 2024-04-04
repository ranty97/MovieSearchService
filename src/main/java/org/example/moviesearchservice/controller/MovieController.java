package org.example.moviesearchservice.controller;

import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/with review")
    public List<Movie> getAllWithReviewMovies(@RequestParam("genre") String genre) {
        return movieService.getAllWithReviewMovies(genre);
    }

    @GetMapping("/{id}")
    public Movie getMovieByTitle(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @PostMapping("/create")
    public Long createMovie(@RequestParam("title") String title,
                            @RequestParam("premiere") String premiere,
                            @RequestParam("language") String language,
                            @RequestParam("runtime") int runtime,
                            @RequestParam("imdb_score") double imdbScore
    ) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setPremiere(premiere);
        movie.setLanguage(language);
        movie.setRuntime(runtime);
        movie.setImdbScore(imdbScore);
        return movieService.createMovie(movie);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @PostMapping("/create/several")
    public List<Long> createSeveralMovies(@RequestBody List<Movie> movies) {
        return movieService.createMovies(movies);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @PutMapping("/update/{id}")
    public Long updateMovie(@PathVariable("id") Long id,
                            @RequestParam("title") String title,
                            @RequestParam("premiere") String premiere,
                            @RequestParam("language") String language,
                            @RequestParam("runtime") int runtime,
                            @RequestParam("imdb_score") double imdbScore) {
        return movieService.updateMovieById(id, title, premiere, language, runtime, imdbScore);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public Long deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovieById(id);
        return id;
    }
}