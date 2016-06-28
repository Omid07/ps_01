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

    public static Bitmap doBlackAndWhite(Bitmap src) {
        Bitmap alteredBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig
                ());
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(filter);
        Matrix matrix = new Matrix();
        Canvas canvas = new Canvas(alteredBitmap);
        canvas.drawBitmap(src, matrix, paint);
        return alteredBitmap;
    }

    public static Bitmap doHUE(Bitmap src, int value) {
        float a = (float) value;
        Bitmap alteredBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas canvas = new Canvas(alteredBitmap);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        a = (a % 360.0f) * (float) Math.PI / 180.0f;
        float cosVal = (float) Math.cos(value);
        float sinVal = (float) Math.sin(value);
        float lumR = 0.213f;
        float lumG = 0.715f;
        float lumB = 0.072f;
        cm.set(new float[]{lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) +
                sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) +
                sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG)
                + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                0f, 0f, 0f, 1f, 0f,
                0f, 0f, 0f, 0f, 1f});
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(src, 0, 0, paint);
        return alteredBitmap;
    }

    public static Bitmap doScale(Bitmap src, int x, int y) {
       return Bitmap.createScaledBitmap(src, x, y, true);
    }
}
