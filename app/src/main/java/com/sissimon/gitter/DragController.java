package com.sissimon.gitter;

/**
 * Created by Simon on 17.06.2015.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * This class is used to initiate a drag within a view or across multiple views.
 * When a drag starts it creates a special view (a DragView) that moves around the screen
 * until the user ends the drag. As feedback to the user, this object causes the device to
 * vibrate as the drag begins.80
 */

public class DragController {

    public int joint = 0;
    private int counter;
    public ArrayList<String> elementList = new ArrayList<String>(24*14);
    public ArrayList<String> jointList = new ArrayList<>(24);
    private double mChoiceY =0;
    private double mChoiceX =0;
    public float mHeight;
    private float mWidth;
    private int mScreenX;
    public boolean tablet;
    private float gitterX;
    private float gitterY;
    public float dpX;
    public float dpY;

    private static final String TAG = "DragController";

    /** Indicates the drag is a move.  */
    public static int DRAG_ACTION_MOVE = 0;


    private Context mContext;
    private Vibrator mVibrator;

    // temporaries to avoid gc thrash
    private Rect mRectTemp = new Rect();
    private final int[] mCoordinatesTemp = new int[2];

    /** Whether or not we're dragging. */
    private boolean mDragging;

    /** X coordinate of the down event. */
    private float mMotionDownX;

    /** Y coordinate of the down event. */
    private float mMotionDownY;

    /** Info about the screen for clamping. */
    private DisplayMetrics mDisplayMetrics = new DisplayMetrics();

    /** Original view that is being dragged.  */
    private View mOriginator;

    /** X offset from the upper-left corner of the cell to where we touched.  */
    private float mTouchOffsetX;

    /** Y offset from the upper-left corner of the cell to where we touched.  */
    private float mTouchOffsetY;

    /** Where the drag originated */
    private DragSource mDragSource;

    /** The data associated with the object being dragged */
    private Object mDragInfo;
    private ImageView imageView;

    /** The view that moves around while you drag.  */
    private DragView mDragView;
    private DragView mDragView2;

    /** Who can receive drop events */
    private ArrayList<DropTarget> mDropTargets = new ArrayList<DropTarget>();

    private DragListener mListener;

    /** The window token used as the parent for the DragView. */
    private IBinder mWindowToken;

    private View mMoveTarget;

    private DropTarget mLastDropTarget;

    public String mchoice;
    public MotionEvent mEvent;

    interface DragListener {
        void onDragStart(DragSource source, Object info, int dragAction);
        void onDragEnd();
    }

    public DragController(Context context) {
        mContext = context;
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void setElementList (){
        int i;
        for (i=0;i<(14*88+1);i++){
            elementList.add(i," ");
            if (i<89){
                jointList.add(i,"");
            }

        }
    }

    public void startDrag(String choice,DragSource source, View v, Object dragInfo, int dragAction, MotionEvent ev, RelativeLayout mLayout){
        mOriginator = v;
        imageView = (ImageView) v;
        mchoice = choice;
        mEvent = ev;
        Bitmap b = getViewBitmap(v);
        if (b == null) {
            // out of memory?
            return;
        }
        mHeight = mLayout.getHeight();
        mWidth = mLayout.getWidth();

        dpX = mWidth *160/ mDisplayMetrics.densityDpi;
        dpY = mHeight *160/ mDisplayMetrics.densityDpi;
        if(dpX > 601 ){
            tablet = true;
            gitterX=11;
            gitterY=8;
        }
        else {
            tablet = false;
            gitterX = 6;
            gitterY = 4;
        }

        switch (choice) {
            case "Festlager 1":
                mChoiceX = (double) 31;
                mChoiceY = (double) 41;
                counter = 0;
                break;
            case "Festlager 2":
                mChoiceX = (double) 41;
                mChoiceY = (double) 32;
                counter = 1;
                break;
            case "Loslager 1":
                mChoiceX = (double) 32;
                mChoiceY = (double) 39;
                counter = 2;
                break;
            case "Loslager 2":
                mChoiceX = (double) 39;
                mChoiceY = (double) 30;
                counter = 3;
                break;
            case "Loslager 3":
                mChoiceX = (double) 41;
                mChoiceY = (double) 41;
                counter = 4;
                break;
            case "Loslager 4":
                mChoiceX = (double) 22;
                mChoiceY = (double) 41;
                counter = 5;
                break;
            case "Stab 1":
                mChoiceX = (double) 60;
                mChoiceY = (double) -18;
                counter = 6;
                break;
            case "Stab 2":
                mChoiceX = (double) -18;
                mChoiceY = (double) -18;
                counter = 7;
                break;
            case "Stab 3":
                mChoiceX = (double) 60;
                mChoiceY = (double) 20;
                counter = 8;
                break;
            case "Stab 4":
                mChoiceX = (double) 21;
                mChoiceY = (double) -19;
                counter = 9;
                break;
            case "Kraft 1":
                mChoiceX = (double) 31;
                mChoiceY = (double) -2;
                counter = 10;
                break;
            case "Kraft 2":
                mChoiceX = (double) 31;
                mChoiceY = (double) 62;
                counter = 11;
                break;
            case "Kraft 3":
                mChoiceX = (double) -2;
                mChoiceY = (double) 31;
                counter = 12;
                break;
            case "Kraft 4":
                mChoiceX = (double) 63;
                mChoiceY = (double) 31;
                counter = 13;
                break;
        }
        if (tablet) {
            mChoiceX = mChoiceX + 20;
            mChoiceY = mChoiceY + 20;
        }

        int[] loc = mCoordinatesTemp;
        v.getLocationOnScreen(loc);
        int screenX = loc[0];
        int screenY = loc[1];
        mScreenX = screenX;

        startDrag(b, screenX, screenY, 0, 0, b.getWidth(), b.getHeight(),
                source, dragInfo, dragAction);

        b.recycle();

        if (dragAction == 0) {
            v.setVisibility(View.GONE);
        }
    }

    public void startDrag(Bitmap b, int screenX, int screenY,
                          int textureLeft, int textureTop, int textureWidth, int textureHeight,
                          DragSource source, Object dragInfo, int dragAction) {

        if (mListener != null) {
            mListener.onDragStart(source, dragInfo, dragAction);
        }

        int registrationX = ((int)mMotionDownX) - screenX;
        int registrationY = ((int)mMotionDownY) - screenY;

        mTouchOffsetX = mMotionDownX - screenX;
        mTouchOffsetY = mMotionDownY - screenY;

        mDragging = true;
        mDragSource = source;
        mDragInfo = dragInfo;

        mVibrator.vibrate(35);

        DragView dragView = mDragView = new DragView(mContext, b, registrationX, registrationY,
                textureLeft, textureTop, textureWidth, textureHeight);
        dragView.show(mWindowToken, (int)mMotionDownX, (int)mMotionDownY);

        double X3;
        double X4;
        double Y3;
        double Y4;
        double XX;
        double YY;
        if (tablet == false) {
            X3 = (screenX - mTouchOffsetX + 60) / mWidth - mChoiceX / 600 + 0.1;
            X4 = (screenX - mTouchOffsetX + 60) / mWidth - mChoiceX / 600 - 0.1;
            Y3 = (screenY - mTouchOffsetY + 60) / mHeight - mChoiceY / 360 + 0.1;
            Y4 = (screenY - mTouchOffsetY + 60) / mHeight - mChoiceY / 360 - 0.1;
            XX = (screenX - mTouchOffsetX + 60) / mWidth - mChoiceX / 600;
            YY = (screenY - mTouchOffsetY + 60) / mHeight - mChoiceY / 360;
        }
        else {
            X3 = (screenX - mTouchOffsetX + 60) / mWidth - mChoiceX / dpX + 0.1;
            X4 = (screenX - mTouchOffsetX + 60) / mWidth - mChoiceX / dpX - 0.1;
            Y3 = (screenY - mTouchOffsetY + 60) / mHeight - mChoiceY / dpY + 0.1;
            Y4 = (screenY - mTouchOffsetY + 60) / mHeight - mChoiceY / dpY - 0.1;
            XX = (screenX - mTouchOffsetX + 60) / mWidth - mChoiceX / dpX;
            YY = (screenY - mTouchOffsetY + 60) / mHeight - mChoiceY / dpY;
        }

        double i;
        double j;
        int ii=6;
        int jj=5;
        double min = 1.0;
        for (i=0;i<gitterX;i++) {
            for (j = 0; j < gitterY; j++) {
                if (X3 > i / (gitterX+1.5) && X4 < i / (gitterX+1.5) && Y3 > j / (gitterY+0.5) && Y4 < j / (gitterY+0.5)
                        && elementList.get(counter * 88 + (int) j * 11 + (int) i) == "y") {
                    if ((XX - (i / (gitterX+1.5))) * (XX - (i / (gitterX+1.5))) + (YY - (j / (gitterY+0.5))) * (YY - (j / (gitterY+0.5))) < min) {
                        ii = (int) i;
                        jj = (int) j;
                        min = ((XX - (i /(gitterX+1.5))) * (XX - (i /(gitterX+1.5)))) + ((YY - (j / (gitterY+0.5))) * (YY - (j / (gitterY+0.5))));
                    }
                }
            }
        }
        if ((ii != 6 && tablet == false) || tablet == true) {
            elementList.set(counter * 88 + jj * 11 + ii, "");

            if(counter<10 && counter>5){
                deleteJoint(ii,jj);
            }
        }


    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);


        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }

        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e(TAG, "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);


        return bitmap;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return mDragging;
    }

    void setMoveTarget(View view) {
        mMoveTarget = view;
    }

    public void cancelDrag() {
        endDrag();
    }

    private void endDrag() {
        if (mDragging) {
            mDragging = false;
            if (mOriginator != null) {
                mOriginator.setVisibility(View.VISIBLE);
            }
            if (mListener != null) {
                mListener.onDragEnd();
            }
            if (mDragView != null) {
                mDragView.remove();
                mDragView = null;
            }
            if (mDragView2 != null) {
                mDragView2.remove();
                mDragView2 = null;
            }
        }
    }
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            recordScreenSize();
        }

        final int screenX = clamp((int)ev.getRawX(), 0, mDisplayMetrics.widthPixels);
        final int screenY = clamp((int)ev.getRawY(), 0, mDisplayMetrics.heightPixels);

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_DOWN:
                // Remember location of down touch
                mMotionDownX = screenX;
                mMotionDownY = screenY;

                mLastDropTarget = null;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                double i;
                double j;
                double X1 = (screenX - mTouchOffsetX)/mWidth - mChoiceX/dpX +0.06;
                double X2 = (screenX - mTouchOffsetX)/mWidth - mChoiceX/dpX -0.06;
                double Y1 = (screenY - mTouchOffsetY)/mHeight - mChoiceY/dpY+0.06;
                double Y2 = (screenY - mTouchOffsetY)/mHeight - mChoiceY/dpY -0.06;
                if (mDragging) {
                    for (i = 0; i < gitterX; i++) {
                        for (j = 0; j < gitterY; j++) {
                            if (X1 > i / (gitterX+1.5) && X2 < i / (gitterX+1.5) && Y1 > j / (gitterY+0.5) && Y2 < j / (gitterY+0.5)) {
                                if (mDragging) {
                                    if (dropable(counter, (int) i, (int) j)) {
                                        drop((int) ((i /(gitterX+1.5)) * mWidth + mChoiceX * mWidth / dpX + mTouchOffsetX),
                                                (int) (j / (gitterY+0.5) * mHeight + mChoiceY * mHeight / dpY + mTouchOffsetY));
                                        elementList.set(counter * 88 + (int) (j * 11) + (int) i, "y");
                                        setJoin(counter, (int) i, (int) j);
                                    }
                                    endDrag();
                                    break;
                                }
                            }
                        }
                    }
                }
        }
        return mDragging;
    }

    public boolean dispatchUnhandledMove(View focused, int direction) {
        return mMoveTarget != null && mMoveTarget.dispatchUnhandledMove(focused, direction);
    }

    /**
     * Call this from a drag source view.
     */
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mDragging) {
            return false;
        }

        final int action = ev.getAction();
        final int screenX = clamp((int)ev.getRawX(), 0, mDisplayMetrics.widthPixels);
        final int screenY = clamp((int)ev.getRawY(), 0, mDisplayMetrics.heightPixels);

        double i;
        double j;
        double X1;
        double X2;
        double Y1;
        double Y2;

        if(tablet == false) {
            X1 = (screenX - mTouchOffsetX) / mWidth - mChoiceX / dpX + 0.06;
            X2 = (screenX - mTouchOffsetX) / mWidth - mChoiceX / dpX - 0.06;
            Y1 = (screenY - mTouchOffsetY) / mHeight - mChoiceY / dpY + 0.06;
            Y2 = (screenY - mTouchOffsetY) / mHeight - mChoiceY / dpY - 0.06;
        }
        else {
            X1 = (screenX - mTouchOffsetX) *160/ mDisplayMetrics.densityDpi - mChoiceX*1.25 + 30*1.25;
            X2 = (screenX - mTouchOffsetX)*160/ mDisplayMetrics.densityDpi - mChoiceX*1.25 - 30*1.25;
            Y1 = (screenY - mTouchOffsetY) *160/ mDisplayMetrics.densityDpi - mChoiceY*1.25 + 30*1.25;
            Y2 = (screenY - mTouchOffsetY)*160/ mDisplayMetrics.densityDpi - mChoiceY*1.25 - 30*1.25;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Remember where the motion event started
                mMotionDownX = screenX;
                mMotionDownY = screenY;
                break;
            case MotionEvent.ACTION_MOVE:
                // Update the drag view.  Don't use the clamped pos here so the dragging looks
                // like it goes off screen a little, intead of bumping up against the edge.
                mDragView.move((int)ev.getRawX(), (int)ev.getRawY());
                // Drop on someone?
                if (tablet == false) {
                    for (i = 0; i < gitterX; i++) {
                        for (j = 0; j < gitterY; j++) {
                            if (X1 > i / (gitterX + 1.5) && X2 < i / (gitterX + 1.5) && Y1 > j / (gitterY + 0.5) && Y2 < j / (gitterY + 0.5)) {
                                if (dropable(counter, (int) i, (int) j)) {
                                    mDragView.move((int) ((i / (gitterX + 1.5)) * mWidth + mChoiceX * mWidth / dpX + mTouchOffsetX),
                                            (int) (j / (gitterY + 0.5) * mHeight + mChoiceY * mHeight / dpY + mTouchOffsetY));
                                }
                            }
                        }
                    }
                }
                else {
                    for (i = 0; i < gitterX; i++) {
                        for (j = 0; j < gitterY; j++) {
                            if(X1 > i *100 && X2 < i*100 && Y1>j*100 && Y2 <j*100 && X1/dpX<0.95 && Y1/dpY<0.95){
                                if (dropable(counter, (int) i, (int) j)) {
                                    mDragView.move((int) ((i*100)*mDisplayMetrics.densityDpi/160 + mChoiceX*mWidth / dpX + mTouchOffsetX),
                                            (int) ((j*100)*mDisplayMetrics.densityDpi/160 + mChoiceY*mHeight / dpY + mTouchOffsetY));
                                }
                            }
                        }
                    }
                }
                if(tablet == false &&(screenX)/mWidth>0.78 && (screenX)/mWidth < 0.9
                        && (screenY)/mHeight>-0.5 && (screenY)/mHeight<0.2){
                    mDragView.move((int)(0.78*mWidth + mTouchOffsetX),
                            (int)(0.05*mHeight + mTouchOffsetY));
                }
                else if(tablet == true && (screenX)/mWidth>0.9 && (screenX)/mWidth < 1.0
                        && (screenY)/mHeight>-0.8 && (screenY)/mHeight<0){
                    mDragView.move((int)(0.85*mWidth + mTouchOffsetX),
                            (int)(0.05*mHeight + mTouchOffsetY));
                }

                final int[] coordinates = mCoordinatesTemp;
                DropTarget dropTarget = findDropTarget(screenX, screenY, coordinates);
                if (dropTarget != null) {
                    if (mLastDropTarget == dropTarget) {
                        dropTarget.onDragOver(mDragSource, coordinates[0], coordinates[1],
                                (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                    } else {
                        if (mLastDropTarget != null) {
                            mLastDropTarget.onDragExit(mDragSource, coordinates[0], coordinates[1],
                                    (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                        }
                        dropTarget.onDragEnter(mDragSource, coordinates[0], coordinates[1],
                                (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                    }
                } else {
                    if (mLastDropTarget != null) {
                        mLastDropTarget.onDragExit(mDragSource, coordinates[0], coordinates[1],
                                (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                    }
                }
                mLastDropTarget = dropTarget;
                break;

            case MotionEvent.ACTION_UP:
                //Töten!
                if(tablet == false && (screenX)/mWidth>0.78 && (screenX)/mWidth < 0.9
                        && (screenY)/mHeight>-0.5 && (screenY)/mHeight<0.2){
                    mVibrator.vibrate(50);
                    if (mScreenX!=0) {
                        drop(0, 0);
                    }

                    imageView.setImageResource(0);
                    imageView.setVisibility(View.INVISIBLE);
                }
                else if(tablet == true && (screenX)/mWidth>0.9 && (screenX)/mWidth < 1.0
                        && (screenY)/mHeight>-0.8 && (screenY)/mHeight<0){
                    mVibrator.vibrate(50);
                    if (mScreenX!=0) {
                        drop(0, 0);
                    }

                    imageView.setImageResource(0);
                    imageView.setVisibility(View.INVISIBLE);
                }
                else if (mDragging) {
                    if(tablet==false) {
                        for (i = 0; i < gitterX; i++) {
                            for (j = 0; j < gitterY; j++) {
                                if (X1 > i / (gitterX + 1.5) && X2 < i / (gitterX + 1.5) && Y1 > j / (gitterY + 0.5) && Y2 < j / (gitterY + 0.5)) {
                                    if (mDragging) {
                                        if (dropable(counter, (int) i, (int) j)) {
                                            drop((int) ((i / (gitterX + 1.5)) * mWidth + mChoiceX * mWidth / dpX + mTouchOffsetX),
                                                    (int) (j / (gitterY + 0.5) * mHeight + mChoiceY * mHeight / dpY + mTouchOffsetY));
                                            elementList.set(counter * 88 + (int) (j * 11) + (int) i, "y");
                                            setJoin(counter, (int) i, (int) j);
                                        }
                                        endDrag();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else {
                        for (i = 0; i < gitterX; i++) {
                            for (j = 0; j < gitterY; j++) {
                                if(X1 > i * 100 && X2 < i * 100 && Y1 > j*100 && Y2 <j*100){
                                    if (dropable(counter, (int) i, (int) j)) {
                                        drop((int) ((i * 100) * mDisplayMetrics.densityDpi / 160 + mChoiceX * mWidth / dpX + mTouchOffsetX),
                                                (int) ((j * 100) * mDisplayMetrics.densityDpi / 160 + mChoiceY * mHeight / dpY + mTouchOffsetY));
                                        elementList.set(counter * 88 + (int) (j * 11) + (int) i, "y");
                                        setJoin(counter, (int) i, (int) j);
                                    }
                                }
                            }
                        }
                    }
                }
                // Drop on start
                else{
                    drop(mTouchOffsetX, mTouchOffsetY);
                }
                endDrag();

                break;
            case MotionEvent.ACTION_CANCEL:
                cancelDrag();
        }

        return true;
    }


    private boolean drop(float x, float y) {

        final int[] coordinates = mCoordinatesTemp;
        DropTarget dropTarget = findDropTarget((int) x, (int) y, coordinates);

        dropTarget.onDrop(mDragSource, coordinates[0], coordinates[1],
                (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
        mDragSource.onDropCompleted((View) dropTarget, true);

        return true;
    }


    private void recordScreenSize() {
        ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(mDisplayMetrics);
    }

    /**
     * Clamp val to be &gt;= min and &lt; max.
     */
    private static int clamp(int val, int min, int max) {
        if (val < min) {
            return min;
        } else if (val >= max) {
            return max - 1;
        } else {
            return val;
        }
    }

    private DropTarget findDropTarget(int x, int y, int[] dropCoordinates) {
        final Rect r = mRectTemp;

        final ArrayList<DropTarget> dropTargets = mDropTargets;
        final int count = dropTargets.size();
        for (int i=count-1; i>=0; i--) {
            final DropTarget target = dropTargets.get(i);
            target.getHitRect(r);
            target.getLocationOnScreen(dropCoordinates);
            r.offset(dropCoordinates[0] - target.getLeft(), dropCoordinates[1] - target.getTop());
            if (r.contains(x, y)) {
                dropCoordinates[0] = x - dropCoordinates[0];
                dropCoordinates[1] = y - dropCoordinates[1];
                return target;
            }
        }
        return null;
    }

    public void setDragListener(DragListener l) {
        mListener = l;
    }

    public void addDropTarget(DropTarget target) {
        mDropTargets.add(target);
    }

    /**
     * Remove a previously installed drag listener.
     */
    public void removeDragListener(DragListener l) {
        mListener = null;
    }

    private boolean dropable (int counter, int i, int j) {

        boolean allowDrop = true;

        if (elementList.get(counter * 88 + j * 11 + i).equals("y")) {
            allowDrop = false;
        }
        int k;
        // Keine 2 Lager
        if (counter < 6) {
            for (k = 0; k < 6; k++) {
                if (elementList.get(k * 88 + j * 11+ i) == "y") {
                    allowDrop = false;
                }
            }
        }
        // kein Stab-X und Ränder
        if (counter == 6 && (elementList.get(7*88 + j*11 +i+1)== "y" | i==5 | j==0)){
            allowDrop = false;
        }
        if (counter == 7 && elementList.get(6*88 + j*11 +i-1)== "y" | i==0 | j==0){
            allowDrop = false;
        }
        if (counter == 8 && i == 5){
            allowDrop = false;
        }
        if (counter == 9 && j == 0){
            allowDrop = false;
        }

        return allowDrop;
    }

    private void setJoin(int counter, int i, int j){
        if (counter> 5 && counter < 10) {
            switch (counter) {
                case 6:                           //               /
                    settingJoint(j - 1, i + 1);
                    settingJoint(j, i);
                    break;

                case 7:                           //               \
                    settingJoint(j - 1, i - 1);
                    settingJoint(j, i);
                    break;

                case 8:                           //               _
                    settingJoint(j, i + 1);
                    settingJoint(j, i);
                    break;

                case 9:                           //               /
                    settingJoint(j - 1, i);
                    settingJoint(j, i);
                    break;
            }

        }
    }

    private void settingJoint(int j, int i){
        joint = j*11+i;
        jointList.set(joint,"y");
    }

    private void deleteJoint(int i, int j){

        if (elementList.get(6 * 88 + j * 11 + i)!="y" && elementList.get(7 * 88 + j * 11 + i)!="y"
                && elementList.get(8 * 88 + j * 11 + i)!="y" && elementList.get(9 * 88 + j * 11 + i)!="y"
                && elementList.get(6 * 88 + (j+1) * 11 + i-1)!="y" && elementList.get(7 * 88 + (j+1) * 11 + i+1)!="y"
                && elementList.get(8 * 88 + j * 11 + i-1)!="y" && elementList.get(9 * 88 + (j+1) * 11 + i+1)!="y"){
            if (jointList.get(j*11+i)=="y") {
                jointList.set(j * 11 + i, "");
            }
        }

        int delta =0;

        switch (counter) {
            case 6:
                delta = -10;
                break;
            case 7:
                delta = -12;
                break;
            case 8:
                delta = 1;
                break;
            case 9:
                delta = -11;
                break;

        }
        if (elementList.get(6 * 88 + j * 11 + i + delta)!="y" && elementList.get(7 * 88 + j * 11 + i + delta)!="y"
                && elementList.get(8 * 88 + j * 11 + i + delta)!="y" && elementList.get(9 * 88 + j * 11 + i + delta)!="y"
                && elementList.get(6 * 88 + (j+1) * 11 + i-1 + delta)!="y" && elementList.get(7 * 88 + (j+1) * 11 + i+1 + delta)!="y"
                && elementList.get(8 * 88 + j * 11 + i-1 + delta)!="y" && elementList.get(9 * 88 + (j+1) * 11 + i+1)!="y" + delta){
            if (jointList.get(j*11+i +delta) == "y") {
                jointList.set(j * 11 + i + delta, "");
            }
        }
    }

}