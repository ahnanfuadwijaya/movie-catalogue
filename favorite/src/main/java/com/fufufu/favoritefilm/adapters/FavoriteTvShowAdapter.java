package com.fufufu.favoritefilm.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.fufufu.favoritefilm.R;
import com.fufufu.favoritefilm.databinding.FavoriteFilmListItemBinding;
import com.fufufu.favoritefilm.databinding.FavoriteTvShowListItemBinding;
import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.models.FavoriteTvShow;
import com.fufufu.favoritefilm.viewmodels.FavoriteFilmViewModel;
import com.fufufu.favoritefilm.viewmodels.FavoriteTvShowViewModel;
import com.fufufu.favoritefilm.views.DetailFilmActivity;
import com.fufufu.favoritefilm.views.FavoriteTvShowFragment;

import java.util.List;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.TvShowHolder> {
    private List<FavoriteTvShow> favoriteTvShows;
    private FavoriteTvShowViewModel favoriteTvShowViewModel;

    public FavoriteTvShowAdapter(FavoriteTvShowFragment favoriteTvShowFragment){
        favoriteTvShowViewModel = ViewModelProviders.of(favoriteTvShowFragment).get(FavoriteTvShowViewModel.class);
    }

    @NonNull
    @Override
    public FavoriteTvShowAdapter.TvShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavoriteTvShowListItemBinding favoriteTvShowListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.favorite_tv_show_list_item, parent, false);
        return new FavoriteTvShowAdapter.TvShowHolder(favoriteTvShowListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvShowAdapter.TvShowHolder holder, final int position) {
        final FavoriteTvShow favoriteTvShow = favoriteTvShows.get(position);
        holder.favoriteTvShowListItemBinding.setFavoriteTvShow(favoriteTvShow);
        holder.favoriteTvShowListItemBinding.tvShowCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(view.getContext(), DetailTvShowActivity.class);
                intent.putExtra("tvShowId", favoriteTvShow.getId());
                view.getContext().startActivity(intent);*/
            }
        });
        holder.favoriteTvShowListItemBinding.removeFromFavoriteTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteTvShowViewModel.deleteFavoriteTvShow(favoriteTvShow.getId());
                favoriteTvShows.remove(position);
                notifyDataSetChanged();
                Toast.makeText(view.getContext(), "Removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteTvShows.size();
    }

    public void setFavoriteTvShows(List<FavoriteTvShow> favoriteTvShows) {
        this.favoriteTvShows = favoriteTvShows;
        notifyDataSetChanged();
    }

    public class TvShowHolder extends RecyclerView.ViewHolder {
        private FavoriteTvShowListItemBinding favoriteTvShowListItemBinding;
        public TvShowHolder(@NonNull FavoriteTvShowListItemBinding favoriteTvShowListItemBinding) {
            super(favoriteTvShowListItemBinding.getRoot());
            this.favoriteTvShowListItemBinding = favoriteTvShowListItemBinding;
        }
    }
}
