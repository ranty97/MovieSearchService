package org.example.moviesearchservice.service;

import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    public Long createGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        return genreRepository.save(genre).getId();
    }

    public void deleteGenreByName(String name) {
        genreRepository.deleteByName(name);
    }
}
