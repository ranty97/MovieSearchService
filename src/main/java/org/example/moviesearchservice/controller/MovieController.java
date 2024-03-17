package org.example.moviesearchservice.controller;

import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/{title}")
    public Movie getMovieByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }

    @ResponseStatus(HttpStatus.CREATED)
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
    @PatchMapping("/update/{title}")
    public void updateMovie(@PathVariable("title") String title,
                            @RequestParam("premiere") String premiere,
                            @RequestParam("language") String language,
                            @RequestParam("runtime") int runtime,
                            @RequestParam("imdb_score") double imdbScore) {
        movieService.updateMovieByTitle(title, premiere, language, runtime, imdbScore);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{title}")
    public void deleteMovie(@PathVariable("title") String title) {
        movieService.deleteMovieByTitle(title);
    }
}