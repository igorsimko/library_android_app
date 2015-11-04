package com.example.bcapp.view;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import com.example.bcapp.app.R;

/**
 * Created by Igor on 23.10.2015.
 */
public class MapHolder extends View {

    private float dpi = 0;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    boolean visible = false;

    private int x = 0;
    private int y = 0;

    private int image_width = 0;
    private int image_height = 0;

    private Canvas mCanvas = new Canvas();


    public MapHolder(Context c) {
        super(c);

        mScaleDetector = new ScaleGestureDetector(c, new ScaleListener());
    }

    public MapHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public MapHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (!visible) {
            Drawable d = getResources().getDrawable(R.drawable.room);

            d.setBounds(0, 0, Math.round(image_width * dpi), Math.round(image_height * dpi));
            d.draw(canvas);
        }

        canvas.scale(mScaleFactor, mScaleFactor);

        // canvas.drawColor(Color.BLACK);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        /* refresh */
        Paint paint1 = new Paint();
        paint1.setColor(Color.BLUE);
        /* refresh */

        canvas.drawCircle(x, y, 50, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 2000;
        int desiredHeight = 900;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);

    }


/*    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        return true;
    }*/

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();

            visible = false;
            return true;
        }
    }


    public void drawPosition(int x, int y) {
        this.x = x;
        this.y = y;

        draw(mCanvas);
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }

    public void setDpi(float dpi) {
        this.dpi = dpi;
    }
}
