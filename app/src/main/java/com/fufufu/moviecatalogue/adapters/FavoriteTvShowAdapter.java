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
import com.fufufu.moviecatalogue.databinding.FavoriteTvShowListItemBinding;
import com.fufufu.moviecatalogue.models.FavoriteTvShow;
import com.fufufu.moviecatalogue.viewmodels.FavoriteTvShowViewModel;
import com.fufufu.moviecatalogue.views.DetailTvShowActivity;
import com.fufufu.moviecatalogue.views.FavoriteTvShowFragment;
import com.fufufu.moviecatalogue.widgets.FavoriteTvShowWidget;

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
    public void onBindViewHolder(@NonNull TvShowHolder holder, final int position) {
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
                favoriteTvShowViewModel.deleteFavoriteTvShow(favoriteTvShow.getId());
                favoriteTvShows.remove(position);
                notifyDataSetChanged();
                Toast.makeText(view.getContext(), view.getContext().getResources().getString(R.string.toast_removed), Toast.LENGTH_LONG).show();

                Context context = view.getContext().getApplicationContext();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName componentName = new ComponentName(context, FavoriteTvShowWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.favorite_tv_show_widget_stack_view);
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
