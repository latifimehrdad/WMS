package com.example.wms.views.custom.circlemenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.wms.R;


public class CustomView extends View {
    private Path path;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        init();
    }


    private void init() {
        path = new Path();
        setBackgroundColor(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTriangle();
        Paint drawPaint = new Paint();
        drawPaint.setColor(Color.GREEN);
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawPath(path, drawPaint);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Touching",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void drawTriangle() {
        path.moveTo(0,80);
        path.lineTo(70, 100);
        path.lineTo(100,160);
        path.cubicTo(100,160,160,100,250,150);
        path.lineTo(310, 130);
        path.lineTo(320,50);
        path.cubicTo(320,50, 160,-80,0,80);
        path.close();
//        canvas.save();
//        canvas.rotate(180,430,500);
//        canvas.drawPath(path, drawPaint);

//        drawPaint.setColor(Color.RED);
//        Path path1 = new Path();
//        path1.moveTo(0,160);
//        path1.lineTo(140, 200);
//        path1.lineTo(200,320);
//        path1.cubicTo(200,320,320,200,500,300);
//        path1.lineTo(620, 260);
//        path1.lineTo(640,100);
//        path1.cubicTo(640,100, 320,-160,0,160);
//        path1.close();
//        canvas.save();
//        canvas.rotate(90,350,500);
//        canvas.drawPath(path1, drawPaint);
//
//        drawPaint.setColor(Color.BLUE);
//        Path path3 = new Path();
//        path3.moveTo(0,160);
//        path3.lineTo(140, 200);
//        path3.lineTo(200,320);
//        path3.cubicTo(200,320,320,200,500,300);
//        path3.lineTo(620, 260);
//        path3.lineTo(640,100);
//        path3.cubicTo(640,100, 320,-160,0,160);
//        path3.close();
//        canvas.save();
//        canvas.rotate(90,350,500);
//        canvas.drawPath(path3, drawPaint);
//
//        drawPaint.setColor(Color.YELLOW);
//        Path path4 = new Path();
//        path4.moveTo(0,160);
//        path4.lineTo(140, 200);
//        path4.lineTo(200,320);
//        path4.cubicTo(200,320,320,200,500,300);
//        path4.lineTo(620, 260);
//        path4.lineTo(640,100);
//        path4.cubicTo(640,100, 320,-160,0,160);
//        path4.close();
//        canvas.save();
//        canvas.rotate(90,350,500);
//        canvas.drawPath(path4, drawPaint);

    }


}