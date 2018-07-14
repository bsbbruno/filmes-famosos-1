package com.brittus.filmesfamosos;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brittus.filmesfamosos.model.Movie;
import com.brittus.filmesfamosos.utils.AsyncTaskDelegate;
import com.brittus.filmesfamosos.utils.MovieService;
import com.brittus.filmesfamosos.utils.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, AsyncTaskDelegate {

    private static final String POPULAR_MOVIES = "/movie/popular";
    private static final String TOP_MOVIES = "/movie/top_rated";

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movie);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        int numberOfColumns = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        loadMovieData(TOP_MOVIES);

    }

    @Override
    public void onClick(Movie movie) {
        Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movie);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.top_rated :
                loadMovieData(TOP_MOVIES);
                return true;
            case R.id.popular :
                loadMovieData(POPULAR_MOVIES);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    private void loadMovieData(String sortBy) {

        if (NetworkUtils.isNetworkConnected(MainActivity.this)) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            showMovieDataView();
            new MovieService(MainActivity.this).execute(sortBy);
        } else {
            View view = getWindow().getDecorView().getRootView();
            Snackbar snackbar = Snackbar.make(view , getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.retry), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadMovieData(TOP_MOVIES);
                }
            });
            snackbar.show();
        }

    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    @Override
    public void processFinish(Object movies) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        if (movies != null) {
            showMovieDataView();
            mMovieAdapter.setmMovies((List<Movie>) movies);
        } else {
            showErrorMessage();
        }
    }
}
