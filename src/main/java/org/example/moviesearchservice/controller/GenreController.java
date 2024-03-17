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

    @GetMapping("/{id}")
    public Genre getGenreByName(@PathVariable Long id) {
        return genreService.getGenreById(id);
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
    @Transactional
    @PutMapping("/update/{id}")
    public void updateGenre(@RequestBody(required = false) List<Long> movies,
                            @PathVariable("id") Long id,
                            @RequestParam("name") String name) {
        genreService.updateGenre(id, name, movies);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @PatchMapping("/relationship")
    public void addMovieToGenre(@RequestParam("genreId") Long genreId, @RequestParam("movieId") Long movieId) {
        genreService.addMovieToGenre(genreId, movieId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteGenre(@PathVariable Long id) {
        genreService.deleteGenreById(id);
    }
}
