package com.nikolam.storyview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.nikolam.library.progress.StoriesProgressView;

public class MainActivity extends AppCompatActivity {

    private StoriesProgressView stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stories = findViewById(R.id.stories);
    }

    @Override
    protected void onStart() {
        super.onStart();
        stories.initializeProgressBars(12);
    }
}