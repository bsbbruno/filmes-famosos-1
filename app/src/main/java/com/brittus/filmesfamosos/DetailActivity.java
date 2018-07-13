package com.brittus.filmesfamosos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.brittus.filmesfamosos.model.Movie;
import com.brittus.filmesfamosos.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView mTitleMovie = findViewById(R.id.tv_title_movie_detail);
        ImageView mPosterMovie = findViewById(R.id.iv_poster_movie_detail);
        TextView mReleaseDate = findViewById(R.id.tv_release_date_detail);
        TextView mVoteAverage = findViewById(R.id.tv_vote_average_detail);
        TextView mOverview = findViewById(R.id.tv_overview_detail);
        ImageView mPosterDisplayMovie = findViewById(R.id.iv_poster_movie_detail_top);
        ImageView mPosterOverviewMovie = findViewById(R.id.iv_poster_movie_overview);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                Movie mMovie = intentThatStartedThisActivity.getParcelableExtra(Intent.EXTRA_TEXT);
                mTitleMovie.setText(mMovie.getTitle());

                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                try {
                    Date goodData = input.parse(mMovie.getReleaseDate()); // parse input
                    mReleaseDate.setText(output.format(goodData));    // format output
                    Log.v(TAG, output.format(goodData));
                } catch (ParseException e) {
                    Log.v(TAG, e.getMessage());
                }

                mVoteAverage.setText(String.valueOf(mMovie.getVoteAverage()));
                mOverview.setText(mMovie.getOverview());

                String displayImageUrl = NetworkUtils.buildDisplayImageUrl(mMovie.getMoviePoster());
                String thumbnailImageUrl = NetworkUtils.buildThumbnailImageUrl(mMovie.getMoviePoster());

                Picasso.with(this)
                        .load(displayImageUrl)
                        .into(mPosterMovie);

                Picasso.with(this)
                        .load(thumbnailImageUrl)
                        .into(mPosterDisplayMovie);

                Picasso.with(this)
                        .load(displayImageUrl)
                        .into(mPosterOverviewMovie);


            }
        }

    }
}
