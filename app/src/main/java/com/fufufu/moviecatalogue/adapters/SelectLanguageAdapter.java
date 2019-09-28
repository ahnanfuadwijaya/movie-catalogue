package com.fufufu.moviecatalogue.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.databinding.LanguageListItemBinding;
import com.fufufu.moviecatalogue.views.MainActivity;
import java.util.Arrays;
import java.util.List;

public class SelectLanguageAdapter extends RecyclerView.Adapter<SelectLanguageAdapter.SelectHolder> {
    private List<String> list;

    public SelectLanguageAdapter(String[] list){
        this.list = Arrays.asList(list);
    }

    @NonNull
    @Override
    public SelectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LanguageListItemBinding languageListItemBinding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.language_list_item, parent,false);
        return new SelectHolder(languageListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectHolder holder, final int position) {
        final String item = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveWithDataIntent = new Intent(view.getContext(), MainActivity.class);
                String langId;
                if(item.equalsIgnoreCase("English")){
                    langId = "en";
                }
                else {
                    langId = "in";
                }
                moveWithDataIntent.putExtra("langId", langId);
                moveWithDataIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                view.getContext().startActivity(moveWithDataIntent);
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
