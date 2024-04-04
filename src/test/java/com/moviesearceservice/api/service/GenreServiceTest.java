package com.moviesearceservice.api.service;

import org.example.moviesearchservice.component.Cache;
import org.example.moviesearchservice.model.Genre;
import org.example.moviesearchservice.model.Movie;
import org.example.moviesearchservice.repository.GenreRepository;
import org.example.moviesearchservice.service.GenreService;
import org.example.moviesearchservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GenreServiceTest {
    @InjectMocks
    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private Cache cache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllGenresNonEmptyList() {
        // Arrange
        List<Genre> mockGenres = Arrays.asList(new Genre(1L, "Action", null), new Genre(2L, "Comedy", null));
        when(genreRepository.findAll()).thenReturn(mockGenres);

        // Act
        List<Genre> result = genreService.getAllGenres();

        // Assert
        assertEquals(mockGenres, result);
    }

    @Test
    void testGetGenreById_ValidId_GenreExists() {
        GenreService genreService = new GenreService(genreRepository, movieService, cache);
        Long validId = 1L;
        Genre expectedGenre = new Genre(validId, "Action", null);

        when(genreRepository.findById(validId)).thenReturn(Optional.of(expectedGenre));

        Genre result = genreService.getGenreById(validId);

        assertEquals(expectedGenre, result);
    }

    @Test
    void testCreateGenreWithNameOnly() {
        String name = "Action";
        Long expectedId = 1L;

        when(genreRepository.save(any())).thenReturn(new Genre(expectedId, name, null));

        Long actualId = genreService.createGenre(name, null);

        assertEquals(expectedId, actualId);
        verify(cache, times(1)).removeNote(anyString());
    }

    @Test
    void testDeleteGenreById() {
        GenreService genreService = new GenreService(genreRepository, movieService, cache);
        Long id = 1L;

        genreService.deleteGenreById(id);

        verify(genreRepository).deleteById(id);
    }

    @Test
    void testUpdateGenre_nameOnly() {
        // Arrange
        Long id = 1L;
        String name = "New Genre Name";
        List<Long> moviesIds = Arrays.asList(1L, 2L);
        GenreRepository genreRepository = mock(GenreRepository.class);
        Genre oldGenre = new Genre(id, "Old Name", null);
        when(genreRepository.findGenreById(id)).thenReturn(oldGenre);
        when(genreRepository.save(any(Genre.class))).thenAnswer(invocation -> {
            Genre updatedGenre = invocation.getArgument(0);
            // Assuming the save operation updates the object in place
            oldGenre.setName(updatedGenre.getName());
            oldGenre.setMovies(updatedGenre.getMovies());
            return oldGenre;
        });

        GenreService genreService = new GenreService(genreRepository, movieService, cache);

        // Act
        Long result = genreService.updateGenre(id, name, moviesIds);

        // Assert
        assertEquals(id, result);
    }

    @Test
    void testAddMovieToGenre_existingGenreAndMovie_shouldAddMovieToGenre() {
        // Arrange
        GenreRepository genreRepository = mock(GenreRepository.class);
        MovieService movieService = mock(MovieService.class);
        Cache cache = mock(Cache.class);
        GenreService genreService = new GenreService(genreRepository, movieService, cache);

        Genre genre = new Genre();
        genre.setId(1L);
        // Предположим, что здесь должно происходить инициализация списка movies
        // genre.setMovies(new ArrayList<>());
        when(genreRepository.findGenreById(1L)).thenReturn(genre);

        Movie movie = new Movie(1L, "Movie Title", "2022-01-01", "English", 120, 8.5, null, null);
        when(movieService.getMovieById(1L)).thenReturn(movie);

        // Act
        genreService.addMovieToGenre(1L, 1L);

        // Assert
        verify(genreRepository, times(1)).save(genre);
    }
}
