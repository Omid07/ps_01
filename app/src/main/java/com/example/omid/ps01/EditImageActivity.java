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
        CropFragment.OnCropListener, LightFragment.OnLightListener, ColorFragment.OnColorListener {
    private Bitmap mOriginalBitmap;
    private String mImagePath;
    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private Button mButtonCrop, mButtonLight, mButtonColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        findViewByID();
        mImagePath = getIntent().getExtras().getString(getString(R.string.image_path));
        mOriginalBitmap = (Bitmap) getIntent().getExtras().getParcelable(getString(R.string
                .bitmap_image));
        mFragmentManager = getFragmentManager();
        mMainFragment = new MainFragment();
        addFragment(mMainFragment);
        mButtonCrop.setOnClickListener(this);
        mButtonLight.setOnClickListener(this);
        mButtonColor.setOnClickListener(this);
    }

    public void findViewByID() {
        mButtonCrop = (Button) findViewById(R.id.button_crop);
        mButtonLight = (Button) findViewById(R.id.button_light);
        mButtonColor = (Button) findViewById(R.id.button_color);
        mButtonLight = (Button) findViewById(R.id.button_light);
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
            case R.id.button_color:
                ColorFragment colorFragment = new ColorFragment();
                replaceFragment(colorFragment);
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
        if (fragment.getClass() != MainFragment.class) {
            mButtonCrop.setVisibility(View.GONE);
            mButtonLight.setVisibility(View.GONE);
            mButtonColor.setVisibility(View.GONE);
        } else {
            mButtonCrop.setVisibility(View.VISIBLE);
            mButtonLight.setVisibility(View.VISIBLE);
            mButtonColor.setVisibility(View.VISIBLE);
        }
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

    @Override
    public void getCropImage(Bitmap bitmap) {
        mOriginalBitmap = bitmap;
    }

    @Override
    public void getLightImage(Bitmap bitmap) {
        mOriginalBitmap = bitmap;
    }

    @Override
    public void getColorImage(Bitmap bitmap) {
        mOriginalBitmap = bitmap;
    }

    public void recycling()
    {
        if (mOriginalBitmap != null) {
            mOriginalBitmap.recycle();
            mOriginalBitmap = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycling();
    }
}