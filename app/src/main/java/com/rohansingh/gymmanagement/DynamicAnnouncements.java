package com.rohansingh.gymmanagement;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class DynamicAnnouncements {
    Context ctx;

    public DynamicAnnouncements(Context ctx){
        this.ctx = ctx;
    }
    public TextView makeTextView(Context context, String text) {
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(15);
        textView.setTypeface(null, Typeface.BOLD_ITALIC);
        textView.setPadding(30, 10, 0, 10);
        textView.setTextColor(Color.BLACK);
        textView.setText(text);
        return textView;
    }
}
