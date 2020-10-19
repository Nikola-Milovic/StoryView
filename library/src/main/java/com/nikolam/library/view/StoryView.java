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
import com.nikolam.library.progress.ProgressListener;
import com.nikolam.library.progress.StoriesProgressView;

import java.util.List;

//Todo, add previous story, left or right click and swipe to go back and forward
// fix lag with image loading
public class StoryView extends FrameLayout implements ProgressListener {

   private StoriesProgressView progressBar;

   private ImageView image;

   private int currentStoryPosition = 0;

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
        progressBar.startFromSpecificBar(currentStoryPosition);

        progressBar.setListener(this);

        image.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(image).load(imageUrls.get(currentStoryPosition)).into(image);

        preloadImage();

    }


    //Todo add exception
    public void nextStory()  {
        if(currentStoryPosition == imageUrls.size()-1){
            //throw new Exception();
            return;
        }

        currentStoryPosition++;
        Glide.with(image).load(imageUrls.get(currentStoryPosition)).into(image);
        progressBar.startFromSpecificBar(currentStoryPosition);
         preloadImage();

    }

    @Override
    public void onStoryEnd() {
        super.onAnimationEnd();
        nextStory();
    }

    private void preloadImage(){
        if(currentStoryPosition + 1 != imageUrls.size())
        Glide.with(image)
                .load(imageUrls.get(currentStoryPosition+1))
                .preload();
    }

}
