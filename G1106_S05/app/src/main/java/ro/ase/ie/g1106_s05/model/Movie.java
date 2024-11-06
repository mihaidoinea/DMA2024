package ro.ase.ie.g1106_s05.model;

import java.util.Date;

public class Movie {
    private String title;
    private Date release;
    private Integer duration;
    private Boolean recommended;
    private GenreEnum genre;
    private ParentalApprovalEnum approval;
    private String posterUrl;
    private Double budget;
    private Float rating;

    public Movie(String title, Date release, Double duration, Boolean recommended, GenreEnum genre, ParentalApprovalEnum approval, String posterUrl, Double budget, Float rating) {
        this.title = title;
        this.release = release;
        this.duration = duration;
        this.recommended = recommended;
        this.genre = genre;
        this.approval = approval;
        this.posterUrl = posterUrl;
        this.budget = budget;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", release=" + release +
                ", duration=" + duration +
                ", recommended=" + recommended +
                ", genre=" + genre +
                ", approval=" + approval +
                ", posterUrl='" + posterUrl + '\'' +
                ", budget=" + budget +
                ", rating=" + rating +
                '}';
    }
}
