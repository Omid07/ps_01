package com.example.omid.ps01;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButtonCamera;
    private Button mButtonGallery;
    private Button mButtonMerge;
    private Button mButtonVideo;
    private ImageSwitcher mImageSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByID();
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.
                        MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        mImageSwitcher.postDelayed(new Runnable() {
            int i = 0;
            public void run() {
                mImageSwitcher.setImageResource(i++ % 2 == 0 ? R.drawable.image1 : R.drawable
                        .image2);
                mImageSwitcher.postDelayed(this, Constant.IMAGE_SWTCHER_TIME);
            }
        }, Constant.IMAGE_SWTCHER_TIME);
        mButtonCamera.setOnClickListener(this);
        mButtonGallery.setOnClickListener(this);
        mButtonMerge.setOnClickListener(this);
        mButtonVideo.setOnClickListener(this);
    }

    public void findViewByID() {
        mButtonCamera = (Button) findViewById(R.id.button_camera);
        mButtonGallery = (Button) findViewById(R.id.button_gallery);
        mButtonMerge = (Button) findViewById(R.id.button_merge);
        mButtonVideo = (Button) findViewById(R.id.button_video);
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.image_switcher);
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.CAMERA_REQUEST);
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constant.GALLERY_REQUEST);
    }

    public void doMerge() {
        Intent intent = startIntentForImages();
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)),
                Constant.PICK_IMAGE_MULTIPLE);
    }

    public void makeVideo() {
        Intent intent = startIntentForImages();
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)),
                Constant.PICK_IMAGES_FOR_VIDEO);
    }

    public Intent startIntentForImages() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        intent.setType(Constant.IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        return intent;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == Constant.CAMERA_REQUEST) {
            Bitmap bitmap = (Bitmap) data.getExtras().get(getString(R.string.data));
            Intent intent = new Intent(MainActivity.this, EditImageActivity.class);
            intent.putExtra(getString(R.string.bitmap_image), bitmap);
            startActivity(intent);
        } else if (requestCode == Constant.GALLERY_REQUEST) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null,
                    null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Intent intent = new Intent(MainActivity.this, EditImageActivity.class);
            intent.putExtra(getString(R.string.image_path), picturePath);
            startActivity(intent);
        }
        if (requestCode == Constant.PICK_IMAGE_MULTIPLE) {
            ArrayList<String> paths = getImagesPaths(data);
            if (paths.size() == Constant.MAX_NUMBER_IMAGE) {
                Intent intent = new Intent(MainActivity.this, MergeImageActivity.class);
                intent.putExtra(getString(R.string.images_path), paths);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), R.string.select_two_images, Toast
                        .LENGTH_LONG).show();
            }
        }
        if (requestCode == Constant.PICK_IMAGES_FOR_VIDEO) {
            ArrayList<String> imagesPaths = getImagesPaths(data);
            if (imagesPaths.size() > Constant.MAX_NUMBER_IMAGE_FOR_VIDEO) {
                Toast.makeText(getApplicationContext(), R.string.select_max_six, Toast
                        .LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                intent.putExtra(getString(R.string.images_path), imagesPaths);
                startActivity(intent);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<String> getImagesPaths(Intent data) {
        ArrayList<String> paths = new ArrayList<>();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        if (data != null) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null,
                            null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePaths = cursor.getString(columnIndex);
                    paths.add(picturePaths);
                    cursor.close();
                }
            }
        }
        return paths;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_camera:
                openCamera();
                break;
            case R.id.button_gallery:
                openGallery();
                break;
            case R.id.button_merge:
                doMerge();
                break;
            case R.id.button_video:
                makeVideo();
            default:
                break;
        }
    }
}
