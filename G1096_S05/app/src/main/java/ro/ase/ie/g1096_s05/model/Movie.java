package ro.ase.ie.g1096_s05.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Movie implements Parcelable {

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
            rating = null;
        } else {
            rating = in.readFloat();
        }
        if (in.readByte() == 0) {
            budget = null;
        } else {
            budget = in.readDouble();
        }
        if (in.readByte() == 0) {
            release = null;
        } else {
            release = new Date(in.readLong());
        }
        genre = GenreEnum.valueOf(in.readString());
        parentalApproval = ParentalApprovalEnum.valueOf(in.readString());
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
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
        if (budget == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(budget);
        }

        if (release == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(release.getTime());
        }

        dest.writeString(genre.toString());
        dest.writeString(parentalApproval.toString());
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
