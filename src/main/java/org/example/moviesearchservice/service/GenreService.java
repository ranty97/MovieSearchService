package org.example.moviesearchservice.service;

import org.example.moviesearchservice.component.Cache;
import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.repository.GenreRepository;
import org.springframework.stereotype.Service;

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

    public Genre getGenreByName(String name) {
        return genreRepository.findByName(name);
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
        } else throw new IllegalArgumentException("Movies list is null");
        cache.removeFromCache(CACHE_KEY + name);
        return genreRepository.save(genre).getId();
    }

    public void deleteGenreByName(String name) {
        genreRepository.deleteByName(name);
    }

    public void updateGenre(Long id, String name, List<Long> moviesIds) {
        Genre genre = genreRepository.findGenreById(id);
        genre.setName(name);

        if(moviesIds != null) {
            List<Movie> movieList = new ArrayList<>();
            for (Long movieId : moviesIds) {
                movieList.add(movieService.getMovieById(movieId));
            }
            genre.setMovies(movieList);
        } else throw new IllegalArgumentException("Movies list is null");
        cache.removeFromCache(CACHE_KEY + name);
        genreRepository.save(genre);
    }
    public void addMovieToGenre(String genreName, String movieName) {
        Genre genre = genreRepository.findByName(genreName);
        Movie movie = movieService.getMovieByTitle(movieName);
        genre.getMovies().add(movie);
        cache.removeFromCache(CACHE_KEY + genreName);
        genreRepository.save(genre);
    }
}
