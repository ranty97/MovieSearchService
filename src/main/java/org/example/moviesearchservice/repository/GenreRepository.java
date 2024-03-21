package org.example.moviesearchservice.repository;

import org.example.moviesearchservice.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findGenreById(Long id);

}
