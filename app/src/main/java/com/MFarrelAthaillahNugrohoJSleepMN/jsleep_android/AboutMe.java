package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.*;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMe extends AppCompatActivity {
    TextView name, email, balance;
    EditText rentName, rentAddress, rentNumber, topupBalance;
    Button regrent, register, cancel, submittopup;
    CardView RegistRenter, InputRenter, AvailRenter;
    TextView TextrentName, TextrentAddress, TextrentNumber, VarrentName, VarrentAddress, VarrentNumber;
    Context mContext;
    BaseApiService mApiService;

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

        setContentView(R.layout.activity_about_me);
        mApiService= UtilsApi.getApiService();
        mContext = this;

        name = findViewById(R.id.acc_name);
        email = findViewById(R.id.acc_email);
        balance = findViewById(R.id.acc_balance);
        name.setText(MainActivity.cookies.name);
        email.setText(MainActivity.cookies.email);
        balance.setText(String.valueOf(MainActivity.cookies.balance));

        RegistRenter = findViewById(R.id.cardview_RegistRent);
        regrent = findViewById(R.id.button_regrent);

        InputRenter = findViewById(R.id.cardview_InputRent);
        rentName = findViewById(R.id.regrent_name);
        rentAddress = findViewById(R.id.regrent_address);
        rentNumber = findViewById(R.id.regrent_number);
        register = findViewById(R.id.button_register);
        cancel = findViewById(R.id.button_cancel);

        AvailRenter = findViewById(R.id.cardview_AvailRent);
        TextrentName = findViewById(R.id.availrent_name);
        TextrentAddress = findViewById(R.id.availrent_address);
        TextrentNumber = findViewById(R.id.availrent_Number);
        VarrentName = findViewById(R.id.varrent_name);
        VarrentAddress = findViewById(R.id.varrent_address);
        VarrentNumber = findViewById(R.id.varrent_Number);

        submittopup = findViewById(R.id.topup_button);
        topupBalance = findViewById(R.id.topup_amount);

        if (MainActivity.cookies.renter == null) {
            RegistRenter.setVisibility(CardView.VISIBLE);
            InputRenter.setVisibility(CardView.GONE);
            AvailRenter.setVisibility(CardView.GONE);

            regrent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RegistRenter.setVisibility(CardView.GONE);
                    InputRenter.setVisibility(CardView.VISIBLE);
                    AvailRenter.setVisibility(CardView.GONE);

                    register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestRenter(MainActivity.cookies.id, rentName.getText().toString(), rentAddress.getText().toString(), rentNumber.getText().toString());
                            RegistRenter.setVisibility(CardView.GONE);
                            InputRenter.setVisibility(CardView.GONE);
                            AvailRenter.setVisibility(CardView.VISIBLE);
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RegistRenter.setVisibility(CardView.VISIBLE);
                            InputRenter.setVisibility(CardView.GONE);
                            AvailRenter.setVisibility(CardView.GONE);
                        }
                    });
                }
            });
        } else {
            RegistRenter.setVisibility(CardView.GONE);
            InputRenter.setVisibility(CardView.GONE);
            AvailRenter.setVisibility(CardView.VISIBLE);
            VarrentName.setText(MainActivity.cookies.renter.username);
            VarrentAddress.setText(MainActivity.cookies.renter.address);
            VarrentNumber.setText(MainActivity.cookies.renter.phoneNumber);
        }
        submittopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = topupBalance.getText().toString();
                if (value.isEmpty()) {
                    Toast.makeText(mContext, "Empty top up amount", Toast.LENGTH_SHORT).show();
                } else {
                    Double topupVal = new Double(topupBalance.getText().toString());
                    double finalValue = topupVal.doubleValue();
                    if (finalValue < 10) {
                        Toast.makeText(mContext, "Minimum top up amount is 10 USD", Toast.LENGTH_SHORT).show();
                    } else {
                        requestTopUp(MainActivity.cookies.id, finalValue);
                    }
                }
            }
        });
    }

    protected Boolean requestTopUp(int id, double balance) {
        System.out.println("Id: " + id);
        System.out.println("TopUp: " + balance);
        mApiService.topUp(id, balance).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Boolean topUpResult = response.body();
                    System.out.println("Top Up Successful!");
                    MainActivity.cookies.balance = MainActivity.cookies.balance + balance;
                    Toast.makeText(mContext, "Top Up Successful!", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(AboutMe.this, AboutMe.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Top Up Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            finish();
            startActivity(getIntent());
        }
    };

    protected Renter requestRenter(int id, String username, String address, String phoneNumber) throws NullPointerException {
        System.out.println("Id: " + id);
        System.out.println("Username: " + username);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phoneNumber);
        mApiService.registerRenter(id, username, address, phoneNumber).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if (response.isSuccessful()) {
                    Renter renter = response.body();
                    MainActivity.cookies.renter = renter;
                    Toast.makeText(mContext, "Renter successfully registered", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(AboutMe.this, MainActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "Failed registering renter", Toast.LENGTH_SHORT).show();
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

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem register = menu.findItem(R.id.add_button);
        MenuItem refresh = menu.findItem(R.id.refresh_button);
        MenuItem acc = menu.findItem(R.id.account_button);
        MenuItem box = menu.findItem(R.id.add_button);
        MenuItem search = menu.findItem(R.id.search_button);
        MenuItem logout = menu.findItem(R.id.logout_button);
        register.setVisible(false);
        search.setVisible(false);
        refresh.setVisible(true);
        acc.setVisible(false);
        box.setVisible(false);
        logout.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_button:
                Intent move = new Intent(AboutMe.this, MainActivity.class);
                startActivity(move);
                return true;
            case R.id.refresh_button:
                Intent refresh = new Intent(AboutMe.this, AboutMe.class);
                startActivity(refresh);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}



