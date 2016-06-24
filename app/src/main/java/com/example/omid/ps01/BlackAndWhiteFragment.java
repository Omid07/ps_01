package com.example.omid.ps01;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class BlackAndWhiteFragment extends Fragment implements View.OnClickListener {
    private ImageView mImageView;
    private EditImageActivity mEditImageActivity;
    private Button mButtonDone, mButtonApply;
    private Bitmap mBlackAndWhite, mOutput, mBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_black_and_white, container, false);
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
        mButtonApply.setOnClickListener(this);
        mButtonDone.setOnClickListener(this);
    }

    public void findViewByID(View view) {
        mImageView = (ImageView) view.findViewById(R.id.image_edit);
        mButtonDone = (Button) view.findViewById(R.id.button_done);
        mButtonApply = (Button) view.findViewById(R.id.button_apply);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mEditImageActivity = (EditImageActivity) context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_done:
                mEditImageActivity.getBlackAndWhiteImage(mBlackAndWhite);
                Fragment fragment = new MainFragment();
                mEditImageActivity.replaceFragment(fragment);
                break;
            case R.id.button_apply:
                mOutput = ImageEffect.doBlackAndWhite(mBitmap);
                mImageView.setImageBitmap(mOutput);
                mBlackAndWhite = mOutput;
                break;
            default:
                break;
        }
    }

    public interface OnBlackAndWhiteListener {
        void getBlackAndWhiteImage(Bitmap bitmap);
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