package com.tacademy.sadajo.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by EUNZY on 2016. 11. 20..
 */

public class NanumBoldTextView extends TextView {
    public NanumBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareBold.ttf"));
    }

    public NanumBoldTextView(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareBold.ttf"));
    }
}
