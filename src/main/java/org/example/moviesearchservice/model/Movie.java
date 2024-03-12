package org.example.moviesearchservice.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name= "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

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

    public Movie(String title, String genre, String premiere, String language, int runtime, double imdbScore) {
        this.genre = genre;
        this.title = title;
        this.language = language;
        this.premiere = premiere;
        this.runtime = runtime;
        this.imdbScore = imdbScore;
    }

    public Movie() {}

}