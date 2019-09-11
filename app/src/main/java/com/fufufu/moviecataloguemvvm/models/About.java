package com.fufufu.moviecataloguemvvm.models;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fufufu.moviecataloguemvvm.R;

public class About {
    private String name, email, photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @BindingAdapter({"avatar"})
    public static void loadImage(ImageView imageView, String imageURL) {
        int resourceId = imageView.getContext().getResources().getIdentifier(imageURL, "drawable", imageView.getContext().getPackageName());
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions().circleCrop())
                .load(resourceId)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}
