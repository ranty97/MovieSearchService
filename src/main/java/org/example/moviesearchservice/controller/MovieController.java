package org.example.moviesearchservice.controller;


import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Movie getMovieByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }

    @PostMapping("/create")
    public void saveMovie(@RequestParam("title") String title,
                           @RequestParam("genre") String genre,
                           @RequestParam("premiere") String premiere,
                           @RequestParam("language") String language,
                           @RequestParam("runtime") int runtime,
                           @RequestParam("imdb_score") double imdbScore
                           ) {
        Movie movie = new Movie();
        movie.setGenre(genre);
        movie.setTitle(title);
        movie.setPremiere(premiere);
        movie.setLanguage(language);
        movie.setRuntime(runtime);
        movie.setImdbScore(imdbScore);
        movieService.saveMovie(movie);
    }

    @DeleteMapping("/delete/{title}")
    @Transactional
    public void deleteMovie(@PathVariable("title") String title) {
        movieService.deleteMovieByTitle(title);
    }
    @PostMapping("/genre")
    public void saveGenre(@RequestBody Movie movie) {
        movieService.saveMovie(movie);
    }
}
