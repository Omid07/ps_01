package com.example.omid.ps01;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MainFragment extends Fragment {
    private ImageView mImageView;
    private EditImageActivity mEditImageActivity;
    private Button mButtonCrop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mImageView = (ImageView) view.findViewById(R.id.image_edit);
        mButtonCrop = (Button) getActivity().findViewById(R.id.button_crop);
        mButtonCrop.setVisibility(View.VISIBLE);
        Bitmap bitmap = mEditImageActivity.getBitmap();
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            String mImagePath = mEditImageActivity.getImagePath();
            mImageView.setImageBitmap(BitmapFactory.decodeFile(mImagePath));
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mEditImageActivity = (EditImageActivity) activity;
    }
}
