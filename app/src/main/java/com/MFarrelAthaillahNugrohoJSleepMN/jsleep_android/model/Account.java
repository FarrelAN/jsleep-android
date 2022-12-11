package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model;


public class Account extends Serializable
{
    public String name;
    public String email;
    public String password;
    public Renter renter;
    public double balance;

    public Account(int id) {
        super(id);
    }


    @Override
    public String toString (){
        return "====ACCOUNT====\n" +  "\nName : " + name + "\nEmail : " + email +
                "\nPassword : " + password;
    }

}
