package com.ngra.wms.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.ngra.wms.R;


public class MLCurveHeader extends View {

    private Paint DrawPaint;
    private Paint DrawPaint2;
    private Path mPath;
    private Path pathShadow;
    private int PitOpenings;
    private int backColor;
    private int shadowColor;
    private int shadow;

    public MLCurveHeader(Context context) {
        super(context);
        init();
    }

    public MLCurveHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MLCurveHeader);

        backColor = ta.getColor(R.styleable.MLCurveHeader_backcolor,Color.WHITE);
        shadowColor = ta.getColor(R.styleable.MLCurveHeader_shadowcolor,Color.argb(60,0,0,0));
        shadow = ta.getInt(R.styleable.MLCurveHeader_shadow,3);
        PitOpenings = ta.getInt(R.styleable.MLCurveHeader_PitOpenings, 10);

        init();
    }


    private void init() {
        mPath = new Path();
        pathShadow = new Path();
        DrawPaint = new Paint();
        DrawPaint.setColor(backColor);

        DrawPaint2 = new Paint();
        //DrawPaint2.setColor(shadowColor);



        setBackgroundColor(0);
    }


    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int width = getWidth();
        int height = getHeight();
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        DrawPaint2.setShader(new LinearGradient(0,halfHeight,0, height - 5,shadowColor,Color.argb(10,0,0,0),Shader.TileMode.CLAMP));

        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(0,halfHeight + (halfHeight / 3) - shadow);

        mPath.lineTo(PitOpenings, halfHeight + (halfHeight / 3) - shadow);

        Point mFirstCurveControlPoint1 = new Point();
        Point mFirstCurveControlPoint2 = new Point();

        mFirstCurveControlPoint1.set(halfWidth / 3 + PitOpenings,halfHeight + (halfHeight / 3) - shadow);
        mFirstCurveControlPoint2.set((halfWidth / 3) + (halfWidth / 5), height - shadow);
        mPath.cubicTo(mFirstCurveControlPoint1.x, mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x, mFirstCurveControlPoint2.y,
                halfWidth + 10, height - shadow);

        Point mSecondCurveControlPoint1 = new Point();
        Point mSecondCurveControlPoint2 = new Point();

        mSecondCurveControlPoint1.set(width - ((halfWidth / 3) + (halfWidth / 5)) + 10, height - shadow);
        mSecondCurveControlPoint2.set(width - halfWidth / 3 + 10 - PitOpenings, halfHeight + (halfHeight / 3) - shadow);

        mPath.cubicTo(mSecondCurveControlPoint1.x, mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x, mSecondCurveControlPoint2.y,
                width - PitOpenings, halfHeight + (halfHeight / 3) - shadow);

        mPath.lineTo(width, halfHeight + (halfHeight / 3) - shadow);

        mPath.lineTo(width,0);
        mPath.lineTo(0,0);
        mPath.close();

//_____________________________________________________________________________________


        Point mFirstCurveShadowPoint1 = new Point();
        Point mFirstCurveShadowPoint2 = new Point();


        pathShadow.moveTo(0,halfHeight + (halfHeight / 3));
        pathShadow.lineTo(PitOpenings, halfHeight + (halfHeight / 3));

        mFirstCurveShadowPoint1.set(halfWidth / 3 + PitOpenings,halfHeight + (halfHeight / 3));
        mFirstCurveShadowPoint2.set((halfWidth / 3) + (halfWidth / 5), height);
        pathShadow.cubicTo(mFirstCurveShadowPoint1.x, mFirstCurveShadowPoint1.y,
                mFirstCurveShadowPoint2.x, mFirstCurveShadowPoint2.y,
                halfWidth + 10, height);

        Point mSecondCurveShadowPoint1 = new Point();
        Point mSecondCurveShadowPoint2 = new Point();

        mSecondCurveShadowPoint1.set(width - ((halfWidth / 3) + (halfWidth / 5)) + 10, height);
        mSecondCurveShadowPoint2.set(width - halfWidth / 3 + 10 - PitOpenings, halfHeight + (halfHeight / 3));

        pathShadow.cubicTo(mSecondCurveShadowPoint1.x, mSecondCurveShadowPoint1.y,
                mSecondCurveShadowPoint2.x, mSecondCurveShadowPoint2.y,
                width - PitOpenings, halfHeight + (halfHeight / 3));

        pathShadow.lineTo(width, halfHeight + (halfHeight / 3));

        pathShadow.lineTo(width,0);
        pathShadow.lineTo(0,0);
        pathShadow.close();

        //___________________________________________________________________________________________

        pathShadow.close();


    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawPath(pathShadow,DrawPaint2);
        canvas.drawPath(mPath, DrawPaint);

    }



}
