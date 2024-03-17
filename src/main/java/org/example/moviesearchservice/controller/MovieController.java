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

    @GetMapping("/sterlings")
    public List<Movie> getAllSterlingMovies(@RequestParam("genre") String genre) {
        return movieService.getAllSterlingMovies(genre);
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

    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @PatchMapping("/update/{id}")
    public void updateMovie(@PathVariable("id") Long id,
                            @RequestParam("title") String title,
                            @RequestParam("premiere") String premiere,
                            @RequestParam("language") String language,
                            @RequestParam("runtime") int runtime,
                            @RequestParam("imdb_score") double imdbScore) {
        movieService.updateMovieById(id, title, premiere, language, runtime, imdbScore);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovieById(id);
    }
}