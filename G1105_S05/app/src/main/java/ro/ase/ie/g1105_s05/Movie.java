package ro.ase.ie.g1105_s05;

import android.content.Intent;

import java.util.Date;

public class Movie {

    private String title;
    private Double budget;
    private Integer duration;
    private Byte rating;
    private Date release;
    private Boolean recommended;
    private Genre genre;
    private Status status;

    public Movie(String title, Double budget, Integer duration,
                 Byte rating, Date release, Boolean recommended,
                 Genre genre, Status status) {
        this.title = title;
        this.budget = budget;
        this.duration = duration;
        this.rating = rating;
        this.release = release;
        this.recommended = recommended;
        this.genre = genre;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", budget=" + budget +
                ", duration=" + duration +
                ", rating=" + rating +
                ", release=" + release +
                ", recommended=" + recommended +
                ", genre=" + genre +
                ", status=" + status +
                '}';
    }
}
