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

import com.theartofdev.edmodo.cropper.CropImageView;

public class CropFragment extends Fragment {
    private CropImageView mImageView;
    private EditImageActivity mEditImageActivity;
    private Button mButtonDone;
    private Bitmap mCropped, mBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViewsInLayout();
        View view = inflater.inflate(R.layout.fragment_crop, container, false);
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
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropped = mImageView.getCroppedImage();
                mEditImageActivity.getCropImage(mCropped);
                Fragment fragment = new MainFragment();
                mEditImageActivity.replaceFragment(fragment);
            }
        });
    }

    public void findViewByID(View view) {
        mImageView = (CropImageView) view.findViewById(R.id.image_edit);
        mButtonDone = (Button) view.findViewById(R.id.button_done);
        mImageView = (CropImageView) view.findViewById(R.id.image_edit);
        mButtonDone = (Button) view.findViewById(R.id.button_done);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mEditImageActivity = (EditImageActivity) context;
    }

    public interface OnCropListener {
        void getCropImage(Bitmap bitmap);
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
