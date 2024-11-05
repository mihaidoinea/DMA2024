package ro.ase.ie.g1105_s05;

import java.util.Date;

public class Movie {

    private String title;
    private Double budget;
    private Integer duration;
    private Float rating;
    private Date release;
    private Boolean recommended;
    private Genre genre;
    private ParentalApprovalEnum status;
    String posterUrl;

    public Movie(String title, Double budget, Integer duration,
                 Float rating, Date release, Boolean recommended,
                 Genre genre, ParentalApprovalEnum status, String posterUrl) {
        this.title = title;
        this.budget = budget;
        this.duration = duration;
        this.rating = rating;
        this.release = release;
        this.recommended = recommended;
        this.genre = genre;
        this.status = status;
        this.posterUrl = posterUrl;
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
                ", posterUrl=" + posterUrl +
                '}';
    }
}
