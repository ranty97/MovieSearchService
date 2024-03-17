package org.example.moviesearchservice.service;

import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Transactional
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovieByTitle(String title) {
        movieRepository.deleteByTitle(title);
    }

    public void updateMovieByTitle(String title, String premiere, String language, int runtime, double imdbScore) {
        Movie movie = getMovieByTitle(title);
        movie.setPremiere(premiere);
        movie.setLanguage(language);
        movie.setRuntime(runtime);
        movie.setImdbScore(imdbScore);
        movieRepository.save(movie);
    }

    public Movie getMovieById(Long movieId) {
        return movieRepository.findMovieById(movieId);
    }
}
