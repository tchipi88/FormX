package com.appli.nyx.formx.ui.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.appli.nyx.formx.R;

public class EditTextUnitDrawable extends Drawable {

    private final Context context;
    private final String text;
    private final Paint paint;

    public EditTextUnitDrawable(Context context, String text) {
        this.context = context;
        this.text = text;
        this.paint = new Paint();
        paint.setTextSize(16f);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.RIGHT);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text, 0, context.getResources().getDimensionPixelSize(R.dimen.unit_margin_bottom), paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public int getWidth() {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    public int getHeight() {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }
}
