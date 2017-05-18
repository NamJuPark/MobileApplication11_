package com.example.mobileapplication11_;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 박남주 on 2017-05-18.
 */

public class MyPainter extends View {
    Bitmap mBitmap;//그려질 그림 넣을...
    Canvas mCanvas;
    Paint mPaint = new Paint();
    public MyPainter(Context context) {
        super(context);
    }

    public MyPainter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);

    }
    private void drawStamp(){
        Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        mCanvas.drawBitmap(img,10,10,mPaint);
    }
    private void Rotate(){
        mCanvas.rotate(45, this.getWidth()/2, getHeight()/2);
    }
    public void Eraser(){
        mBitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public boolean Save(String file_name) {
        try {
            FileOutputStream out = new FileOutputStream(file_name);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return false;
    }
    float startX = -1, startY = -1, stopX = -1, stopY = -1;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();//실제 그려서 보여질 곳
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(startX, startY, stopX, stopY, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == event.ACTION_DOWN) {
            startX = event.getX();
            startY = event.getY();
        }
        else if (event.getAction() == event.ACTION_UP) {
            stopX = event.getX();
            stopY = event.getY();
            invalidate();
        }
        return true;
    }
}
