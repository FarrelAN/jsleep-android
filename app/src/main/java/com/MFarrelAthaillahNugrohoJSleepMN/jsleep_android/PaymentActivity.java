package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import static com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.DetailRoomActivity.tempRoom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.content.Context;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.*;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.*;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Date.*;
import java.text.SimpleDateFormat;
import java.util.regex.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    Button button_book;
    TextView book_roomName, book_roomAddress, book_roomPrice;
    EditText checkIn, checkOut, book_Name, book_Email, book_Phone;
    DatePickerDialog picker_checkIn, picker_checkOut;
    Context mContext;
    BaseApiService mApiService;

    final String REGEX_DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mApiService= UtilsApi.getApiService();
        mContext = this;
        book_Name = findViewById(R.id.bookvar_name);
        book_Email = findViewById(R.id.bookvar_email);
        book_Phone = findViewById(R.id.bookvar_phone);
        book_roomName = findViewById(R.id.bookvar_roomname);
        book_roomAddress = findViewById(R.id.bookvar_roomaddress);
        book_roomPrice = findViewById(R.id.bookvar_roomprice);

        book_Name.setText(MainActivity.cookies.name);
        book_Email.setText(MainActivity.cookies.email);
        book_roomName.setText(tempRoom.name);
        book_roomAddress.setText(tempRoom.address);
        try {
            book_Phone.setText(MainActivity.cookies.renter.phoneNumber);
        }
        catch (RuntimeException f) {
            f.printStackTrace();
        }

        book_roomPrice.setText(String.valueOf(tempRoom.price.price));
        button_book = findViewById(R.id.button_booking);
        checkIn = findViewById(R.id.date_checkIn);
        checkOut = findViewById(R.id.date_checkOut);
        checkIn.setInputType(InputType.TYPE_NULL);
        checkOut.setInputType(InputType.TYPE_NULL);

        button_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Payment payment = requestPayment(MainActivity.cookies.id, MainActivity.cookies.id, tempRoom.id,
                        checkIn.getText().toString(), checkOut.getText().toString());
            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar checkInCalendar = Calendar.getInstance();
                int day = checkInCalendar.get(Calendar.DAY_OF_MONTH);
                int month = checkInCalendar.get(Calendar.MONTH);
                int year = checkInCalendar.get(Calendar.YEAR);

                picker_checkIn = new DatePickerDialog(PaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                checkIn.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker_checkIn.show();
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker_checkOut = new DatePickerDialog(PaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                checkOut.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker_checkOut.show();
            }
        });

    }

    protected Payment requestPayment(int buyerId, int renterId, int roomId, String fromDate, String toDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date chInDate = null;
        Date chOutDate = null;

        System.out.println(fromDate);
        System.out.println(toDate);

        Pattern pattern = Pattern.compile(REGEX_DATE_PATTERN);
        Matcher matcher = pattern.matcher(fromDate);
        Matcher matcher2 = pattern.matcher(toDate);

        boolean isMatched = matcher.matches();
        boolean isMatched2 = matcher2.matches();

        try {
            chInDate  = sdf.parse(fromDate);
            chOutDate = sdf.parse(toDate);
            System.out.println("Date  "+ checkOut);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        long diffInMilliseconds = chOutDate.getTime() - chInDate.getTime();
        long diffInDays = diffInMilliseconds / (1000 * 60 * 60 * 24);

        if(isMatched && isMatched2) {
            mApiService.payment(buyerId, renterId, roomId, fromDate, toDate).enqueue(new Callback<Payment>() {
                @Override
                public void onResponse(Call<Payment> call, Response<Payment> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, "Booking Success", Toast.LENGTH_SHORT).show();

                        MainActivity.cookies.balance = MainActivity.cookies.balance - (Double.parseDouble(book_roomPrice.getText().toString().replaceAll("S\\$|\\.$", "")) * (double) diffInDays);
                        Intent move = new Intent(PaymentActivity.this, MainActivity.class);
                        startActivity(move);
                    } else {
                        Toast.makeText(mContext, "Booking Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Payment> call, Throwable t) {
                    Toast.makeText(mContext, "Date Unavailable, Booking Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(mContext, "Invalid Date Format", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.add_button);
        MenuItem refresh = menu.findItem(R.id.refresh_button);
        MenuItem acc = menu.findItem(R.id.account_button);
        MenuItem box = menu.findItem(R.id.add_button);
        MenuItem search = menu.findItem(R.id.search_button);
        MenuItem home = menu.findItem(R.id.home_button);
        MenuItem logout = menu.findItem(R.id.logout_button);
        register.setVisible(true);
        search.setVisible(false);
        refresh.setVisible(false);
        acc.setVisible(false);
        box.setVisible(false);
        home.setVisible(true);
        logout.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_button:
                Intent move = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(move);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}