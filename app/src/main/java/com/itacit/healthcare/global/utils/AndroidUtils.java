package com.itacit.healthcare.global.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.data.network.interceptors.AuthInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Cache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 26.10.15.
 */
public final class AndroidUtils {

        /**
         * This method converts dp unit to equivalent pixels, depending on device density.
         *
         * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
         * @param context Context to get resources and device specific display metrics
         * @return A float value to represent px equivalent to dp depending on device density
         */
        public static float convertDpToPixel(float dp, Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px = dp * (metrics.densityDpi / 160f);
            return px;
        }

        /**
         * This method converts device specific pixels to density independent pixels.
         *
         * @param px A value in px (pixels) unit. Which we need to convert into db
         * @param context Context to get resources and device specific display metrics
         * @return A float value to represent dp equivalent to px value
         */
        public static float convertPixelsToDp(float px, Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float dp = px / (metrics.densityDpi / 160f);
            return dp;
        }

    public static void preventRootScroll(View view, ScrollView rootSv) {
        view.setOnTouchListener((v, event) -> {
            rootSv.requestDisallowInterceptTouchEvent(true);
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    rootSv.requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });
    }

    public static void toggleListVisibility(RecyclerView recyclerView, ImageView expandIv) {
        if (recyclerView.getVisibility() == View.GONE) {
            expandIv.setImageResource(R.drawable.ic_drop);
            expand(recyclerView);
        } else {
            expandIv.setImageResource(R.drawable.ic_drop_hide);
            collapse(recyclerView);
        }
    }

    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void checkRecyclerViewIsEmpty(List<?> list, RecyclerView recyclerView, TextView textView) {
        if (list.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
    }

    public static Picasso createPicassoWithAuth(Context context) {
        OkHttpClient picassoClient = new OkHttpClient();
        picassoClient.interceptors().add(new AuthInterceptor());
        return new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(picassoClient))
                .memoryCache(Cache.NONE)
                .build();
    }
}
