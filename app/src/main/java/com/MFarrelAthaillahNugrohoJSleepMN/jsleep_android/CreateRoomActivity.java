package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;
import android.os.Bundle;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.*;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.*;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateRoomActivity extends AppCompatActivity {

    EditText roomName, roomPrice, roomSize, roomAddress;
    Spinner bedSpin, citySpin;
    Button createRoom, cancelCreate;
    CheckBox wifi, bathtub, balcony, AC, gym, fridge, restaurant, pool;
    ArrayList<Facility> facility = new ArrayList<Facility>();
    BedType bedType;
    Price price;
    City city;

    BaseApiService mApiService;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                finish();
                startActivity(getIntent());
            }
        };

        setContentView(R.layout.activity_create_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        bedSpin = (Spinner) findViewById(R.id.spin_bedtype);
        citySpin = (Spinner) findViewById(R.id.spin_city);

        AC = findViewById(R.id.check_ac);
        fridge = findViewById(R.id.check_fridge);
        wifi = findViewById(R.id.check_wifi);
        bathtub = findViewById(R.id.check_bathtub);
        balcony = findViewById(R.id.check_balcony);
        restaurant = findViewById(R.id.check_resto);
        pool = findViewById(R.id.check_pool);
        gym = findViewById(R.id.check_gym);

        roomName = findViewById(R.id.detail_name);
        roomPrice = findViewById(R.id.detail_price);
        roomSize = findViewById(R.id.detail_size);
        roomAddress = findViewById(R.id.detail_address);
        createRoom = findViewById(R.id.button_createroom);
        cancelCreate = findViewById(R.id.button_cancelcreate);

        bedSpin.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        citySpin.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AC.isChecked()) {
                    facility.add(Facility.AC);
                }
                if (fridge.isChecked()) {
                    facility.add(Facility.Refrigerator);
                }
                if (wifi.isChecked()) {
                    facility.add(Facility.WiFi);
                }
                if (bathtub.isChecked()) {
                    facility.add(Facility.Bathtub);
                }
                if (balcony.isChecked()) {
                    facility.add(Facility.Balcony);
                }
                if (restaurant.isChecked()) {
                    facility.add(Facility.Restaurant);
                }
                if (pool.isChecked()) {
                    facility.add(Facility.SwimmingPool);
                }
                if (gym.isChecked()) {
                    facility.add(Facility.FitnessCenter);
                }
                String bed = bedSpin.getSelectedItem().toString();
                String cityStr = citySpin.getSelectedItem().toString();
                bedType = BedType.valueOf(bed);
                city = City.valueOf(cityStr);

                Integer priceObj = new Integer(roomPrice.getText().toString());
                Integer sizeObj = new Integer(roomSize.getText().toString());

                int priceInt = priceObj.parseInt(roomPrice.getText().toString());
                int sizeInt = sizeObj.parseInt(roomSize.getText().toString());
                //price.price = priceInt;
                Room room = requestRoom(MainActivity.cookies.id, roomName.getText().toString(), sizeInt, priceInt, facility, city, roomAddress.getText().toString(), bedType);
            }
        });
    }

    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            finish();
            startActivity(getIntent());
        }
    };

    @Override
    public void onRestart() {
        super.onRestart();
    }

    protected Room requestRoom(int id, String name, int size, int price, ArrayList<Facility> facility, City city, String address, BedType bedType) {
        mApiService.room(id, name, size, price, facility, city, address, bedType).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Room has been created!", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Failed creating room", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home_button:
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.add_button);
        MenuItem refresh = menu.findItem(R.id.refresh_button);
        MenuItem acc = menu.findItem(R.id.account_button);
        MenuItem box = menu.findItem(R.id.add_button);
        MenuItem search = menu.findItem(R.id.search_button);
        MenuItem home = menu.findItem(R.id.home_button);
        register.setVisible(true);
        search.setVisible(false);
        refresh.setVisible(false);
        acc.setVisible(false);
        box.setVisible(false);
        home.setVisible(true);
        return true;
    }
}