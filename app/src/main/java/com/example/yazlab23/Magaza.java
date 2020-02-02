package com.example.yazlab23;

public class Magaza {
    double mesafe;
    String firmaid,firmaadi,firmalokasyon,kampanyaicerik,kampanyasuresi,resimkey;

    public Magaza(double mesafe2,String firmaid2,String firmaadi2,String firmalokasyon2,String kampanyaicerik2,String kampanyasuresi2,String resimkey2){
        mesafe = mesafe2;
        firmaid = firmaid2;
        firmaadi = firmaadi2;
        firmalokasyon = firmalokasyon2;
        kampanyaicerik = kampanyaicerik2;
        kampanyasuresi = kampanyasuresi2;
        resimkey = resimkey2;
    }

    public double getMesafe(){
        return mesafe;
    }
    public String getFirmaid(){
        return firmaid;
    }
    public String getFirmaadi(){
        return  firmaadi;
    }
    public String getFirmalokasyon(){ return firmalokasyon; }
    public String getKampanyaicerik(){
        return kampanyaicerik;
    }
    public String getKampanyasuresi(){
        return kampanyasuresi;
    }
    public String getResimkey(){ return resimkey; }
}
