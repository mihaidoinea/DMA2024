package ro.ase.ie.g1106_s05.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.Objects;

import ro.ase.ie.g1106_s05.database.DateTypeConverter;

@Entity(tableName = "movie")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private Long movieId;

    @ColumnInfo(name = "movieTitle")
    private String title;

    @TypeConverters(DateTypeConverter.class)
    @ColumnInfo
    private Date release;

    @ColumnInfo
    private Integer duration;

    @ColumnInfo
    private Boolean recommended;

    @ColumnInfo
    private GenreEnum genre;

    @ColumnInfo
    private ParentalApprovalEnum approval;

    @ColumnInfo
    private String posterUrl;

    @ColumnInfo
    private Double budget;

    @ColumnInfo
    private Float rating;

    public Movie() {
        Log.d("Movie", "Default constructor!");
    }

    public Movie(String title, Date release, Integer duration, Boolean recommended, GenreEnum genre, ParentalApprovalEnum approval, String posterUrl, Double budget, Float rating) {
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

    protected Movie(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        byte tmpRecommended = in.readByte();
        recommended = tmpRecommended == 0 ? null : tmpRecommended == 1;
        posterUrl = in.readString();
        if (in.readByte() == 0) {
            budget = null;
        } else {
            budget = in.readDouble();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
        if (in.readByte() == 0) {
            release = null;
        } else {
            release = new Date(in.readLong());
        }
        if (in.readByte() == 0) {
            genre = null;
        } else {
            genre = GenreEnum.valueOf(in.readString());
        }
        if (in.readByte() == 0) {
            approval = null;
        } else {
            approval = ParentalApprovalEnum.valueOf(in.readString());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        dest.writeByte((byte) (recommended == null ? 0 : recommended ? 1 : 2));
        dest.writeString(posterUrl);
        if (budget == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(budget);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
        if (release == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(release.getTime());
        }
        if (genre == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(genre.toString());
        }
        if (approval == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(approval.toString());
        }

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

    public void setApproval(ParentalApprovalEnum approval) {
        this.approval = approval;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public Date getRelease() {
        return release;
    }

    public Integer getDuration() {
        return duration;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public ParentalApprovalEnum getApproval() {
        return approval;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public Double getBudget() {
        return budget;
    }

    public Float getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getTitle(), movie.getTitle()) && Objects.equals(getRelease(), movie.getRelease());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getRelease());
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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
