package org.example.moviesearchservice.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonBackReference
    private Movie movie;

    public Review(String comment) {
        this.text = comment;
    }

    public Review() {}

}
