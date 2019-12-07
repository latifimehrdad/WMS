package com.example.wms.views.custom.circlemenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.wms.R;

public class MLCurveViewButton extends View {

    private final int CURVE_CIRCLE_RADIUS = 64;
    private Point mFirstCurveControlPoint1 = new Point();
    private Point mFirstCurveControlPoint2 = new Point();
    private Point mFirstCurveEndPoint = new Point();
    private Point mFirstCurveStartPoint = new Point();
    private int mNavigationBarHeight;
    private int mNavigationBarWidth;
    private Paint mPaint;
    private Path mPath;
    private Point mSecondCurveControlPoint1 = new Point();
    private Point mSecondCurveControlPoint2 = new Point();
    private Point mSecondCurveEndPoint = new Point();
    private Point mSecondCurveStartPoint = new Point();

    public MLCurveViewButton(Context context) {
        super(context);
        init();
    }

    public MLCurveViewButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(getContext().getResources().getColor(R.color.mlBlack));
        setBackgroundColor(0);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mNavigationBarWidth = getWidth();
        mNavigationBarHeight = getHeight();
        mFirstCurveStartPoint.set((((mNavigationBarWidth / 2) - 128) + 32) - 12, 0);
        mFirstCurveEndPoint.set(mNavigationBarWidth / 2, 55);
        mSecondCurveStartPoint = mFirstCurveEndPoint;
        mSecondCurveEndPoint.set((((mNavigationBarWidth / 2) + 128) - 32) + 12, 0);
        mFirstCurveControlPoint1.set(mFirstCurveStartPoint.x + 64 + 16, mFirstCurveStartPoint.y);
        mFirstCurveControlPoint2.set((mFirstCurveEndPoint.x - 128) + 64, mFirstCurveEndPoint.y);
        mSecondCurveControlPoint1.set((mSecondCurveStartPoint.x + 128) - 64, mSecondCurveStartPoint.y);
        mSecondCurveControlPoint2.set(mSecondCurveEndPoint.x - 80, mSecondCurveEndPoint.y);
        mPath.reset();
        mPath.moveTo(0.0f, 0.0f);
        mPath.lineTo((float) mFirstCurveStartPoint.x, (float) mFirstCurveStartPoint.y);
        mPath.cubicTo((float) mFirstCurveControlPoint1.x, (float) mFirstCurveControlPoint1.y, (float) mFirstCurveControlPoint2.x, (float) mFirstCurveControlPoint2.y, (float) mFirstCurveEndPoint.x, (float) mFirstCurveEndPoint.y);
        mPath.cubicTo((float) mSecondCurveControlPoint1.x, (float) mSecondCurveControlPoint1.y, (float) mSecondCurveControlPoint2.x, (float) mSecondCurveControlPoint2.y, (float) mSecondCurveEndPoint.x, (float) mSecondCurveEndPoint.y);
        mPath.lineTo((float) mNavigationBarWidth, 0.0f);
        mPath.lineTo((float) mNavigationBarWidth, (float) mNavigationBarHeight);
        mPath.lineTo(0.0f, (float) mNavigationBarHeight);
        mPath.close();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                final float x = ev.getX();
                final float y = ev.getY();
                Toast.makeText(this.getContext(),"Touch",Toast.LENGTH_SHORT).show();
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:

                break;


            case MotionEvent.ACTION_CANCEL:
                break;


            case MotionEvent.ACTION_POINTER_UP:


                break;
        }

        return true;
    }


}