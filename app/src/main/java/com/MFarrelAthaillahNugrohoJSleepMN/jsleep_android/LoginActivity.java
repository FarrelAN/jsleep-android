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

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.Account;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.*;

public class LoginActivity extends AppCompatActivity {

    BaseApiService mApiService;
    EditText username, password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        TextView register = findViewById(R.id.textView3);
        Button mainActivity = findViewById(R.id.button);
        username = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Account account = requestAccount();
                Account account = requestLogin();
                //  Intent move = new Intent(LoginActivity.this, MainActivity.class);
                //   startActivity(move);
            }
        });

    }

    protected Account requestAccount() {
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                    System.out.println("tes");
                }

            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "no Account id=0", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected Account requestLogin() {
        mApiService.login(username.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {

                    MainActivity.cookies = response.body();
                    Intent go = new Intent(LoginActivity.this, MainActivity.class);

                    startActivity(go);
                    Toast.makeText(mContext, "Login Successfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println(t.toString());

                Toast.makeText(mContext, "invalid email or password",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return null;
    }
}