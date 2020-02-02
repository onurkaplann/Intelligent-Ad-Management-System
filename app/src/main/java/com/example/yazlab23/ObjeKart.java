package com.example.yazlab23;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

public class ObjeKart extends android.support.v7.widget.CardView {

    public ObjeKart(@NonNull Context context,Magaza magaza) {
        super(context);
        this.setCardBackgroundColor(Color.parseColor("#ffffff"));
        this.setRadius((float) 50F);
        this.addView(new ObjeLayout(getContext(), magaza));
    }

}
