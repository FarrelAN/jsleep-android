package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable
{
    public int size;
    public String name;
    public String address;
    public ArrayList<Facility> facility;
    public Price price;
    public BedType bedType;
    public City city;

    public int accountId;
    public ArrayList<Date> booked;

    public Room(int accountId, String name, int size, Price price, ArrayList<Facility> facility, City city, String address, BedType bedType) {
        this.accountId = accountId;
        this.name = name;
        this.size = size;
        this.price = price;
        this.facility.addAll(facility);
        this.bedType = bedType;
        this.city = city;
        this.address = address;
        this.booked = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Room{" +
                "size=" + size +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", facility=" + facility +
                ", price=" + price +
                ", bedType=" + bedType +
                ", city=" + city +
                ", accountId=" + accountId +
                ", booked=" + booked +
                '}';
    }

    // @Override
    public boolean read(String xxx){
        return true;
    }

    //@Override
    public Object write(){
        return null;
    }

}

