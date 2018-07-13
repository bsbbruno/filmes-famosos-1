package com.brittus.filmesfamosos.utils;

import com.brittus.filmesfamosos.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class MovieJsonUtils {

    public static List<Movie> getMoviesFromJson(String moviesJsonStr) throws JSONException {

        final String RESULTS = "results";
        final String ID = "id";
        final String TITLE = "title";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";
        final String VOTE_AVERAGE = "vote_average";
        final String OVERVIEW = "overview";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        JSONArray resultsMovies = moviesJson.getJSONArray(RESULTS);

        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < resultsMovies.length(); i++) {


            JSONObject movieObj = resultsMovies.getJSONObject(i);

            Movie movie = new Movie(movieObj.getLong(ID),movieObj.getString(TITLE),movieObj.getString(RELEASE_DATE),
                    movieObj.getString(POSTER_PATH), movieObj.getDouble(VOTE_AVERAGE), movieObj.getString(OVERVIEW));

            movies.add(movie);

        }

        return movies;

    }

}
