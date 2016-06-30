package com.example.omid.ps01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {
    private ImageView mImageView;
    private MediaPlayer mediaPlayer;
    private Button mStop;
    private Bitmap mBitmap;
    private AnimationDrawable mAnimation;
    private Drawable mDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ArrayList<String> paths = (ArrayList<String>) getIntent().getSerializableExtra(getString
                (R.string.images_path));
        findViewByID();
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mAnimation = new AnimationDrawable();
        for (int i = 0; i < paths.size(); i++) {
            mBitmap = ImageEffect.doScale(BitmapFactory.decodeFile(paths.get(i)), Constant
                    .VIDEO_IMAGE_SCALING_X_VALUE, Constant.VIDEO_IMAGE_SCALING_Y_VALUE);
            mDrawable = new BitmapDrawable(mBitmap);
            mAnimation.addFrame(mDrawable, Constant.DURATION);
        }
        mAnimation.setOneShot(false);
        mImageView.setBackgroundDrawable(mAnimation);
        mediaPlayer.start();
        mAnimation.start();
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.stop();
                mediaPlayer.stop();
            }
        });
    }

    public void findViewByID() {
        mImageView = (ImageView) findViewById(R.id.image_switcher);
        mStop = (Button) findViewById(R.id.button_stop);
    }
}
