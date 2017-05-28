package com.example.mobileapplication11_;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by 박남주 on 2017-05-18.
 */

public class MyPainter extends View {
    Bitmap mBitmap;//그려질 그림 넣을...
    Canvas mCanvas;
    Paint mPaint = new Paint();//그림그릴
    boolean checkbox, blur, color;
    int X, Y;

    public MyPainter(Context context) {
        super(context);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public MyPainter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void setCheckbox(boolean check) {
        this.checkbox = check;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);
        mPaint.setStrokeWidth(3);


    }

    private void drawStamp() {
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mCanvas.drawBitmap(img, X, Y, mPaint);
    }

    public void Rotate() {
        mCanvas.rotate(30, mCanvas.getWidth() / 2, mCanvas.getHeight() / 2);
        invalidate();
    }

    public void Trans() {
        mCanvas.translate(10, 10);
        invalidate();
    }

    public void Scale() {
        mCanvas.scale(1.5f, 1.5f);
        invalidate();
    }

    public void Skew() {
        mCanvas.skew(0.2f, 0);
        invalidate();
    }

    public void Eraser() {
        mBitmap.eraseColor(Color.YELLOW);
        invalidate();
    }

    public boolean Save(String file_name) {
        try {
            FileOutputStream out = new FileOutputStream(file_name);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Toast.makeText(getContext(),"저장됨",Toast.LENGTH_SHORT).show();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return false;
    }

    public void Open(String filename) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                Eraser();
                mCanvas.scale(0.5f, 0.5f);
                mCanvas.drawBitmap(BitmapFactory.decodeFile(filename),mCanvas.getWidth()/2,mCanvas.getHeight()/2,mPaint);
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void Blur(boolean check) {
        if (check == true) {
            blur = true;
            if (checkbox == true) {
                BlurMaskFilter blurMaskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER);
                mPaint.setMaskFilter(blurMaskFilter);
            } else {
                blur = false;
                mPaint.setMaskFilter(null);
            }
        } else {
            blur = false;
            mPaint.setMaskFilter(null);
        }
        invalidate();

    }

    public void Color(boolean check) {
        if (check) {
            color = true;
            if (checkbox) {
                float[] matrixarray = {
                        2f, 0f, 0f, 0f, -15f,
                        0f, 2f, 0f, 0f, -15f,
                        0f, 0f, 2f, 0f, -15f,
                        0f, 0f, 0f, 1f, 0f,
                };
                ColorMatrix matrix = new ColorMatrix(matrixarray);
                mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
            } else {
                color = false;
                mPaint.setColorFilter(null);
            }
        } else {
            color = false;
            mPaint.setColorFilter(null);
        }
        invalidate();
    }

    public void setPenWidth(int width) { // seunghonice@hanyang.ac.kr
        if (width == 5) {
            mPaint.setStrokeWidth(width);
        } else {
            mPaint.setStrokeWidth(width);
        }
        invalidate();
    }

    public void setPenColor(String str) {
        if (str.equals("red")) mPaint.setColor(Color.RED);
        else mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
    }

    int oldX = -1, oldY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        X = (int) event.getX();
        Y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {//누를때
            oldX = X;
            oldY = Y;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {//누르고 움직일 때
            if (oldX != -1) {
                if (checkbox == false) {
                    mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                    invalidate();//onDraw부르기
                    oldX = X;
                    oldY = Y;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {//땔 때
            if (oldX != -1) {
                if (checkbox == false) {//stamp 꺼져 있을 때
                    mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                } else {
                    drawStamp();
                }
                invalidate();
            }
            oldX = -1;
            oldY = -1;
        }
        return true;

    }
}
