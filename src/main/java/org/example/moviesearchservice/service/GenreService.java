package org.example.moviesearchservice.service;

import org.example.moviesearchservice.component.Cache;
import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.repository.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieService movieService;
    private final Cache cache;
    private static final String CACHE_KEY = "genre-";

    public GenreService(GenreRepository genreRepository, MovieService movieService, Cache cache) {
        this.genreRepository = genreRepository;
        this.movieService = movieService;
        this.cache = cache;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long createGenre(String name, List<Long> moviesIds) {
        Genre genre = new Genre();
        genre.setName(name);

        if (moviesIds != null) {
            List<Movie> movieList = new ArrayList<>();
            for (Long movieId : moviesIds) {
                movieList.add(movieService.getMovieById(movieId));
            }
            genre.setMovies(movieList);
            }
        cache.removeFromCache(CACHE_KEY + name);
        return genreRepository.save(genre).getId();
    }

    public void deleteGenreById(Long id) {
        genreRepository.deleteById(id);
    }

    public void updateGenre(Long id, String name, List<Long> moviesIds) {
        Genre genre = genreRepository.findGenreById(id);
        genre.setName(name);

        if (moviesIds != null) {
            List<Movie> movieList = new ArrayList<>();
            for (Long movieId : moviesIds) {
                movieList.add(movieService.getMovieById(movieId));
            }
            genre.setMovies(movieList);
        }
        cache.removeFromCache(CACHE_KEY + name);
        genreRepository.save(genre);
    }

    public void addMovieToGenre(Long genreId, Long movieId) {
        Genre genre = genreRepository.findGenreById(genreId);
        Movie movie = movieService.getMovieById(movieId);
        if (movie != null && genre != null) {
            genre.getMovies().add(movie);
            cache.removeFromCache(CACHE_KEY + genreId);
            genreRepository.save(genre);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

