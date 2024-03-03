package org.example.moviesearchservice.model;


import jakarta.persistence.*;

@Entity
@Table(name="movies", schema = "public")
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
    @Column(name = "imdb_Score")
    private double imdbScore;


    public Movie(String title, String genre, String premiere, String language, int runtime, double imdbScore) {
        this.genre = genre;
        this.title = title;
        this.language = language;
        this.premiere = premiere;
        this.runtime = runtime;
        this.imdbScore = imdbScore;
    }

    public Movie() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPremiere() {
        return premiere;
    }

    public void setPremiere(String premiere) {
        this.premiere = premiere;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(double imdbScore) {
        this.imdbScore = imdbScore;
    }
}
