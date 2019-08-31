package com.fufufu.moviecataloguemvvm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivityDetailFilmBinding;
import com.fufufu.moviecataloguemvvm.models.Film;

public class DetailFilmActivity extends AppCompatActivity {

    TextView tvJudul, tvSinopsis, tvGenre;
    ImageView imgPoster;
    String judul, genre, sinopsis, poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailFilmBinding activityDetailFilmBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_film);

        Film film = getIntent().getParcelableExtra("film");
        activityDetailFilmBinding.setFilm(film);

        String title = film.getTitle();
        Log.d("DetailFilmActivity", "Title-"+title);

        //setContentView(R.layout.activity_detail_film);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
