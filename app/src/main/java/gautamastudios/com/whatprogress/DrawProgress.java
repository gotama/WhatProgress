package gautamastudios.com.whatprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DrawProgress extends View {
    private final static int fat = 15;
    private final static int two = 2;

    private int length;
    private int wrapWidth;
    private int wrapHeight;
    private int rectStartLine;
    private int rectEndLine;
    private int indicators;
    private int progress;
    private Paint progressPaint;

    private boolean isHorizontal;
    private int primaryColor;
    private int secondaryColor;
    private int tertiaryColor;

    private RectF tOval;
    private RectF tRect;

    public DrawProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setAntiAlias(true);

        tOval = new RectF();
        tRect = new RectF();

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DrawProgressBar, 0, 0);
        try {
            isHorizontal = typedArray.getBoolean(R.styleable.DrawProgressBar_isHorizontal, true);
            primaryColor = typedArray.getColor(R.styleable.DrawProgressBar_primaryColor, Color.BLACK);
            secondaryColor = typedArray.getColor(R.styleable.DrawProgressBar_secondaryColor, Color.BLACK);
            tertiaryColor = typedArray.getColor(R.styleable.DrawProgressBar_tertiaryColor, Color.BLACK);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * Here we measure to figure out the wrapWidth
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (isHorizontal) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            length = widthSize / indicators;
            wrapHeight = length;
            wrapWidth = length * indicators;
            wrapWidth = wrapWidth - length;//length to cut off the last tile
            rectStartLine = length / 3;
            rectEndLine = rectStartLine * two;
            setMeasuredDimension(Math.min(wrapWidth, widthSize), wrapHeight);
        } else {
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            length = heightSize / indicators;
            wrapWidth = length;
            wrapHeight = length * indicators;
            wrapHeight = wrapHeight - length;
            rectStartLine = length / 3;
            rectEndLine = rectStartLine * two;
            setMeasuredDimension(wrapWidth, Math.min(wrapHeight, heightSize));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int offset = isHorizontal ? wrapWidth : wrapHeight;
        for (int i = indicators - two; i >= 0; i--) {

            if (i == progress) {

                if ((i % two) == 0) {
                    drawOval(canvas, offset, true);
                    drawCurrentOval(canvas, offset - length, offset);
                } else {
                    drawRect(canvas, offset, i == progress);
                }
            } else {

                if ((i % two) == 0) {
                    drawOval(canvas, offset, (progress != 0 && i <= progress));
                } else {
                    drawRect(canvas, offset, (progress != 0 && i <= progress));
                }
            }

            offset = offset - length;
        }
    }

    private void drawCurrentOval(Canvas canvas, int outerLeft, int offset) {
        progressPaint.setColor(tertiaryColor);
        int innerCalibratedOffsetA = (outerLeft == 0) ? length / 3 : outerLeft + (length / 3);
        int innerCalibratedOffsetB = (outerLeft == 0) ? innerCalibratedOffsetA * two : offset + (outerLeft - innerCalibratedOffsetA);
        if (isHorizontal) {
            tOval.set(innerCalibratedOffsetA, rectStartLine, innerCalibratedOffsetB, rectEndLine);
        } else {
            tOval.set(rectStartLine, innerCalibratedOffsetA, rectEndLine, innerCalibratedOffsetB);
        }
        canvas.drawOval(tOval, progressPaint);
    }

    private void drawOval(Canvas canvas, int offset, boolean isSecondaryColor) {
        progressPaint.setColor(isSecondaryColor ? secondaryColor : primaryColor);
        int calibratedOffset = offset - length;
        if (isHorizontal) {
            tOval.set(calibratedOffset, 0, offset, length);
        } else {
            tOval.set(0, calibratedOffset, length, offset);
        }
        canvas.drawOval(tOval, progressPaint);
    }

    private void drawRect(Canvas canvas, int offset, boolean isSecondaryColor) {
        progressPaint.setColor(isSecondaryColor ? secondaryColor : primaryColor);
        int calibratedOffset = (offset != 0) ? offset - length : offset + length;
        if (isHorizontal) {
            tRect.set(calibratedOffset - fat, rectStartLine, offset + fat, rectEndLine);
        } else {
            tRect.set(rectStartLine, calibratedOffset - fat, rectEndLine, offset + fat);
        }
        canvas.drawRect(tRect, progressPaint);
    }

    public void setTotalIndicators(int indicators) {
        this.indicators = indicators * two;
        invalidate();
    }

    public void incrementProgress() {
        if (progress < (indicators - two)) {
            this.progress = progress + two;
            invalidate();
        }
    }

    public void decrementProgress() {
        if (progress != 0) {
            this.progress = progress - two;
            invalidate();
        }
    }
}
