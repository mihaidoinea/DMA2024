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

    public Movie() {
    }

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
    }

    public void setParentalApproval(ParentalApprovalEnum parentalApproval) {
        this.parentalApproval = parentalApproval;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
