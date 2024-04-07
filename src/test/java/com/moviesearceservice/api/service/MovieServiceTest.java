package com.moviesearceservice.api.service;

import org.example.moviesearchservice.component.Cache;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.repository.MovieRepository;
import org.example.moviesearchservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieServiceTest {
    @InjectMocks
    private MovieService movieService;

    @Mock
    private Cache cache;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllMovies_ReturnsNonEmptyList() {
        // Arrange
        List<Movie> mockMovies = Arrays.asList(new Movie(), new Movie());
        when(movieRepository.findAll()).thenReturn(mockMovies);

        // Act
        List<Movie> result = movieService.getAllMovies();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testCreateMovie() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");

        when(movieRepository.save(movie)).thenReturn(movie);

        Long id = movieService.createMovie(movie);

        assertEquals(movie.getId(), id);
    }

    @Test
    void testDeleteMovieById_ValidId() {
        Long validId = 1L;
        movieService.deleteMovieById(validId);
        verify(movieRepository).deleteById(validId);
    }

    @Test
    void testUpdateMovieById() {
        // Arrange
        Long id = 1L;
        String title = "New Title";
        String premiere = "2024-01-01";
        String language = "English";
        int runtime = 120;
        double imdbScore = 8.0;

        Movie oldMovie = new Movie(id, "Old Title", "2023-01-01", "Spanish", 100, 7.5, null, null);
        when(movieRepository.findById(id)).thenReturn(Optional.of(oldMovie));

        // Mock the save method to return the object passed to it
        when(movieRepository.save(oldMovie)).thenAnswer(invocation -> invocation.getArgument(0));

        MovieService movieService = new MovieService(movieRepository, cache);

        // Act
        Long result = movieService.updateMovieById(id, title, premiere, language, runtime, imdbScore);

        // Assert
        assertEquals(id, result);
        assertEquals(title, oldMovie.getTitle());
        assertEquals(premiere, oldMovie.getPremiere());
        assertEquals(language, oldMovie.getLanguage());
        assertEquals(runtime, oldMovie.getRuntime());
        assertEquals(imdbScore, oldMovie.getImdbScore());
        verify(movieRepository, times(1)).save(oldMovie);
    }

    @Test
    void testGetMovieById_ValidId() {
        Long validId = 1L;
        Movie expectedMovie = new Movie(validId, "Movie Title", "2022-01-01", "English", 120, 8.5, null, null);

        when(movieRepository.findById(validId)).thenReturn(Optional.of(expectedMovie));

        Movie actualMovie = movieService.getMovieById(validId);

        assertEquals(expectedMovie, actualMovie);
    }

    @Test
    void testGetAllWithReviewMovies_RetrievesMoviesFromCache() {
        // Arrange
        String genreName = "Action";
        List<Movie> cachedMovies = Arrays.asList(new Movie(), new Movie());
        when(cache.get("genre-Action")).thenReturn(cachedMovies);

        // Act
        List<Movie> result = movieService.getAllWithReviewMovies(genreName);

        // Assert
        assertEquals(cachedMovies, result);
    }

    @Test
    void testCreateMovies() {
        List<Movie> moviesWithDuplicates = Arrays.asList(
                new Movie(2L, "Title 1", "Premiere 1", "Language 1", 120, 8.5, null, null),
                new Movie(2L, "Title 1", "Premiere 1", "Language 1", 120, 8.5, null, null),
                new Movie(1L, "Title 2", "Premiere 2", "Language 2", 120, 8.5, null, null)
        );

        List<Long> expectedIds = Arrays.asList(1L, 2L);

        when(movieRepository.saveAll(anyList())).thenReturn(moviesWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList()));

        List<Long> actualIds = movieService.createMovies(moviesWithDuplicates);

        verify(movieRepository, times(1)).saveAll(anyList());

        assertEquals(expectedIds, actualIds);
    }
}

