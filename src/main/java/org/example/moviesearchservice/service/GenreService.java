package org.example.moviesearchservice.service;

import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.repository.GenreRepository;
import org.example.moviesearchservice.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieService movieService;

    private final MovieRepository movieRepository;

    public GenreService(GenreRepository genreRepository, MovieService movieService, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    @Transactional
    public void createGenre(String name, List<Long> moviesIds) {
        Genre genre = new Genre();
        genre.setName(name);
        if (moviesIds != null) {
            for (Long movieId : moviesIds) {
                Movie movie = movieService.getMovieById(movieId);
                genre.getMovies().add(movie);
            }
        } else throw new IllegalArgumentException("Movies list cannot be null");
        genreRepository.save(genre);
    }

    public void deleteGenreByName(String name) {
        genreRepository.deleteByName(name);
    }

    public void updateGenre(Long id, String name, List<Long> moviesIds) {
        Genre genre = genreRepository.findGenreById(id);
        genre.setName(name);
        if (moviesIds != null) {
            List<Movie> movieList = new ArrayList<>();
            for (Long movieId : moviesIds) {
                genre.getMovies().add(movieService.getMovieById(movieId));
            }
        } else throw new IllegalArgumentException("Movies list cannot be null");
        genreRepository.save(genre);
    }

    @Transactional
    public void addMovieToGenre(String genreName, String movieName) {
        Genre genre = genreRepository.findByName(genreName);
        Movie movie = movieService.getMovieByTitle(movieName);
        genre.getMovies().add(movie);;
        genreRepository.save(genre);
    }
}
