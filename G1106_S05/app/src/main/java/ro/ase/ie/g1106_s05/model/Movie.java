package ro.ase.ie.g1106_s05.model;

import java.util.Date;

public class Movie {
    private String title;
    private Date release;
    private Integer duration;
    private Boolean recommended;
    private GenreEnum genre;
    private StatusEnum status;

    public Movie(String title, Date release, Integer duration,
                 Boolean recommended, GenreEnum genre, StatusEnum status) {
        this.title = title;
        this.release = release;
        this.duration = duration;
        this.recommended = recommended;
        this.genre = genre;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", release=" + release +
                ", duration=" + duration +
                ", recommended=" + recommended +
                ", genre=" + genre +
                ", status=" + status +
                '}';
    }
}
