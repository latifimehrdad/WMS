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

    private Canvas canvas;
    private Paint drawPaint;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        drawPaint = new Paint();
        drawPaint.setColor(Color.GREEN);
        drawTriangle();
    }


    public void drawTriangle() {
        Path path;
        path = new Path();
        path.moveTo(0,160);
        path.lineTo(140, 200);
        path.lineTo(200,320);
        path.cubicTo(200,320,320,200,500,300);
        path.lineTo(620, 260);
        path.lineTo(640,100);
        path.cubicTo(640,100, 320,-160,0,160);
        path.close();
        canvas.save();
        canvas.rotate(180,430,500);
        canvas.drawPath(path, drawPaint);

        drawPaint.setColor(Color.RED);
        Path path1 = new Path();
        path1.moveTo(0,160);
        path1.lineTo(140, 200);
        path1.lineTo(200,320);
        path1.cubicTo(200,320,320,200,500,300);
        path1.lineTo(620, 260);
        path1.lineTo(640,100);
        path1.cubicTo(640,100, 320,-160,0,160);
        path1.close();
        canvas.save();
        canvas.rotate(90,350,500);
        canvas.drawPath(path1, drawPaint);

        drawPaint.setColor(Color.BLUE);
        Path path3 = new Path();
        path3.moveTo(0,160);
        path3.lineTo(140, 200);
        path3.lineTo(200,320);
        path3.cubicTo(200,320,320,200,500,300);
        path3.lineTo(620, 260);
        path3.lineTo(640,100);
        path3.cubicTo(640,100, 320,-160,0,160);
        path3.close();
        canvas.save();
        canvas.rotate(90,350,500);
        canvas.drawPath(path3, drawPaint);

        drawPaint.setColor(Color.YELLOW);
        Path path4 = new Path();
        path4.moveTo(0,160);
        path4.lineTo(140, 200);
        path4.lineTo(200,320);
        path4.cubicTo(200,320,320,200,500,300);
        path4.lineTo(620, 260);
        path4.lineTo(640,100);
        path4.cubicTo(640,100, 320,-160,0,160);
        path4.close();
        canvas.save();
        canvas.rotate(90,350,500);
        canvas.drawPath(path4, drawPaint);

    }


}