package org.example.moviesearchservice.controller;

import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Long id) {
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
    public Long updateGenre(@RequestBody(required = false) List<Long> movies,
                            @PathVariable("id") Long id,
                            @RequestParam("name") String name) {
        return genreService.updateGenre(id, name, movies);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @PatchMapping("/relationship")
    public void addMovieToGenre(@RequestParam("genreId") Long genreId, @RequestParam("movieId") Long movieId) {
        genreService.addMovieToGenre(genreId, movieId);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public Long deleteGenre(@PathVariable Long id) {
        genreService.deleteGenreById(id);
        return id;
    }
}
