package org.example.moviesearchservice.repository;

import org.example.moviesearchservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    void deleteByTitle(String title);

    Movie findByTitle(String title);
}
