package com.moviesearceservice.api.controller;

import org.example.moviesearchservice.controller.GenreController;
import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class GenreControllerTest {

    @InjectMocks
    private GenreController genreController;

    @Mock
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllGenres() {
        // Arrange
        List<Genre> mockGenres = Arrays.asList(new Genre(1L,"Action", null), new Genre(2L,"Comedy", null));
        when(genreService.getAllGenres()).thenReturn(mockGenres);

        // Act
        List<Genre> result = genreController.getAllGenres();

        // Assert
        assertEquals(mockGenres, result);
    }

    @Test
    void testGetGenreById_ValidId_ReturnsGenre() {
        Long id = 1L;
        Genre expectedGenre = new Genre(id, "Action", null);

        when(genreService.getGenreById(id)).thenReturn(expectedGenre);

        Genre actualGenre = genreController.getGenreById(id);

        assertEquals(expectedGenre, actualGenre);
    }

    @Test
    void testCreateGenreStatus() {
        String name = "Action";
        when(genreService.createGenre(any(), any())).thenReturn(1L);

        try {
            genreController.createGenre(name, Collections.emptyList());
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.CREATED, ex.getStatusCode());
        }
    }

    @Test
    void testUpdateGenreStatus() {
        Long id = 1L;
        String newName = "New Name";
        Genre genre = new Genre(id, "Old Name", null);
        when(genreService.updateGenre(any(Long.class), any(String.class), any(List.class))).thenReturn(genre.getId());

        try {
            genreController.updateGenre(null, id, newName);
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.OK, ex.getStatusCode());
        }
    }

    @Test
    void testAddMovieToGenreStatus() {
        Long genreId = 1L;
        Long movieId = 2L;
        doNothing().when(genreService).addMovieToGenre(anyLong(), anyLong());

        try {
            genreController.addMovieToGenre(genreId, movieId);
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.OK, ex.getStatusCode());
        }
    }

    @Test
    void testDeleteGenreStatus() {
        Long id = 1L;
        doNothing().when(genreService).deleteGenreById(anyLong());

        try {
            Long result = genreController.deleteGenre(id);
            assertEquals(id, result);
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.OK, ex.getStatusCode());
        }
    }
}