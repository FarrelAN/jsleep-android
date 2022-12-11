package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Payment extends Invoice {


    private int roomId;
    public Date from;
    public Date to;



    public Payment(int buyerId, int renterId, int roomId, Date from, Date to) {
        super(buyerId, renterId);
        this.roomId = roomId;
        this.from = from;
        this.to = to;
    }

    public Payment(Account buyer, Renter renter, int roomId, Date from, Date to) {
        super(buyer, renter);
        this.roomId = roomId;
        this.from = from;
        this.to = to;
    }

    public static boolean makeBooking(Date from, Date to, Room room){
        if(availability(from, to, room)){
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(from);
            end.setTime(to);
            for(Date date= start.getTime();start.before(end);start.add(Calendar.DATE,1),date=start.getTime()) {
                room.booked.add(date);
            }
            return true;
        }
        return false;
    }

    public static boolean availability(Date from, Date to, Room room){
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(from);
        end.setTime(to);
        if(start.after(end)||start.equals(end)){
            return false;
        }
        for(Date date= start.getTime();start.before(end);start.add(Calendar.DATE,1),date=start.getTime()) {
            if(room.booked.contains(date)){
                return false;
            }
        }
        return true;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public String print(){
        return "Payment{" +
                "roomId=" + roomId +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }

}
