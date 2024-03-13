package org.example.moviesearchservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    @JsonBackReference
    private List<Movie> movies;

    public Genre() {}

    public Genre(String name){
        this.name = name;
    }
}
