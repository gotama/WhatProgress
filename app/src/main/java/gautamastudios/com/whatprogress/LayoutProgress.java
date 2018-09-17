package gautamastudios.com.whatprogress;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LayoutProgress extends RelativeLayout {

    private static final int NO_RULE = -1, PARENT_LAYOUT = 0, CURRENT_INDEX = 1,
            PROGRESS_CONTAINER = 2, GREY_CIRCLE = 3, GREEN_CIRCLE = 4, CURRENT_CIRCLE = 5,
            DOTTED_BAR = 6, SOLID_BAR = 7;

    private boolean isHorizontal;
    private int primaryColor;
    private int secondaryColor;
    private int tertiaryColor;

    private List<Object> items;

    public LayoutProgress(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

//        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.LayoutProgressBar, 0, 0);
//        try {
//            isHorizontal = typedArray.getBoolean(R.styleable.LayoutProgressBar_test1, true);
//            primaryColor = typedArray.getColor(R.styleable.LayoutProgressBar_test2, Color.BLACK);
//            secondaryColor = typedArray.getColor(R.styleable.LayoutProgressBar_test3, Color.BLACK);
//            tertiaryColor = typedArray.getColor(R.styleable.LayoutProgressBar_test4, Color.BLACK);
//        } finally {
//            typedArray.recycle();
//        }

        items = new ArrayList<>();
        items.add(new Object());
        items.add(new Object());
        items.add(new Object());
        items.add(new Object());
        items.add(new Object());
        items.add(new Object());
        init();
    }

    int height = 530;
    int width = 60;

    private void init() {

        TextView currentIndex = new TextView(getContext());
        currentIndex.setId(View.generateViewId());
        currentIndex.setLayoutParams(generateLayoutParams(CURRENT_INDEX, NO_RULE));
        currentIndex.setBackground(generateDrawable(CURRENT_INDEX));
        currentIndex = buildTextView(currentIndex, Typeface.create("sans-serif-condensed", Typeface.NORMAL), Typeface.NORMAL, 24,
                Color.WHITE, Gravity.CENTER);
        currentIndex.setText("2.3"); //TODO set to binding

        //TODO
        //Progress Container
        RelativeLayout progressContainer = new RelativeLayout(getContext());
//        progressContainer.setOrientation(LinearLayout.VERTICAL);
        progressContainer.setLayoutParams(generateLayoutParams(PROGRESS_CONTAINER, currentIndex.getId()));

        //Grey Circle 20x20
        View greyCircle = new View(getContext());
        greyCircle.setId(View.generateViewId());
        greyCircle.setLayoutParams(generateLayoutParams(GREY_CIRCLE, NO_RULE));
        greyCircle.setBackground(generateDrawable(GREY_CIRCLE));

        //Green Circle 20x20
        View greenCircle = new View(getContext());
        greenCircle.setId(View.generateViewId());
        greenCircle.setLayoutParams(generateLayoutParams(GREEN_CIRCLE, greyCircle.getId()));
        greenCircle.setBackground(generateDrawable(GREEN_CIRCLE));

        //Current Circle 26x26
        View currentCircle = new View(getContext());
        currentCircle.setId(View.generateViewId());
        currentCircle.setLayoutParams(generateLayoutParams(CURRENT_CIRCLE, greenCircle.getId()));
        currentCircle.setBackground(generateDrawable(CURRENT_CIRCLE));

        //Dotted Bar
        View dottedBar = new View(getContext());
        dottedBar.setId(View.generateViewId());
        dottedBar.setLayoutParams(generateLayoutParams(DOTTED_BAR, currentCircle.getId()));
        dottedBar.setBackground(generateDrawable(DOTTED_BAR));

        //Green Bar
        View solidBar = new View(getContext());
        solidBar.setId(View.generateViewId());
        solidBar.setLayoutParams(generateLayoutParams(SOLID_BAR, dottedBar.getId()));
        solidBar.setBackground(generateDrawable(SOLID_BAR));

        //First draw all grey based on item size
        View circle;
        View bar = null;
        int count = 0;
        for (Object object : items) {

            count++;

            circle = new View(getContext());
            circle.setId(View.generateViewId());
            circle.setLayoutParams(generateLayoutParams(GREY_CIRCLE, bar != null ? bar.getId() : NO_RULE));
            circle.setBackground(generateDrawable(GREY_CIRCLE));
            progressContainer.addView(circle);

            if (count == items.size()) break;

            bar = new View(getContext());
            bar.setId(View.generateViewId());
            bar.setLayoutParams(generateLayoutParams(DOTTED_BAR, circle.getId()));
            bar.setBackground(generateDrawable(DOTTED_BAR));
            progressContainer.addView(bar);
        }

//        progressContainer.addView(greyCircle);
//        progressContainer.addView(greenCircle);
//        progressContainer.addView(currentCircle);
//        progressContainer.addView(dottedBar);
//        progressContainer.addView(solidBar);


        RelativeLayout parentContainer = new RelativeLayout(getContext());
        parentContainer.setLayoutParams(generateLayoutParams(PARENT_LAYOUT, NO_RULE));
        parentContainer.addView(currentIndex);
        parentContainer.addView(progressContainer);

        this.addView(parentContainer);
    }

    private LayoutParams generateLayoutParams(int layoutType, int viewIdForRule) {
        switch (layoutType) {
            case PARENT_LAYOUT:
                RelativeLayout.LayoutParams parentLayout = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parentLayout.height = dpToPx(height);
                parentLayout.width = dpToPx(width);
                return parentLayout;
            case CURRENT_INDEX:
                RelativeLayout.LayoutParams currentIndexTextView = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                currentIndexTextView.addRule(ALIGN_PARENT_TOP, TRUE);
                currentIndexTextView.addRule(CENTER_HORIZONTAL, TRUE);
                currentIndexTextView.height = dpToPx(49);
                currentIndexTextView.width = dpToPx(58);
                return currentIndexTextView;
            case PROGRESS_CONTAINER:
                RelativeLayout.LayoutParams progressContainer = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                progressContainer.addRule(BELOW, viewIdForRule);
                return progressContainer;
//            case GREY_CIRCLE:
//                LayoutParams greyCircle = new LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                greyCircle.height = dpToPx(20);
//                greyCircle.width = dpToPx(20);
//                return greyCircle;
            case GREY_CIRCLE:
            case GREEN_CIRCLE:
                LayoutParams circle = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                circle.height = dpToPx(20);
                circle.width = dpToPx(20);
                circle.addRule(BELOW, viewIdForRule);
                circle.addRule(CENTER_HORIZONTAL, TRUE);
                return circle;
            case CURRENT_CIRCLE:
                LayoutParams currentCircle = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                currentCircle.height = dpToPx(26);
                currentCircle.width = dpToPx(26);
                currentCircle.addRule(BELOW, viewIdForRule);
                return currentCircle;
            case DOTTED_BAR:
            case SOLID_BAR:
                LayoutParams bar = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                bar.height = dpToPx(35);
                bar.width = dpToPx(3);
                bar.addRule(BELOW, viewIdForRule);
                bar.addRule(CENTER_HORIZONTAL, TRUE);
                return bar;

//                LayoutParams solidBar = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                solidBar.height = dpToPx(35);
//                solidBar.width = dpToPx(3);
//                solidBar.addRule(BELOW, viewIdForRule);
//                return solidBar;
            default:
                return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private Drawable generateDrawable(int layoutType) {
        switch (layoutType) {
            case PARENT_LAYOUT:
                GradientDrawable parentLayout = new GradientDrawable();
                parentLayout.setColor(Color.DKGRAY);
                return parentLayout;
            case CURRENT_INDEX:
                GradientDrawable currentIndexTextView = new GradientDrawable();
                currentIndexTextView.setColor(Color.parseColor("#61C436")); //TODO Attrz
                currentIndexTextView.setCornerRadii(new float[]{
                        0, 0,
                        5, 5,
                        0, 0,
                        0, 0});
                return currentIndexTextView;
            case GREY_CIRCLE:
                GradientDrawable greyCircleView = new GradientDrawable();
                greyCircleView.setShape(GradientDrawable.OVAL);
                greyCircleView.setColor(Color.parseColor("#D8D8D8")); //TODO Attrz
                return greyCircleView;
            case GREEN_CIRCLE:
                GradientDrawable greenCircleView = new GradientDrawable();
                greenCircleView.setShape(GradientDrawable.OVAL);
                greenCircleView.setColor(Color.parseColor("#61C436")); //TODO Attrz
                return greenCircleView;
            case CURRENT_CIRCLE:
                GradientDrawable currentCircleView = new GradientDrawable();
                currentCircleView.setShape(GradientDrawable.OVAL);
                currentCircleView.setColor(Color.parseColor("#B0E19A")); //TODO Attrz
                currentCircleView.setStroke(3, Color.parseColor("#61C436")); //TODO Attrz
                return currentCircleView;
            case DOTTED_BAR:
                ShapeDrawable dottedBar = new ShapeDrawable(new RectShape());
                Paint paint = dottedBar.getPaint();
                paint.setAntiAlias(true);
                paint.setColor(Color.parseColor("#D8D8D8"));//TODO Attrz
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(6);
                paint.setPathEffect(new DashPathEffect(new float[]{6.0f, 8.0f}, 0.0f));
                return dottedBar;
            case SOLID_BAR:
                ShapeDrawable solidBar = new ShapeDrawable(new RectShape());
                Paint solidPaint = solidBar.getPaint();
                solidPaint.setAntiAlias(true);
                solidPaint.setColor(Color.parseColor("#61C436"));//TODO Attrz
                solidPaint.setStyle(Paint.Style.STROKE);
                solidPaint.setStrokeWidth(3);
                return solidBar;
            default:
                return new GradientDrawable();
        }
    }

    private int dpToPx(int dp) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;
        return density == 1.5 ? dp : Math.round((float) dp * density);
    }

    private TextView buildTextView(TextView view, Typeface typeface, int textStyle, int textSizeDP,
                                   @ColorInt int colorInt, int gravity) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;

        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, density == 1.5 ? textSizeDP : Math.round((float) textSizeDP * density));
        view.setTextColor(colorInt);
        view.setTypeface(typeface, textStyle);
        view.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        view.setGravity(gravity);

        return view;
    }


}
