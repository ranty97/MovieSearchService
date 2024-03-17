package org.example.moviesearchservice.controller;

import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;


    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{name}")
    public Genre getGenreByName(@PathVariable String name) {
        return genreService.getGenreByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @PostMapping("/create")
    public Long createGenre(@RequestParam("name") String name,
                            @RequestBody(required = false) List<Long> movies
                            ) {
        return genreService.createGenre(name, movies);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update/{id}")
    public void updateGenre(@RequestBody(required = false) List<Long> movies,
                            @PathVariable("id") Long id,
                            @RequestParam("name") String name) {
        genreService.updateGenre(id, name, movies);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @PutMapping("/relationship")
    public void addMovieToGenre(@RequestParam("genre") String genreName, @RequestParam("movie") String movieName) {
        genreService.addMovieToGenre(genreName, movieName);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @DeleteMapping("/delete/{name}")
    public void deleteGenre(@PathVariable String name) {
        genreService.deleteGenreByName(name);
    }
}
