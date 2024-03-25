package org.example.moviesearchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.moviesearchservice.component.Cache;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;
    private final Cache cache;
    private static final String CACHE_LOG = "Data loaded from cache using key: ";

    public MovieService(MovieRepository movieRepository, Cache cache) {
        this.movieRepository = movieRepository;
        this.cache = cache;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Long createMovie(Movie movie) {
        return movieRepository.save(movie).getId();
    }

    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }

    public Long updateMovieById(Long id, String title, String premiere, String language, int runtime, double imdbScore) {
        Movie oldMovie = getMovieById(id);

        if (title != null) {
            oldMovie.setTitle(title);
        }
        if (premiere != null) {
            oldMovie.setPremiere(premiere);
        }
        if (language != null) {
            oldMovie.setLanguage(language);
        }
        if (runtime != 0) {
            oldMovie.setRuntime(runtime);
        }
        if (imdbScore != 0) {
            oldMovie.setImdbScore(imdbScore);
        }

        return movieRepository.save(oldMovie).getId();
    }

    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("ERROR 500 (not found)"));
    }

    public List<Movie> getAllWithReviewMovies(String genreName) {
        String cacheKey = "genre-" + genreName;
        List<Movie> movies = (List<Movie>) cache.getFromCache(cacheKey);
        if (movies != null) {
            log.info(CACHE_LOG + cacheKey);
            return movies;
        }
        movies = movieRepository.findMoviesWithGenresAndReviews(genreName);
        cache.addToCache(cacheKey, movies);
        return movies;
    }
}
