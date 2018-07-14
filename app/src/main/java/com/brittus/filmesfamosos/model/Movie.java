package com.brittus.filmesfamosos.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private final Long id;
    private final String title;
    private final String releaseDate;
    private final String moviePoster;
    private final Double voteAverage;
    private final String overview;

    public Movie(Long id, String title, String releaseDate, String moviePoster, Double voteAverage, String overview) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    private Movie(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.moviePoster = in.readString();
        this.voteAverage = in.readDouble();
        this.overview = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(moviePoster);
        parcel.writeDouble(voteAverage);
        parcel.writeString(overview);

    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
