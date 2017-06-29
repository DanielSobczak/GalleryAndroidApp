package com.sample.galleryapp.gallery.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.sample.galleryapp.R;
import com.sample.galleryapp.common.BaseFragment;
import com.sample.galleryapp.common.exceptions.ErrorBundle;
import com.sample.galleryapp.gallery.GalleryComponent;
import com.sample.galleryapp.gallery.GalleryPresenter;
import com.sample.galleryapp.gallery.models.GalleryCellImage;
import com.sample.galleryapp.gallery.others.GalleryImagesAdapter;
import com.sample.galleryapp.gallery.others.RxSearch;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.Subscriptions;

public class GalleryFragment extends BaseFragment implements GalleryView, ErrorView.OnErrorBtnClickedListener {

    private static final int QUERY_DEBOUNCE_MILLIS = 400;
    @Inject
    GalleryPresenter galleryPresenter;

    @BindView(R.id.list_of_images)
    RecyclerView recyclerView;
    @BindView(R.id.view_gallery_error)
    ErrorView errorView;
    @BindView(R.id.cpb_gallery_loading)
    CircularProgressBar loader;
    private GalleryImagesAdapter imagesAdapter;
    private Subscription tagQuerySubsciption = Subscriptions.empty();

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
        setHasOptionsMenu(true);
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
        tagQuerySubsciption.unsubscribe();
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

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_gallery, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        MenuItemCompat.setActionView(item, searchView);
        setupTextChangeObserver(searchView);
    }

    private void setupTextChangeObserver(final SearchView searchView) {
        tagQuerySubsciption = RxSearch.fromSearchView(searchView)
                .debounce(QUERY_DEBOUNCE_MILLIS, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(query -> galleryPresenter.onQueryChanged(query))
                .subscribe();
    }
}
