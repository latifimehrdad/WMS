package com.ngra.wms.game.mistake;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.ngra.wms.R;
import com.ngra.wms.game.controls.GameObject;

import java.util.Random;

import static com.ngra.wms.game.controls.GamePanel.MOVESPEED;


public class Mistake_Banana extends GameObject {


    private int speed;
    private Bitmap spritesheet;
//    private float scaleFactorX;
//    private float scaleFactorY;

    public Mistake_Banana(Resources resources,
                         int x,
                         int y,
                         int w,
                         int h,
                         int s,
                         int HalfDeviceWidth,
                         int DeviceWidth,
                         int DeviceHeight,
                          int sc) {

        score = sc;
//        scaleFactorX = DeviceWidth / (WIDHT * 1.f);
//        scaleFactorY = DeviceHeight / (HEIGHT * 1.f);

        int wTemp = HalfDeviceWidth * 2;
        double temp = (wTemp  * 5) / 100;
        int left = (int) Math.round(temp);
        int right = wTemp - left;

        if (left >= x) {
            x = left;
        }

//        int NewW = w;
//        if(scaleFactorX < scaleFactorY)
//            NewW = Math.round(w * scaleFactorY);

        if (right <= x + w) {
            x = right - w - 10;
        }

        super.x = x;
        super.y = y;
        width = w;
        height = h;
        scoreForSpeed = s;

        spritesheet = BitmapFactory.decodeResource(
                resources
                , R.drawable.mistake_banana_fish);

        int min = 0;
        int max = 3;
        int random = new Random().nextInt((max - min) + 1) + min;
        //speed = 2 + (score / 500) + random;
        speed = 2 + (MOVESPEED / 12) + random + (MOVESPEED / 20);

    }


    public void update() {

        y += speed;

    }


    public void Collection() {

        y = (y / 2);

    }

    public void draw(Canvas canvas) {
        try {
//            Bitmap img = spritesheet;
//            Bitmap resize = null;
//            if(scaleFactorX > scaleFactorY) {
//                float dScale = scaleFactorX - scaleFactorY;
//                int hImg = img.getHeight() + Math.round(img.getHeight() * dScale);
//                resize = Bitmap.createScaledBitmap(img, (int) (img.getWidth()), hImg, true);
//            }
//            else {
//                float dScale = scaleFactorY - scaleFactorX;
//                int wImg = img.getWidth() + Math.round(img.getWidth() * dScale);
//                resize = Bitmap.createScaledBitmap(img, wImg, (int) (img.getHeight()), true);
//            }
            canvas.drawBitmap(spritesheet, x, y, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getWidth() {
        return height - 10;
    }


}