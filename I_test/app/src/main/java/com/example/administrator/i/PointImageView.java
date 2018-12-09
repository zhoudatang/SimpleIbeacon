package com.example.administrator.i;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class PointImageView extends AppCompatImageView {

    public PointImageView(Context context) {
        super(context);
    }

    public PointImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PointImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Point mPoint = null;

    private Paint mPointPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPointPaint.setColor(Color.RED);

        if (mPoint != null) {
            canvas.drawCircle(mPoint.getX(), mPoint.getY(), mPoint.getRadius(), mPointPaint);
        }
    }

    public void drawPoint(Point p) {
        mPoint = p;
        invalidate();
    }
}
