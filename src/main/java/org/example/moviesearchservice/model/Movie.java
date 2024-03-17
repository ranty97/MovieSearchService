package org.example.moviesearchservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "premiere")
    private String premiere;

    @Column(name = "language")
    private String language;

    @Column(name = "runtime")
    private int runtime;

    @Column(name = "imdb_score")
    private double imdbScore;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;

    @JsonIgnoreProperties("movies")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
    )
    private List<Genre> genres;

    public Movie(String title, String premiere, String language, int runtime, double imdbScore) {
        this.title = title;
        this.language = language;
        this.premiere = premiere;
        this.runtime = runtime;
        this.imdbScore = imdbScore;
    }

    public Movie() {
    }

}