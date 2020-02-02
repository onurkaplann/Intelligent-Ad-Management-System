package com.example.yazlab23;

import android.content.Context;
import android.widget.LinearLayout;

public class ObjeLayout extends LinearLayout {
    public ObjeLayout(Context context,Magaza magaza) {
        super(context);
        this.setOrientation(VERTICAL);
        this.addView(new ObjeGorsel(getContext(),magaza.getResimkey()));
        this.addView(new ObjeBilgi(getContext(),"Firma Adı : "+magaza.getFirmaadi()));
        this.addView(new ObjeBilgi(getContext(),"Kampanya İçerik : "+magaza.getKampanyaicerik()));
        this.addView(new ObjeBilgi(getContext(),"Kampanya Süresi : "+magaza.getKampanyasuresi()));
        this.addView(new ObjeBilgi(getContext(),"Mesafe : "+magaza.getMesafe()));
    }
}
