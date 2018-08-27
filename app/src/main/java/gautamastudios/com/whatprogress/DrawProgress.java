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

    private int indicators;
    private int progress;

    //CustomFields
    private float barHeight;
    private int primaryColor;
    private int secondaryColor;
    private int indicatorHeight;
    private int indicatorWidth;

    private Paint progressPaint;
    private RectF unitIndicator;

    public DrawProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        unitIndicator = new RectF();


        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DrawProgressBar, 0, 0);
        try {
            barHeight = typedArray.getDimensionPixelSize(R.styleable.DrawProgressBar_barHeight, -1);
            primaryColor = typedArray.getColor(R.styleable.DrawProgressBar_primaryColor, Color.BLACK);
            secondaryColor = typedArray.getColor(R.styleable.DrawProgressBar_secondaryColor, Color.BLACK);
            indicatorHeight = typedArray.getDimensionPixelSize(R.styleable.DrawProgressBar_indicatorHeight, -1);
            indicatorWidth = typedArray.getDimensionPixelSize(R.styleable.DrawProgressBar_indicatorWidth, -1);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw Unit indicators
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setAntiAlias(true);
        int width = getWidth() / indicators;
        int spaceBetweenUnit = 0;
        for (int i = 0; i < indicators; i++) {
            unitIndicator.set(spaceBetweenUnit, 0, indicatorWidth + spaceBetweenUnit, indicatorHeight);
            canvas.drawOval(unitIndicator, progressPaint);

            spaceBetweenUnit += width;
        }
        int totalWidth = indicatorWidth + spaceBetweenUnit - width;
        int halfHeight = getHeight() / 2;
//        int progressEndX = (int) (getWidth() * progress / 100f);
//        int barWidth = (int) (getWidth() * units / 100f);
//
        // draw the part of the bar that's filled
//        progressPaint.setStrokeWidth(barHeight);
//        progressPaint.setColor(secondaryColor);
//        canvas.drawLine(0, halfHeight, progressEndX, halfHeight, progressPaint);
//
//        // draw the unfilled section
        progressPaint.setColor(primaryColor);
        canvas.drawLine(0, halfHeight, totalWidth, halfHeight, progressPaint);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height;
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(indicatorHeight, heightSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                height = indicatorHeight;
                break;
            default:
                height = indicatorHeight;
                break;
        }

        setMeasuredDimension(width, height);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        updateGoalReached();
        invalidate();
    }

    public void setTotalIndicators(int indicators) {
        this.indicators = indicators;

        updateGoalReached();
        invalidate();
    }

    private void updateGoalReached() {
//        isGoalReached = progress >= units;
    }
}
