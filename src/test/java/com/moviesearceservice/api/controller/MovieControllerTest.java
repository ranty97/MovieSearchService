package com.moviesearceservice.api.controller;

import org.example.moviesearchservice.controller.MovieController;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.model.Review;
import org.example.moviesearchservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class MovieControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllMovies_ReturnsListWithAtLeastOneMovie() {
        // Arrange
        List<Movie> mockMovies = Arrays.asList(new Movie(1L, "Movie 1", "2022-01-01", "English", 120, 8.5, null, null), new Movie(2L, "Movie 2", "2022-01-01", "English", 120, 8.5, null, null));
        when(movieService.getAllMovies()).thenReturn(mockMovies);

        // Act
        List<Movie> result = movieController.getAllMovies();

        // Assert
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetAllWithReviewMovies() {
        // Setup
        String genre = "Action";
        List<Movie> expectedMovies = Arrays.asList(Movie.builder().reviews(Arrays.asList(Review.builder().text("Great movie!").build(), Review.builder().text("Cool").build())).build());
        when(movieService.getAllWithReviewMovies(genre)).thenReturn(expectedMovies);

        // Execute
        List<Movie> actualMovies = movieController.getAllWithReviewMovies(genre);

        // Verify
        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    void testGetMovieByValidId() {
        Long validId = 1L;
        Movie expectedMovie = new Movie(validId, "Movie Title", "2022-01-01", "English", 120, 8.5, null, null);

        when(movieService.getMovieById(validId)).thenReturn(expectedMovie);

        Movie actualMovie = movieController.getMovieByTitle(validId);

        assertEquals(expectedMovie, actualMovie);
    }

    @Test
    void testCreateMovieStatus() {
        String title = "Test Movie";
        String premiere = "2024-04-04";
        String language = "English";
        int runtime = 120;
        double imdbScore = 8.0;

        when(movieService.createMovie(any(Movie.class))).thenReturn(1L);

        try {
            movieController.createMovie(title,  premiere, language, runtime, imdbScore);
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.CREATED, ex.getStatusCode());
        }
    }

    @Test
    void testUpdateMovieStatus() {
        Long movieId = 1L;
        String title = "Updated Title";
        String premiere = "2024-04-05";
        String language = "Spanish";
        int runtime = 130;
        double imdbScore = 8.5;

        when(movieService.updateMovieById(any(Long.class), any(String.class), any(String.class),
                any(String.class), any(Integer.class), any(Double.class)))
                .thenReturn(movieId);

        try {
            movieController.updateMovie(movieId, title, premiere, language, runtime, imdbScore);
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.CREATED, ex.getStatusCode());
        }
    }

    @Test
    void testDeleteMovieStatus() {
        Long id = 1L;
        doNothing().when(movieService).deleteMovieById(anyLong());

        try {
            Long result = movieController.deleteMovie(id);
            assertEquals(id, result);
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.OK, ex.getStatusCode());
        }
    }

    @Test
    void testCreateSeveralMovies() {
        List<Movie> movies = Arrays.asList(new Movie(), new Movie());

        when(movieService.createMovies(movies)).thenReturn(Arrays.asList(1L, 2L));

        List<Long> result = movieController.createSeveralMovies(movies);

        assertEquals(Arrays.asList(1L, 2L), result);
    }
}
