package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.Serializable;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.*;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMe extends AppCompatActivity {
    TextView name, email, balance;
    EditText renterName, renterAddress, renterNumber;
    Button regRenter, reg, cancel;
    CardView noRenter, RegistRenter, AvailRenter;
    TextView TextrentName, TextrentAddress, TextrentNumber, VarrentName, VarrentAddress, VarrentNumber ;
    Context mContext;
    BaseApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mContext = this;

        name = findViewById(R.id.acc_name);
        email = findViewById(R.id.acc_email);
        balance = findViewById(R.id.acc_balance);

        name.setText(MainActivity.cookies.name);
        email.setText(MainActivity.cookies.email);
        balance.setText(String.valueOf(MainActivity.cookies.balance));
        regRenter = findViewById(R.id.button_regrent);

        RegistRenter = findViewById(R.id.cardview_RegRent);
        renterName = findViewById(R.id.regrent_name);
        renterAddress = findViewById(R.id.regrent_address);
        renterNumber = findViewById(R.id.regrent_number);
        reg = findViewById(R.id.button_register);
        cancel = findViewById(R.id.button_cancel);

        AvailRenter = findViewById(R.id.cardview_AvailRent);
        TextrentName = findViewById(R.id.availrent_name);
        TextrentAddress = findViewById(R.id.availrent_address);
        TextrentNumber = findViewById(R.id.availrent_address);
        VarrentName = findViewById(R.id.varrent_name);
        VarrentAddress = findViewById(R.id.varrent_address);
        VarrentNumber = findViewById(R.id.varrent_Number);


    }
}

