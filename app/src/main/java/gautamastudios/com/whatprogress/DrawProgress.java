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
    private final static int fat = 10;
    private final static int two = 2;

    private int length;
    private int wrapWidth;
    private int rectTopLine;
    private int rectBottomLine;
    private int indicators;
    private int progress;
    private Paint progressPaint;

    private int primaryColor;
    private int secondaryColor;
    private int tertiaryColor;

    //TODO Investigate worth in optimizing with Map?
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

        int width;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        length = widthSize / indicators;
        wrapWidth = length * indicators;
        wrapWidth = wrapWidth - length;//length to cut off the last tile

        rectTopLine = length / 3;
        rectBottomLine = rectTopLine * two;

        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(wrapWidth, widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                width = wrapWidth;
                break;
            default:
                width = wrapWidth;
                break;
        }

        //TODO Measure textview into length

        setMeasuredDimension(width, length);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //TODO Improve UI
//        Drawable d = getResources().getDrawable(R.drawable.foobar);
//        d.setBounds(left, top, right, bottom);
//        d.draw(canvas);

        int offset = wrapWidth;
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
        int innerLeft = (outerLeft == 0) ? length / 3 : outerLeft + (length / 3);
        int innerRight = (outerLeft == 0) ? innerLeft * two : offset + (outerLeft - innerLeft);

        tOval.set(innerLeft, rectTopLine, innerRight, rectBottomLine);
        canvas.drawOval(tOval, progressPaint);
    }

    private void drawOval(Canvas canvas, int offset, boolean isSecondaryColor) {
        progressPaint.setColor(isSecondaryColor ? secondaryColor : primaryColor);
        int left = offset - length;
        tOval.set(left, 0, offset, length);
        canvas.drawOval(tOval, progressPaint);
    }

    private void drawRect(Canvas canvas, int offset, boolean isSecondaryColor) {
        progressPaint.setColor(isSecondaryColor ? secondaryColor : primaryColor);
        int left = (offset != 0) ? offset - length : offset + length;
        tRect.set(left - fat, rectTopLine, offset + fat, rectBottomLine);
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
