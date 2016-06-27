package com.example.omid.ps01;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LightFragment extends Fragment implements View.OnClickListener {
    private ImageView mImageView;
    private EditImageActivity mEditImageActivity;
    private Button mButtonDone;
    private ImageButton mButtonIncrease, mButtonDecrease;
    private Bitmap mLight, mBitmap, mOutPut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        container.removeAllViewsInLayout();
        View view = inflater.inflate(R.layout.fragment_light, container, false);
        findViewByID(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBitmap = mEditImageActivity.getBitmap();
        if (mBitmap != null) {
            mImageView.setImageBitmap(mBitmap);
        } else {
            String mImagePath = mEditImageActivity.getImagePath();
            mImageView.setImageBitmap(BitmapFactory.decodeFile(mImagePath));
        }
        mButtonDone.setOnClickListener(this);
        mButtonIncrease.setOnClickListener(this);
        mButtonDecrease.setOnClickListener(this);
    }

    public void findViewByID(View view) {
        mImageView = (ImageView) view.findViewById(R.id.image_edit);
        mButtonDone = (Button) view.findViewById(R.id.button_done);
        mButtonIncrease = (ImageButton) view.findViewById(R.id.button_increase);
        mButtonDecrease = (ImageButton) view.findViewById(R.id.button_dicrease);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_done:
                mEditImageActivity.getLightImage(mLight);
                Fragment fragment = new MainFragment();
                mEditImageActivity.replaceFragment(fragment);
                break;
            case R.id.button_increase:
                mBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                mOutPut = ImageEffect.doBrightness(mBitmap, Constant.BRIGHTNESS_INCREASE);
                mImageView.setImageBitmap(mOutPut);
                mLight = mOutPut;
                break;
            case R.id.button_dicrease:
                mBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                mOutPut = ImageEffect.doBrightness(mBitmap, Constant.BRIGHTNESS_DECREASE);
                mImageView.setImageBitmap(mOutPut);
                mLight = mOutPut;
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mEditImageActivity = (EditImageActivity) context;
    }

    public interface OnLightListener {
        void getLightImage(Bitmap bitmap);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycling();
    }

    public void recycling()
    {
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}