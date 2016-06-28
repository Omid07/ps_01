package com.example.omid.ps01;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ScaleFragment extends Fragment implements View.OnClickListener {
    private Button mButtonDone, mButtonApply;
    private Bitmap mScale, mBitmap, mOutPut;
    private EditImageActivity mEditImageActivity;
    private ImageView mImageView;
    private EditText mXvalue, mYvalue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scale, container, false);
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
        mButtonApply.setOnClickListener(this);
    }

    public void findViewByID(View view) {
        mImageView = (ImageView) view.findViewById(R.id.image_edit);
        mButtonDone = (Button) view.findViewById(R.id.button_done);
        mButtonApply = (Button) view.findViewById(R.id.button_apply);
        mXvalue = (EditText) view.findViewById(R.id.et_xvalue);
        mYvalue = (EditText) view.findViewById(R.id.et_yvalue);
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
                mEditImageActivity.getScaleImage(mScale);
                Fragment fragment = new MainFragment();
                mEditImageActivity.replaceFragment(fragment);
                break;
            case R.id.button_apply:
                applyScaling();
                break;
            default:
                break;
        }
    }

    public void applyScaling() {
        if ((!TextUtils.isEmpty(mXvalue.getText().toString())) && (!TextUtils.isEmpty(mYvalue.getText().toString()))) {
            int xValue = Integer.valueOf(mXvalue.getText().toString());
            int yValue = Integer.valueOf(mXvalue.getText().toString());
            if ((xValue > Constant.MAX_X_VALUE) || (yValue > Constant.MAX_Y_VALUE)) {
                Toast.makeText(getActivity(), R.string.reduce_sacle_value, Toast.LENGTH_LONG).show();
            } else {
                mOutPut = ImageEffect.doScale(mBitmap, xValue, yValue);
                mImageView.setImageBitmap(mOutPut);
                mScale = mOutPut;
            }
        } else {
            Toast.makeText(getActivity(), R.string.value_for_sacle, Toast.LENGTH_LONG).show();
        }
    }

    public interface OnScaleListener {
        void getScaleImage(Bitmap bitmap);
    }
}