package com.nikolam.library.progress;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StoriesProgressView extends LinearLayout {

    private int storyCount = 0;

    private int current = 0;

    private final LayoutParams PROGRESS_BAR_LAYOUT_PARAM = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
    private final LayoutParams SPACE_LAYOUT_PARAM = new LayoutParams(5, LayoutParams.WRAP_CONTENT);

    private final List<PausableProgressBar> progressBars = new ArrayList<>();

    private ProgressListener listener;

    public StoriesProgressView(Context context) {
        super(context);
    }

    public StoriesProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StoriesProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void initializeProgressBars(int storyCount){
        this.storyCount = storyCount;
        setOrientation(LinearLayout.HORIZONTAL);

        createViews();

    }

    private void createViews(){
        progressBars.clear();
        removeAllViews();

        for(int i = 1; i <= storyCount; i++){
            PausableProgressBar b = createProgressbar();
            progressBars.add(b);
            addView(b);

            if(storyCount > 1 && i != storyCount){
                View v = createSpace();
                addView(v);
            }
        }
    }


    public void startFromSpecificBar(int position) throws IndexOutOfBoundsException{
        if(position < 0 || position > progressBars.size()){
            throw new IndexOutOfBoundsException("Invalid index " + position);
        }
        for(int i = 0; i < position; i++){
            progressBars.get(i).setBarToFull();
        }
        progressBars.get(position).startProgress();
    }

    public void setStoryDuration(Long duration){
        for(PausableProgressBar bar : progressBars){
            bar.setDuration(duration);
        }
    }

    private PausableProgressBar createProgressbar(){
        PausableProgressBar bar = new PausableProgressBar(getContext());
        bar.setLayoutParams(PROGRESS_BAR_LAYOUT_PARAM);
        return bar;
    }

    private View createSpace(){
        View v = new View(getContext());
        v.setLayoutParams(SPACE_LAYOUT_PARAM);
        return v;
    }


    public void setListener(ProgressListener listener) {
        this.listener = listener;
        for(int i = 0; i < progressBars.size(); i++){
            progressBars.get(i).listener = listener;
        }

    }
}
