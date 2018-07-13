package com.brittus.filmesfamosos.utils;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String THE_MOVIE_BASE_URL = "http://api.themoviedb.org/3";
    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY = "";
    private static final String LANGUAGE_PARAM = "language";
    private static final String LANGUAGE_DEFAULT = "pt-BR";

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    private static final String THUMBNAIL_IMAGE_SIZE = "/w185";
    private static final String DISPLAY_IMAGE_SIZE = "/w342";

    /**
     * Constroi URL a partir de uma rota.
     * @param route rota
     * @return URL
     */
    public static URL buildUrl(String route) {

        Uri builtUri = Uri.parse(THE_MOVIE_BASE_URL + route).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE_DEFAULT)
                .build();

        return getUrl(builtUri);

    }

    public static String buildThumbnailImageUrl(String image) {
        return IMAGE_BASE_URL + THUMBNAIL_IMAGE_SIZE + image;
    }

    public static String buildDisplayImageUrl(String image) {
        return IMAGE_BASE_URL + DISPLAY_IMAGE_SIZE + image;
    }

    @Nullable
    private static URL getUrl(Uri builtUri) {
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
