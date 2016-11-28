package com.devgary.sandbox.glide_vs_picasso.imageloadercomparison;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.devgary.sandbox.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Gary on 2016-11-25.
 */

public class ImageLoadingActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Bind(R.id.floatingactionbutton) FloatingActionButton floatingActionButton;
    @Bind(R.id.imageview1) ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loading);

        ButterKnife.bind(this);

        flashImageViewBackgroundColor();

        final int imageResource = R.drawable.wide_image;
        final Random ran = new Random();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int overrideWidth = Math.max(1, ran.nextInt(1080));

                Glide.with(ImageLoadingActivity.this)
                     .load(imageResource)
                     .override(overrideWidth, Target.SIZE_ORIGINAL)
                     .listener(new RequestListener<Integer, GlideDrawable>() {
                         @Override
                         public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
                             return false;
                         }

                         @Override
                         public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                             Bitmap bitmap = ((GlideBitmapDrawable) resource).getBitmap();

                             Timber.d("GLIDE DEBUG: ==============================================================================");
                             Timber.d("GLIDE DEBUG: Override Size:           " + overrideWidth  + " x " + "Target.SIZE_ORIGINAL");
                             Timber.d("GLIDE DEBUG: Bitmap size:             " + bitmap.getWidth() + " x " + bitmap.getHeight());
                             Timber.d("GLIDE DEBUG: ==============================================================================");
                             Timber.d("GLIDE DEBUG: ");

                             return false;
                         }
                     })
                     .into(imageView1);
            }
        });
    }

    private void flashImageViewBackgroundColor() {

        final List<Integer> colors = new ArrayList<>();

        colors.add(R.color.md_amber_500);
        colors.add(R.color.md_teal_500);
        colors.add(R.color.md_red_500);
        colors.add(R.color.md_pink_500);
        colors.add(R.color.md_white_1000);
        colors.add(R.color.md_cyan_400);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                imageView1.setBackgroundColor(ContextCompat.getColor(ImageLoadingActivity.this, colors.get((int) (Math.random() * colors.size()))));

                handler.postDelayed(this, 50);
            }
        };

        handler.postDelayed(runnable, 50);
    }
}
