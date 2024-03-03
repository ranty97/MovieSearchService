package org.example.moviesearchservice.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="reviews", schema = "public")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "comment")
    private String comment;

    public Review(String comment) {
        this.comment = comment;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title")
    private Movie movie;

    public Review() {}
}
