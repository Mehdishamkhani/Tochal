package org.android.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import org.android.R;


public class TriangleView extends View {

    Paint mPaint;

    Path mPath;
    Direction direction=Direction.SOUTH;
    private ColorStateList mColor;

    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    public TriangleView(Context context) {
        super(context);
        create();
    }

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        hanldeAttributes(context, attrs);
        create();
    }

    private void hanldeAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyTriangle);
        int mDirection= a.getInt(R.styleable.MyTriangle_mDirection, 0);
        switch (mDirection){
            case 0:
                direction=Direction.NORTH;
                break;
            case 1:
                direction=Direction.SOUTH;
                break;
            case 2:
                direction=Direction.EAST;
                break;
            case 3:
                direction=Direction.WEST;
                break;
        }
         mColor = a.getColorStateList(R.styleable.MyTriangle_mColor);

    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }
    public void setDirection(Direction direction){
        this.direction=direction;
        invalidate();
    }

    private void create() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShadowLayer(2, 0, 0, Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor.getDefaultColor());


    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath = calculate(direction);
        canvas.drawPath(mPath, mPaint);

    }

    private Path calculate(Direction direction) {
        Point p1 = new Point();
        Point p2 =new Point(), p3 = new Point();
        Point p4 = new Point();Point p5 = new Point();
        int width = getWidth();
        int height=getHeight();

        if (direction == Direction.NORTH) {
            p1.x=width/2; p2.y=0;
            p2.x=0; p2.y=height;
            p3.x=width;p3.y=height;
        }else if (direction == Direction.SOUTH) {
            p1.x=0; p1.y=0;
            p2.x=width; p2.y=0;
            p3.x=0;p3.y=height-height/6;
            p4.x=width;p4.y=height-height/6;
            p5.x=width/2;p5.y=height;

        } else if (direction == Direction.EAST) {
            p1.x=0; p2.y=0;
            p2.x=0; p2.y=height;
            p3.x=width;p3.y=height/2;
        } else if (direction == Direction.WEST) {
            p1.x=0; p1.y=height/2;
            p2.x=width; p2.y=0;
            p3.x=width;p3.y=height;
        }

        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p3.x, p3.y);
        path.lineTo(p5.x, p5.y);
        path.lineTo(p4.x, p4.y);
        path.lineTo(p2.x, p2.y);

        return path;
    }
}