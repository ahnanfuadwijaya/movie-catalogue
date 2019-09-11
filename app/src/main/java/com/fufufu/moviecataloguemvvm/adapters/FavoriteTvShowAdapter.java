package com.fufufu.moviecataloguemvvm.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.FavoriteTvShowListItemBinding;
import com.fufufu.moviecataloguemvvm.models.FavoriteTvShow;
import com.fufufu.moviecataloguemvvm.viewmodels.FavoriteTvShowViewModel;
import com.fufufu.moviecataloguemvvm.views.DetailTvShowActivity;
import com.fufufu.moviecataloguemvvm.views.FavoriteTvShowFragment;
import java.util.List;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.TvShowHolder> {
    private List<FavoriteTvShow> favoriteTvShows;
    private FavoriteTvShowViewModel favoriteTvShowViewModel;

    public FavoriteTvShowAdapter(FavoriteTvShowFragment favoriteTvShowFragment) {
        favoriteTvShowViewModel = ViewModelProviders.of(favoriteTvShowFragment).get(FavoriteTvShowViewModel.class);
    }

    @NonNull
    @Override
    public TvShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavoriteTvShowListItemBinding favoriteTvShowListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.favorite_tv_show_list_item, parent, false);
        return new FavoriteTvShowAdapter.TvShowHolder(favoriteTvShowListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowHolder holder, int position) {
        final FavoriteTvShow favoriteTvShow = favoriteTvShows.get(position);
        holder.favoriteTvShowListItemBinding.setFavoriteTvShow(favoriteTvShow);
        holder.favoriteTvShowListItemBinding.tvShowCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailTvShowActivity.class);
                intent.putExtra("tvShowId", favoriteTvShow.getId());
                view.getContext().startActivity(intent);
            }
        });
        holder.favoriteTvShowListItemBinding.removeFromFavoriteTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteTvShowViewModel.deleteFavoriteTvShow(favoriteTvShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favoriteTvShows != null)
            return favoriteTvShows.size();
        else {
            return 0;
        }
    }

    public void setFavoriteTvShows(List<FavoriteTvShow> favoriteTvShows) {
        this.favoriteTvShows = favoriteTvShows;
        notifyDataSetChanged();
    }

    class TvShowHolder extends RecyclerView.ViewHolder {
        private FavoriteTvShowListItemBinding favoriteTvShowListItemBinding;

        TvShowHolder(FavoriteTvShowListItemBinding favoriteTvShowListItemBinding) {
            super(favoriteTvShowListItemBinding.getRoot());
            this.favoriteTvShowListItemBinding = favoriteTvShowListItemBinding;
        }
    }
}
