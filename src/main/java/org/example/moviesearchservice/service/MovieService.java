package org.example.moviesearchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.moviesearchservice.component.Cache;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;
    private final Cache cache;
    private static final String CACHE_LOG = "Data loaded from cache using key: ";

    @Autowired
    public MovieService(MovieRepository movieRepository, Cache cache) {
        this.movieRepository = movieRepository;
        this.cache = cache;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }
    public Long createMovie(Movie movie) {
        return movieRepository.save(movie).getId();
    }

    public void deleteMovieByTitle(String title) {
        movieRepository.deleteByTitle(title);
    }

    public void updateMovieByTitle(String title, String premiere, String language, int runtime, double imdbScore) {
        Movie oldMovie = getMovieByTitle(title);

        if (premiere != null) oldMovie.setPremiere(premiere);
        if (language != null) oldMovie.setLanguage(language);
        if (runtime != 0) oldMovie.setRuntime(runtime);
        if (imdbScore != 0) oldMovie.setImdbScore(imdbScore);

        movieRepository.save(oldMovie);
    }

    public Movie getMovieById(Long movieId) {
        return movieRepository.findMovieById(movieId);
    }

    public List<Movie> getAllSterlingMovies(String genreName) {
        String cacheKey = "genre-" + genreName;
        List<Movie> movies = (List<Movie>) cache.getFromCache(cacheKey);
        if(movies != null) {
            log.info(CACHE_LOG + cacheKey);
            return movies;
        }
        movies = movieRepository.findMoviesWithGenresAndReviews(genreName);
        cache.addToCache(cacheKey, movies);
        return movies;
    }
}
