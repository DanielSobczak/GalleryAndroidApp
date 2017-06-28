package com.sample.galleryapp.common;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.galleryapp.common.di.HasComponent;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    /**
     * Inject the dependencies
     */
    protected void injectDependencies() {

    }
}