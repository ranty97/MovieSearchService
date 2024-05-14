package org.example.moviesearchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.moviesearchservice.component.Cache;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        if (oldMovie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }

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
        List<Movie> movies = (List<Movie>) cache.get(cacheKey);
        if (movies != null) {
            log.info(CACHE_LOG + cacheKey);
            return movies;
        }
        movies = movieRepository.findMoviesWithGenresAndReviews(genreName);
        cache.put(cacheKey, movies);
        return movies;
    }

    public List<Long> createMovies(List<Movie> movies) {
        Logger logger = LoggerFactory.getLogger(MovieService.class);

        Map<Object, List<Movie>> groupedMovies = movies.stream()
                .collect(Collectors.groupingBy(movie -> Arrays.asList(movie.getTitle(), movie.getPremiere())));

        List<Movie> duplicates = groupedMovies.values().stream()
                .filter(group -> group.size() > 1)
                .flatMap(List::stream)
                .toList();

        if (!duplicates.isEmpty()) {
            logger.warn("Duplicate movies found {}", duplicates);
        }

        List<Movie> uniqueMovies = groupedMovies.values().stream()
                .map(group -> group.get(0))
                .toList();

        movieRepository.saveAll(uniqueMovies);

        return uniqueMovies.stream().map(Movie::getId).toList();
    }

    public Long updateMovie(Long id, Movie movie) {
        Movie oldMovie = getMovieById(id);
        if (oldMovie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }

        oldMovie.setTitle(movie.getTitle());
        oldMovie.setLanguage(movie.getLanguage());
        oldMovie.setPremiere(movie.getPremiere());
        oldMovie.setRuntime(movie.getRuntime());
        oldMovie.setImdbScore(movie.getImdbScore());
        
        return movieRepository.save(oldMovie).getId();
    }
}
