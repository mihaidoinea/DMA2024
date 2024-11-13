package ro.ase.ie.g1106_s05.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

public class Movie implements Parcelable {
    private String title;
    private Date release;
    private Integer duration;
    private Boolean recommended;
    private GenreEnum genre;
    private ParentalApprovalEnum approval;
    private String posterUrl;
    private Double budget;
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
