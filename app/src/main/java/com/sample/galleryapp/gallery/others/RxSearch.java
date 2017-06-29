package com.sample.galleryapp.gallery.others;

import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class RxSearch {

    private static final String EMPTY_QUERY = "";

    public static Observable<String> fromSearchView(@NonNull final SearchView searchView) {
        final BehaviorSubject<String> subject = BehaviorSubject.create(EMPTY_QUERY);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                subject.onCompleted();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    subject.onNext(newText);
                }
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            subject.onNext(EMPTY_QUERY);
            return false;
        });

        return subject;
    }
}