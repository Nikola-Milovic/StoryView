package com.nikolam.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.nikolam.library.R;
import com.nikolam.library.progress.StoriesProgressView;

import java.util.List;

public class StoryView extends FrameLayout {

   private StoriesProgressView progressBar;

   private ImageView image;

   private List<String> imageUrls;

    public StoryView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public StoryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StoryView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context){
        View view = inflate(context, R.layout.story_view, this);
        progressBar = view.findViewById(R.id.progress_bars);
        image = view.findViewById(R.id.imageView);
    }

    public void addImageUrls(List<String> urls){
        imageUrls = urls;
        progressBar.initializeProgressBars(imageUrls.size());
        progressBar.startFromSpecificBar(0);

        image.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(image).load(imageUrls.get(0)).into(image);

    }


}
