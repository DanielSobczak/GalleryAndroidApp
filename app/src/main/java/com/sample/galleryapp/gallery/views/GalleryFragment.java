package com.sample.galleryapp.gallery.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sample.galleryapp.R;
import com.sample.galleryapp.common.BaseFragment;
import com.sample.galleryapp.common.exceptions.ErrorBundle;
import com.sample.galleryapp.gallery.GalleryComponent;
import com.sample.galleryapp.gallery.GalleryPresenter;
import com.sample.galleryapp.gallery.models.GalleryCellImage;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class GalleryFragment extends BaseFragment implements GalleryView, ErrorView.OnErrorBtnClickedListener {

    @Inject
    GalleryPresenter galleryPresenter;

    @BindView(R.id.list_of_images)
    RecyclerView recyclerView;
    @BindView(R.id.view_gallery_error)
    ErrorView errorView;
    @BindView(R.id.cpb_gallery_loading)
    CircularProgressBar loader;
    private GalleryImagesAdapter imagesAdapter;

    public static Fragment create() {
        return new GalleryFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_gallery;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getComponent(GalleryComponent.class).inject(this);
        galleryPresenter.setView(this);
        galleryPresenter.initialise();
        imagesAdapter = new GalleryImagesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(imagesAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        galleryPresenter.destroy();
    }

    @Override
    public void renderGalleryImages(final List<GalleryCellImage> galleryImages) {
        imagesAdapter.setImages(galleryImages);
        imagesAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loader.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showError(final ErrorBundle errorBundle) {
        errorView.setError(R.string.gallery_general_error_message, R.string.gallery_error_btn);
        errorView.setVisibility(View.VISIBLE);
        errorView.setOnBtnClickedListener(this);
        recyclerView.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onErrorBtnClicked() {
        galleryPresenter.onRetryClicked();
    }
}
