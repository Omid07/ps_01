package com.example.omid.ps01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MergeImageActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Bitmap mFirstBitmap, mFinalBitmap;
    private Button mDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_image);
        findViewByID();
        ArrayList<String> paths = (ArrayList<String>) getIntent().getSerializableExtra(getString
                (R.string.images_path));
        mFirstBitmap = ImageEffect.doScale(BitmapFactory.decodeFile(paths.get(1)), Constant
                .SCALING_X_VALUE, Constant.SCALING_Y_VALUE);
        mFinalBitmap = createSingleImageFromMultipleImages(BitmapFactory.decodeFile(paths.get(0))
                , mFirstBitmap);
        mImageView.setImageBitmap(mFinalBitmap);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaStore.Images.Media.insertImage(getContentResolver(), mFinalBitmap, null, null);
                Toast.makeText(getApplicationContext(), R.string.image_saved, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void findViewByID() {
        mImageView = (ImageView) findViewById(R.id.image_edit);
        mDone = (Button) findViewById(R.id.button_done);
    }

    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage) {
        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(),
                firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, 10, 10, null);
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycling();
    }

    public void recycling() {
        if (mFirstBitmap != null) {
            mFirstBitmap.recycle();
            mFirstBitmap = null;
        }
        if (mFinalBitmap != null) {
            mFinalBitmap.recycle();
            mFinalBitmap = null;
        }
    }
}