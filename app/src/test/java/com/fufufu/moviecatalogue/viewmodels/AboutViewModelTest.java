package com.fufufu.moviecatalogue.viewmodels;

import com.fufufu.moviecatalogue.models.About;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.refEq;

public class AboutViewModelTest {
    private AboutViewModel viewModel;
    private About testAbout;

    @Before
    public void setUp() {
        viewModel = new AboutViewModel();
        testAbout = new About();
        testAbout.setName("Ahnan Fuadwijaya");
        testAbout.setEmail("ahnanfuadwijay@gmail.com");
        testAbout.setPhoto("profile_photo");

    }

    @Test
    public void getData() {

        viewModel.setData();
        About about = viewModel.getData();

        assertNotNull(about);

        assertTrue(assertEquals(testAbout, about));
    }

    private boolean assertEquals(About a, About b) {
        if(a.getName().equals(b.getName())){
            if(a.getEmail().equals(b.getEmail())){
                return a.getPhoto().equals(b.getPhoto());
            }
        }
        return false;
    }
}