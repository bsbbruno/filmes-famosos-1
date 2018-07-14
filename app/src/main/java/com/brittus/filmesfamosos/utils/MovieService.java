package com.brittus.filmesfamosos.utils;

import android.os.AsyncTask;

import com.brittus.filmesfamosos.model.Movie;

import java.net.URL;
import java.util.List;

public class MovieService extends AsyncTask<String, Void, List<Movie>> {

    private AsyncTaskDelegate delegate;

    public MovieService(AsyncTaskDelegate responder){
        this.delegate = responder;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        String route = params[0];
        URL movieRequestUrl = NetworkUtils.buildUrl(route);

        try {
            String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

            return MovieJsonUtils.getMoviesFromJson(jsonMoviesResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if(delegate != null)
            delegate.processFinish(movies);
    }

}
