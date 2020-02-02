package com.example.yazlab23;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;

public class ObjeBilgi extends android.support.v7.widget.AppCompatTextView{

    public ObjeBilgi(Context context, String bilgi) {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 20, 30, 30);
        this.setLayoutParams(params);
        this.setText(bilgi);
        this.setTextSize(18);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        this.setTypeface(boldTypeface);
        this.setTextColor(Color.parseColor("#083e22"));
    }
    public ObjeBilgi(Context context, int i) {
        super(context);
        //this.setText("-------");
        this.setTextColor(Color.parseColor("#aebeb4"));
        this.setBackgroundColor(Color.parseColor("#000000"));
    }

}
