package com.fufufu.moviecataloguemvvm.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.TvShowListItemBinding;
import com.fufufu.moviecataloguemvvm.models.TvShow;
import com.fufufu.moviecataloguemvvm.views.DetailTvShowActivity;
import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowHolder> {
    private ArrayList<TvShow> tvShows;

    @NonNull
    @Override
    public TvShowAdapter.TvShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TvShowListItemBinding tvShowListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.tv_show_list_item, parent, false);
        return new TvShowHolder(tvShowListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowHolder holder, int position) {
        final TvShow tvShow = tvShows.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveWithDataIntent = new Intent(view.getContext(), DetailTvShowActivity.class);
                moveWithDataIntent.putExtra("tvShowId", tvShow.getId());
                view.getContext().startActivity(moveWithDataIntent);
            }
        });
        holder.tvShowListItemBinding.setTvShow(tvShow);
    }

    @Override
    public int getItemCount() {
        if (tvShows != null) {
            return tvShows.size();
        } else {
            return 0;
        }
    }
    public void setTvShows(ArrayList<TvShow> tvShows) {
        this.tvShows = tvShows;
        notifyDataSetChanged();
    }

    class TvShowHolder extends RecyclerView.ViewHolder {
        TvShowListItemBinding tvShowListItemBinding;
        TvShowHolder(@NonNull TvShowListItemBinding tvShowListItemBinding) {
            super(tvShowListItemBinding.getRoot());
            this.tvShowListItemBinding = tvShowListItemBinding;
        }
    }
}
