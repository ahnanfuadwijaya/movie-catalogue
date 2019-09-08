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
import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.views.DetailFilmActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFilmAdapter extends RecyclerView.Adapter<FavoriteFilmAdapter.FilmHolder>{
    private List<FavoriteFilm> favoriteFilms;

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
        final FavoriteFilm favoriteFilm = favoriteFilms.get(position);
        holder.favoriteFilmListItemBinding.setFavoriteFilm(favoriteFilm);
    }

    @Override
    public int getItemCount() {
        if (favoriteFilms != null) {
            return favoriteFilms.size();
        } else {
            return 0;
        }
    }
    public void setFavoriteFilms(List<FavoriteFilm> favoriteFilms) {
        this.favoriteFilms = favoriteFilms;
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
