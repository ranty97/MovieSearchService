package org.example.moviesearchservice.repository;

import org.example.moviesearchservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT DISTINCT m FROM Movie m "
            +
            "JOIN m.genres g "
            +
            "JOIN m.reviews r "
            +
            "WHERE g.name = :genreName "
            +
            "GROUP BY m "
            +
            "HAVING COUNT(DISTINCT r) > 0")
    List<Movie> findMoviesWithGenresAndReviews(@Param("genreName") String genreName);
}

