package org.example.moviesearchservice.controller;

import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;


    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{name}")
    public Genre getGenreByName(@PathVariable String name) {
        return genreService.getGenreByName(name);
    }

    @PostMapping("/create")
    public Long createGenre(@RequestParam("name") String name) {
        return genreService.createGenre(name);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteGenre(@PathVariable String name) {
        genreService.deleteGenreByName(name);
    }
}
