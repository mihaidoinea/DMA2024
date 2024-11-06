package ro.ase.ie.g1096_s05.model;

import java.util.Date;

public class Movie {

    private String title;
    private Date release;
    private Integer duration;
    private Boolean recommended;
    private GenreEnum genre;
    private ParentalApprovalEnum parentalApproval;
    private String posterUrl;
    private Float rating;
    private Double budget;

    public Movie(String title, Date release, Integer duration, Boolean recommended, GenreEnum genre, ParentalApprovalEnum parentalApproval, String posterUrl, Float rating, Double budget) {
        this.title = title;
        this.release = release;
        this.duration = duration;
        this.recommended = recommended;
        this.genre = genre;
        this.parentalApproval = parentalApproval;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", release=" + release +
                ", duration=" + duration +
                ", recommended=" + recommended +
                ", genre=" + genre +
                ", parentalApproval=" + parentalApproval +
                ", posterUrl='" + posterUrl + '\'' +
                ", rating=" + rating +
                ", budget=" + budget +
                '}';
    }
}
