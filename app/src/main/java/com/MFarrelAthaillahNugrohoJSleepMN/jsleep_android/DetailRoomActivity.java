package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.*;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.BaseApiService;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.UtilsApi;

public class DetailRoomActivity extends AppCompatActivity {


    TextView roomName, roomPrice, roomSize, roomAddress, roomBedtype;
    CheckBox wifi, bathtub, balcony, AC, gym, fridge, restaurant, pool;
    Button book;
    BaseApiService mApiService;

    public static Room tempRoom;

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

        mApiService = UtilsApi.getApiService();
        setContentView(R.layout.activity_detail_room);

        roomName = findViewById(R.id.detailvar_name);
        roomPrice = findViewById(R.id.detailvar_price);
        roomSize = findViewById(R.id.detailvar_size);
        roomAddress = findViewById(R.id.detailvar_address);
        roomBedtype = findViewById(R.id.detailvar_bedtype);

        book = findViewById(R.id.booking_button);

        wifi = findViewById(R.id.detailcheck_wifi);
        bathtub = findViewById(R.id.detailcheck_bathtub);
        balcony = findViewById(R.id.detailcheck_balcony);
        AC = findViewById(R.id.detailcheck_ac);
        gym = findViewById(R.id.detailcheck_gym);
        fridge = findViewById(R.id.detailcheck_fridge);
        restaurant = findViewById(R.id.detailcheck_resto);
        pool = findViewById(R.id.detailcheck_pool);

        roomName.setText(tempRoom.name);
        roomPrice.setText(String.valueOf(tempRoom.price.price));
        roomSize.setText(String.valueOf(tempRoom.size));
        roomAddress.setText(tempRoom.address);
        roomBedtype.setText(tempRoom.bedType.toString());

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent booking = new Intent(DetailRoomActivity.this, PaymentActivity.class);
                startActivity(booking);
            }
        });

        for (int i = 0; i < tempRoom.facility.size(); i++) {
            if (tempRoom.facility.get(i).equals(Facility.AC)) {
                AC.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Refrigerator)) {
                fridge.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.WiFi)) {
                wifi.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Bathtub)) {
                bathtub.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Balcony)) {
                balcony.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Restaurant)) {
                restaurant.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                pool.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                gym.setChecked(true);
            }
        }
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
        register.setVisible(true);
        search.setVisible(false);
        refresh.setVisible(false);
        acc.setVisible(false);
        box.setVisible(false);
        home.setVisible(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_button:
                Intent move = new Intent(DetailRoomActivity.this, MainActivity.class);
                startActivity(move);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}