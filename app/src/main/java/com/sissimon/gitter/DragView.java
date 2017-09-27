package com.sissimon.gitter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by Simon on 18.06.2015.
 */
public class DragView extends View {

    private WindowManager mWindowManager;
    private float mScale;
    private Bitmap mBitmap;
    private Bitmap mBitmap2;
    private Paint mPaint;
    private Paint mPaint2;
    private int mRegistrationX;
    private int mRegistrationY;
    private boolean m2;
    private DragLayer mDraglayer;
    private MainActivity mMain;


    private WindowManager.LayoutParams mLayoutParams;

    public DragView(Context context, Bitmap bitmap, int registrationX, int registrationY,
                    int left, int top, int width, int height) {
        super(context);
        // mWindowManager = WindowManagerImpl.getDefault();
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        m2 = false;
        Matrix scale = new Matrix();
        float scaleFactor = width;
        scaleFactor = mScale = 1;
        scale.setScale(scaleFactor, scaleFactor);
        mBitmap = Bitmap.createBitmap(bitmap, left, top, width, height, scale, true);
    //    mBitmap2 = Bitmap.createBitmap(bitmap, left, top, width, height, scale, true);
        int i;
        int j;
        for (i=0;i<6;i++) {
            for (j = 1; j < 4 ;j++){
                if (registrationX > i*50 && registrationX < i*50 +50 && registrationY > j*50 && registrationY < j*50 +50){
   //             mBitmap2 = Bitmap.createBitmap(bitmap, i*50+25, j*50+25, width, height, scale, true);
   //                 m2 = true;
                }
            }
        }

        // The point in our scaled bitmap that the touch events are located
        mRegistrationX = registrationX;
        mRegistrationY = registrationY;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (m2) {
            // for debugging
            Paint p = new Paint();
            p.setStyle(Paint.Style.FILL);
            p.setColor(0x88dd0011);
            canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        }
        float scale = 1.0f;
        if (scale < 0.999f) { // allow for some float error
            float width = mBitmap.getWidth();
            float offset = (width-(width*scale))/2;
            canvas.translate(offset, offset);
            canvas.scale(scale, scale);
        }
        canvas.drawBitmap(mBitmap, 0.0f, 0.0f, mPaint);
//        canvas.drawBitmap(mBitmap2, 0.0f, 0.0f, mPaint);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBitmap.recycle();
    }


    public void show(IBinder windowToken, int touchX, int touchY) {
        WindowManager.LayoutParams lp;
        int pixelFormat;

        pixelFormat = PixelFormat.TRANSLUCENT;

        lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                touchX-mRegistrationX, touchY-mRegistrationY,
                WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    /*| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM*/,
                pixelFormat);
//        lp.token = mStatusBarView.getWindowToken();
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        lp.token = windowToken;
        lp.setTitle("DragView");
        mLayoutParams = lp;

        mWindowManager.addView(this, lp);

    }
    void move(int touchX, int touchY) {
        // This is what was done in the Launcher code.
        WindowManager.LayoutParams lp = mLayoutParams;
        lp.x = touchX - mRegistrationX;
        lp.y = touchY - mRegistrationY;
        mWindowManager.updateViewLayout(this, lp);
    }

    void remove() {
        mWindowManager.removeView(this);
    }


}
