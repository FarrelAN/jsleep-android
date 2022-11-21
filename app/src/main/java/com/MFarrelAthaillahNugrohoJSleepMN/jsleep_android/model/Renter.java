package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ini adalah class Renter
 * @author M. Farrel Athaillah Nugroho
 * @version Modul 8
 */
public class Renter extends Serializable {

    public String username;
    public String address;
    public String phoneNumber;
    public Renter(int id) {
        super(id);
    }

}
