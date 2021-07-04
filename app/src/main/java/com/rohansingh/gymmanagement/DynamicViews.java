package com.rohansingh.gymmanagement;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class DynamicViews {
    Context ctx;

    public DynamicViews(Context ctx){
        this.ctx = ctx;
    }
    public TextView makeTextView(Context context, String text) {
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(0, 10, 0, 10);
        textView.setTextColor(Color.BLACK);
        textView.setText(text);
        return textView;
    }
}
