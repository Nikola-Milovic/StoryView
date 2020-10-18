package com.nikolam.library.progress;

import android.content.Context;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

import com.nikolam.library.R;

//Todo make private
public class PausableProgressBar extends FrameLayout {

    private static final int DEFAULT_PROGRESS_DURATION = 2000;

    private View frontProgressView;
    private View maxProgressView;

    private PausableProgressAnimation animation;

    public PausableProgressBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PausableProgressBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PausableProgressBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    private long duration = DEFAULT_PROGRESS_DURATION;


    private  void init(Context context){
        View view = inflate(context, R.layout.pausable_progress, this);
        frontProgressView = view.findViewById(R.id.front_progress);
        maxProgressView = view.findViewById(R.id.max_progress);
    }

    public void startProgress() {
        maxProgressView.setVisibility(GONE);

        animation = new PausableProgressAnimation(0, 1, 1, 1, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(duration);
        animation.setInterpolator(new LinearInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                frontProgressView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });
        animation.setFillAfter(true);
        frontProgressView.startAnimation(animation);
    }

    void setBarToFull() {
        maxProgressView.setBackgroundResource(R.color.progress_max_active);

        maxProgressView.setVisibility(VISIBLE);
        if (animation != null) {
            animation.setAnimationListener(null);
            animation.cancel();
        }
    }
    //Todo Make these protected probably

    public void pauseProgress() {
        if (animation != null) {
            animation.pause();
        }
    }

    public void resumeProgress() {
        if (animation != null) {
            animation.resume();
        }
    }

    void clear() {
        if (animation != null) {
            animation.setAnimationListener(null);
            animation.cancel();
            animation = null;
        }
    }


    private static class PausableProgressAnimation extends ScaleAnimation {

        private boolean mPaused = false;
        private long mElapsed = 0;

        public PausableProgressAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
            super(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
        }

        @Override
        public boolean getTransformation(long currentTime, Transformation outTransformation, float scale) {
            if (mPaused && mElapsed == 0) {
                mElapsed = currentTime - getStartTime();
            }
            if (mPaused) {
                setStartTime(currentTime - mElapsed);
            }
            return super.getTransformation(currentTime, outTransformation, scale);
        }

        void pause() {
            if (mPaused) return;
            mElapsed = 0;
            mPaused = true;
        }

        void resume() {
            mPaused = false;
        }
    }


}
