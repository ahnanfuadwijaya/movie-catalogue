package com.fufufu.moviecataloguemvvm.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.FavoriteFilmListItemBinding;
import com.fufufu.moviecataloguemvvm.databinding.FilmListItemBinding;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.views.DetailFilmActivity;

import java.util.ArrayList;

public class FavoriteFilmAdapter extends RecyclerView.Adapter<FavoriteFilmAdapter.FilmHolder>{
    private ArrayList<Film> films;

    @NonNull
    @Override
    public FavoriteFilmAdapter.FilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavoriteFilmListItemBinding favoriteFilmListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.favorite_film_list_item, parent, false);
        return new FilmHolder(favoriteFilmListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFilmAdapter.FilmHolder holder, int position) {
        final Film film = films.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveWithDataIntent = new Intent(view.getContext(), DetailFilmActivity.class);
                moveWithDataIntent.putExtra("film", film);
                view.getContext().startActivity(moveWithDataIntent);
            }
        });
        holder.favoriteFilmListItemBinding.setFilm(film);
    }

    @Override
    public int getItemCount() {
        if (films != null) {
            return films.size();
        } else {
            return 0;
        }
    }
    public void setFilms(ArrayList<Film> films) {
        this.films = films;
        notifyDataSetChanged();
    }

    class FilmHolder extends RecyclerView.ViewHolder {
        private FavoriteFilmListItemBinding favoriteFilmListItemBinding;

        FilmHolder(@NonNull FavoriteFilmListItemBinding favoriteFilmListItemBinding) {
            super(favoriteFilmListItemBinding.getRoot());
            this.favoriteFilmListItemBinding = favoriteFilmListItemBinding;
        }
    }
}
