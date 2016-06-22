package com.example.omid.ps01;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditImageActivity extends AppCompatActivity implements View.OnClickListener,
        CropFragment.OnCropListener, LightFragment.OnLightListener {
    private Bitmap mOriginalBitmap;
    private String mImagePath;
    private Button mCrop;
    private Button mLight;
    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        mImagePath = getIntent().getExtras().getString(getString(R.string.image_path));
        mOriginalBitmap = (Bitmap) getIntent().getExtras().getParcelable(getString(R.string
                .bitmap_image));
        mFragmentManager = getFragmentManager();
        mMainFragment = new MainFragment();
        addFragment(mMainFragment);
        mCrop = (Button) findViewById(R.id.button_crop);
        mLight = (Button) findViewById(R.id.button_light);
        mCrop.setOnClickListener(this);
        mLight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_crop:
                CropFragment cropFragment = new CropFragment();
                replaceFragment(cropFragment);
                break;
            case R.id.button_light:
                LightFragment lightFragment = new LightFragment();
                replaceFragment(lightFragment);
                break;
            default:
                break;
        }
    }

    private void addFragment(Fragment fragment) {
        android.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public Bitmap getBitmap() {
        return mOriginalBitmap;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void getCropImage(Bitmap bitmap) {
        mOriginalBitmap = bitmap;
    }

    @Override
    public void getLightImage(Bitmap bitmap) {
        mOriginalBitmap = bitmap;
    }
}