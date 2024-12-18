package ro.ase.ie.g1105_s05.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.Objects;

import ro.ase.ie.g1105_s05.database.DateConverter;

@Entity(tableName = "movie")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Long movieId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "genre")
    private Genre genre;

    @ColumnInfo(name = "budget")
    private Double budget;

    @ColumnInfo
    private Integer duration;

    @ColumnInfo
    private Float rating;
    @ColumnInfo
    private Boolean recommended;

    @ColumnInfo
    private ParentalApprovalEnum status;

    @TypeConverters(DateConverter.class)
    @ColumnInfo
    private Date release;

    @ColumnInfo
    private String posterUrl;


    public Movie()
    {

    }

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

    @Ignore
    protected Movie(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            budget = null;
        } else {
            budget = in.readDouble();
        }
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
        byte tmpRecommended = in.readByte();
        recommended = tmpRecommended == 0 ? null : tmpRecommended == 1;
        posterUrl = in.readString();
        release = new Date(in.readLong());
        genre = Genre.valueOf(in.readString());
        status = ParentalApprovalEnum.valueOf(in.readString());
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        if (budget == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(budget);
        }
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
        dest.writeByte((byte) (recommended == null ? 0 : recommended ? 1 : 2));
        dest.writeString(posterUrl);
        dest.writeLong(release.getTime());
        dest.writeString(genre.toString());
        dest.writeString(status.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public ParentalApprovalEnum getStatus() {
        return status;
    }

    public void setStatus(ParentalApprovalEnum status) {
        this.status = status;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getTitle(), movie.getTitle()) && Objects.equals(getRelease(), movie.getRelease());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getRelease());
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
