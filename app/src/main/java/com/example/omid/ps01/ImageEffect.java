package com.example.omid.ps01;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

/**
 * Created by omid on 6/23/16.
 */
public class ImageEffect {
    public static Bitmap doBrightness(Bitmap src, int value) {
        Bitmap alteredBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig
                ());
        Canvas canvas = new Canvas(alteredBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        float b = (float) value;
        colorMatrix.set(new float[]{1, 0, 0, 0, b,
                0, 1, 0, 0, b,
                0, 0, 1, 0, b,
                0, 0, 0, 1, 0});
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        Matrix matrix = new Matrix();
        canvas.drawBitmap(src, matrix, paint);
        return alteredBitmap;
    }

    public static Bitmap doColor(Bitmap src, int color) {
        Bitmap alteredBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig
                ());
        Paint paint = new Paint();
        ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
        paint.setColorFilter(colorFilter);
        Canvas canvas = new Canvas(alteredBitmap);
        canvas.drawBitmap(src, 0, 0, paint);
        return alteredBitmap;
    }
}
