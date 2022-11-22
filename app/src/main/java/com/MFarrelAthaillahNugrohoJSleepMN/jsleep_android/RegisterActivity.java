package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.Account;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.BaseApiService;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.UtilsApi;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity {

    BaseApiService mApiService;
    EditText name, username, password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApiService = UtilsApi.getApiService();
        mContext = this;
        username = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
        name = findViewById(R.id.editTextTextPersonName);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button reg_mainActivity = findViewById(R.id.but.ton3);

        reg_mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestRegister();
            }
        });
    }

    protected Account requestRegister() {
        mApiService.register(name.getText().toString(), username.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    MainActivity.cookies = response.body();
                    Intent go = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(go);
                    Toast.makeText(mContext, "Register successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Existing account detected", Toast.LENGTH_SHORT).show();
            }
        });

        return null;
    }
}
