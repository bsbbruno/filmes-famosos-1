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

            Movie movie = new Movie(movieObj.optLong(ID),movieObj.optString(TITLE),movieObj.optString(RELEASE_DATE),
                    movieObj.optString(POSTER_PATH), movieObj.optDouble(VOTE_AVERAGE), movieObj.optString(OVERVIEW));

            movies.add(movie);

        }

        return movies;

    }

}
