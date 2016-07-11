package com.example.omid.ps01;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EditImageActivity extends AppCompatActivity implements View.OnClickListener,
        CropFragment.OnCropListener, LightFragment.OnLightListener, ColorFragment
                .OnColorListener, BlackAndWhiteFragment.OnBlackAndWhiteListener, HueFragment
                .OnHueListener, ScaleFragment.OnScaleListener {
    private Bitmap mOriginalBitmap;
    private String mImagePath;
    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private Button mButtonCrop, mButtonLight, mButtonColor, mButtonBlacknWhite, mButtonHue,
            mScale, mSave;

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
        mButtonBlacknWhite.setOnClickListener(this);
        mButtonHue.setOnClickListener(this);
        mScale.setOnClickListener(this);
        mSave.setOnClickListener(this);
    }

    public void findViewByID() {
        mButtonCrop = (Button) findViewById(R.id.button_crop);
        mButtonLight = (Button) findViewById(R.id.button_light);
        mButtonColor = (Button) findViewById(R.id.button_color);
        mButtonLight = (Button) findViewById(R.id.button_light);
        mButtonBlacknWhite = (Button) findViewById(R.id.button_black_white);
        mButtonHue = (Button) findViewById(R.id.button_hue);
        mScale = (Button) findViewById(R.id.button_scale);
        mSave = (Button) findViewById(R.id.button_save);
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
            case R.id.button_black_white:
                BlackAndWhiteFragment blackAndWhiteFragment = new BlackAndWhiteFragment();
                replaceFragment(blackAndWhiteFragment);
                break;
            case R.id.button_hue:
                HueFragment hueFragment = new HueFragment();
                replaceFragment(hueFragment);
                break;
            case R.id.button_scale:
                ScaleFragment scaleFragment = new ScaleFragment();
                replaceFragment(scaleFragment);
                break;
            case R.id.button_save:
                MediaStore.Images.Media.insertImage(getContentResolver(), mOriginalBitmap, null,
                        null);
                Toast.makeText(getApplicationContext(), R.string.image_saved, Toast.LENGTH_LONG)
                        .show();
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
            mButtonBlacknWhite.setVisibility(View.GONE);
            mButtonHue.setVisibility(View.GONE);
            mScale.setVisibility(View.GONE);
            mSave.setVisibility(View.GONE);
        } else {
            mButtonCrop.setVisibility(View.VISIBLE);
            mButtonLight.setVisibility(View.VISIBLE);
            mButtonColor.setVisibility(View.VISIBLE);
            mButtonBlacknWhite.setVisibility(View.VISIBLE);
            mButtonHue.setVisibility(View.VISIBLE);
            mScale.setVisibility(View.VISIBLE);
            mSave.setVisibility(View.VISIBLE);
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

    @Override
    public void getBlackAndWhiteImage(Bitmap bitmap) {
        mOriginalBitmap = bitmap;
    }

    @Override
    public void getHueImage(Bitmap bitmap) {
        mOriginalBitmap = bitmap;
    }

    @Override
    public void getScaleImage(Bitmap bitmap) {
        mOriginalBitmap = bitmap;
    }

    public void recycling() {
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