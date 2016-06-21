package com.example.omid.ps01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class EditImageActivity extends AppCompatActivity {
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        mImage = (ImageView) findViewById(R.id.image_edit);
        String imagePath = getIntent().getExtras().getString(getString(R.string.image_path));
        Bitmap bitmap = (Bitmap) getIntent().getExtras().getParcelable(getString(R.string
                .bitmap_image));
        if (bitmap != null) {
            mImage.setImageBitmap(bitmap);
        }
        if (imagePath != null) {
            mImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }
    }
}
