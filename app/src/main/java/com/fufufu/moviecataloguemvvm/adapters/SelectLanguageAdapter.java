package com.fufufu.moviecataloguemvvm.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivitySetLanguageBinding;
import com.fufufu.moviecataloguemvvm.databinding.LanguageListItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SelectLanguageAdapter extends RecyclerView.Adapter<SelectLanguageAdapter.SelectHolder> {
    List<String> list;

    public SelectLanguageAdapter(String[] list){
        this.list = Arrays.asList(list);
        Log.d("String", Arrays.toString(list));
    }

    @NonNull
    @Override
    public SelectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LanguageListItemBinding languageListItemBinding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.language_list_item, parent,false);
        return new SelectHolder(languageListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectHolder holder, int position) {
        final String item = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), String.valueOf(getItemCount()), Toast.LENGTH_LONG).show();
            }
        });
        holder.languageListItemBinding.tvLanguageItem.setText(item);
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    class SelectHolder extends RecyclerView.ViewHolder {
        LanguageListItemBinding languageListItemBinding;
        SelectHolder(LanguageListItemBinding languageListItemBinding) {
            super(languageListItemBinding.getRoot());
            this.languageListItemBinding = languageListItemBinding;
        }
    }
}
