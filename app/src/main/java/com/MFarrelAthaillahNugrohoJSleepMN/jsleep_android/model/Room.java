package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model;

import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.City;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.Facility;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.Price;
import java.util.ArrayList;
import java.util.Date;

/**
 * Ini adalah class Room
 * @author M. Farrel Athaillah Nugroho
 * @version Modul 8
 */

public class Room extends Serializable
{
    public int accountId;
    public int size;
    public String name;
    public ArrayList<Date> booked;
    public String address;
    public Price price;
    public City city;
    public BedType bedType;
    public Facility facility;

    public Room(int id) {
        super(id);
    }

}

