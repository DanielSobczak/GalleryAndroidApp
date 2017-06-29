package com.sample.galleryapp.gallery.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sample.galleryapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ErrorView extends LinearLayout {
    @BindView(R.id.view_error_tv)
    TextView text;
    @BindView(R.id.view_error_btn)
    Button button;
    private OnErrorBtnClickedListener onClickListener;

    public ErrorView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ErrorView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ErrorView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.view_error, this);
        ButterKnife.bind(this);
        button.setOnClickListener(__ -> {
            if (onClickListener != null) {
                onClickListener.onErrorBtnClicked();
            }
        });
    }

    public void setError(@StringRes int errorMsg, @StringRes int buttonMsg) {
        text.setText(errorMsg);
        button.setText(buttonMsg);
    }

    public void setOnBtnClickedListener(OnErrorBtnClickedListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public interface OnErrorBtnClickedListener {
        /**
         * On error btn clicked.
         */
        void onErrorBtnClicked();
    }

}
