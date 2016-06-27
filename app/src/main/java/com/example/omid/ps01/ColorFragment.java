package com.example.omid.ps01;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ColorFragment extends Fragment implements View.OnClickListener {
    private ImageView mImageView;
    private EditImageActivity mEditImageActivity;
    private Button mButtonDone, mButtonRed, mButtonBlue, mButtonGreen;
    private Bitmap mColor, mBitmap, mOutput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViewsInLayout();
        View view = inflater.inflate(R.layout.fragment_color, container, false);
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
            mBitmap = BitmapFactory.decodeFile(mImagePath);
        }
        mButtonDone.setOnClickListener(this);
        mButtonRed.setOnClickListener(this);
        mButtonBlue.setOnClickListener(this);
        mButtonGreen.setOnClickListener(this);
    }

    public void findViewByID(View view) {
        mImageView = (ImageView) view.findViewById(R.id.image_edit);
        mButtonDone = (Button) view.findViewById(R.id.button_done);
        mButtonRed = (Button) view.findViewById(R.id.button_red);
        mButtonBlue = (Button) view.findViewById(R.id.button_blue);
        mButtonGreen = (Button) view.findViewById(R.id.button_green);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_done:
                mEditImageActivity.getColorImage(mColor);
                Fragment fragment = new MainFragment();
                mEditImageActivity.replaceFragment(fragment);
                break;
            case R.id.button_red:
                mOutput = ImageEffect.doColor(mBitmap, Color.RED);
                mImageView.setImageBitmap(mOutput);
                mColor = mOutput;
                break;
            case R.id.button_blue:
                mOutput = ImageEffect.doColor(mBitmap, Color.BLUE);
                mImageView.setImageBitmap(mOutput);
                mColor = mOutput;
                break;
            case R.id.button_green:
                mOutput = ImageEffect.doColor(mBitmap, Color.GREEN);
                mImageView.setImageBitmap(mOutput);
                mColor = mOutput;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mEditImageActivity = (EditImageActivity) context;
    }

    public interface OnColorListener {
        void getColorImage(Bitmap bitmap);
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
