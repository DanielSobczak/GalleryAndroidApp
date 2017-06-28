package com.sample.galleryapp.common;


import android.support.v7.app.AppCompatActivity;

import com.sample.galleryapp.common.di.component.ApplicationComponent;

public class BaseActivity extends AppCompatActivity {

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }


}
