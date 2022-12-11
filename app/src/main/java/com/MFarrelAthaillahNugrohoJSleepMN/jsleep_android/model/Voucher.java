package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model;


public class Voucher  {
    public double cut;
    public String name;
    public int code;
    public double minimum;
    private boolean used;

    public boolean isUsed(){
        return (boolean) used;
    }
    public Object write(){
        return 0;
    }
    public boolean read(String a){
        return true;
    }

}
