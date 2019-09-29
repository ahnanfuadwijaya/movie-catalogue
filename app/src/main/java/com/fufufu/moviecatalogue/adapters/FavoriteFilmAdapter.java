package com.fufufu.moviecatalogue.adapters;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.databinding.FavoriteFilmListItemBinding;
import com.fufufu.moviecatalogue.models.FavoriteFilm;
import com.fufufu.moviecatalogue.viewmodels.FavoriteFilmViewModel;
import com.fufufu.moviecatalogue.views.DetailFilmActivity;
import com.fufufu.moviecatalogue.views.FavoriteFilmFragment;
import com.fufufu.moviecatalogue.widgets.FavoriteFilmWidget;

import java.util.List;

public class FavoriteFilmAdapter extends RecyclerView.Adapter<FavoriteFilmAdapter.FilmHolder> {
    private List<FavoriteFilm> favoriteFilms;
    private FavoriteFilmViewModel favoriteFilmViewModel;

    public FavoriteFilmAdapter(FavoriteFilmFragment favoriteFilmFragment) {
        favoriteFilmViewModel = ViewModelProviders.of(favoriteFilmFragment).get(FavoriteFilmViewModel.class);
    }

    @NonNull
    @Override
    public FavoriteFilmAdapter.FilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavoriteFilmListItemBinding favoriteFilmListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.favorite_film_list_item, parent, false);
        return new FilmHolder(favoriteFilmListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteFilmAdapter.FilmHolder holder, final int position) {
        final FavoriteFilm favoriteFilm = favoriteFilms.get(position);
        holder.favoriteFilmListItemBinding.setFavoriteFilm(favoriteFilm);
        holder.favoriteFilmListItemBinding.filmCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailFilmActivity.class);
                intent.putExtra("filmId", favoriteFilm.getId());
                view.getContext().startActivity(intent);
            }
        });
        holder.favoriteFilmListItemBinding.removeFromFavoriteFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteFilmViewModel.deleteFavoriteFilm(favoriteFilm.getId());
                favoriteFilms.remove(position);
                notifyDataSetChanged();
                Toast.makeText(view.getContext(), view.getContext().getResources().getString(R.string.toast_removed), Toast.LENGTH_LONG).show();

                Context context = view.getContext().getApplicationContext();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName componentName = new ComponentName(context, FavoriteFilmWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.favorite_film_widget_stack_view);
            }
        });
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
