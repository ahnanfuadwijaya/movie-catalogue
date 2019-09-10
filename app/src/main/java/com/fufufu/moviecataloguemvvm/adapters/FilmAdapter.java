package com.fufufu.moviecataloguemvvm.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.FilmListItemBinding;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.views.DetailFilmActivity;
import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {
    private ArrayList<Film> films;

    @NonNull
    @Override
    public FilmAdapter.FilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilmListItemBinding filmListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.film_list_item, parent, false);
        return new FilmHolder(filmListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmHolder holder, int position) {
        final Film film = films.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveWithDataIntent = new Intent(view.getContext(), DetailFilmActivity.class);
                moveWithDataIntent.putExtra("filmId", film.getId());
                view.getContext().startActivity(moveWithDataIntent);
            }
        });
        holder.filmListItemBinding.setFilm(film);
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
        private FilmListItemBinding filmListItemBinding;

        FilmHolder(@NonNull FilmListItemBinding filmListItemBinding) {
            super(filmListItemBinding.getRoot());
            this.filmListItemBinding = filmListItemBinding;
        }
    }
}
