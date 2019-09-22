package com.fufufu.moviecatalogue.viewmodels;

import androidx.lifecycle.ViewModel;
import com.fufufu.moviecatalogue.models.About;

public class AboutViewModel extends ViewModel {
    private About about = new About();

    public AboutViewModel(){
    }

    public void setData(){
        about.setName("Ahnan Fuadwijaya");
        about.setEmail("ahnanfuadwijay@gmail.com");
        about.setPhoto("profile_photo");
    }
    public About getData(){
        return about;
    }
}
