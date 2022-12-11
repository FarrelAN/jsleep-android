package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLOutput;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.*;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.*;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText email,password;
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

        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        TextView register = findViewById(R.id.login_register);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pass);

        Button mainActivity = findViewById(R.id.login_button);

        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Account account = requestLogin();
            }
        }
        );

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
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


    protected Account requestAccount(){
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()) {
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "no Account id = 0", LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected Account requestLogin(){
        mApiService.login(email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()) {
                    MainActivity.cookies = response.body();
                    Intent go = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(go);
                    Toast.makeText(mContext, "Login Successful", LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Invalid email or password",
                        LENGTH_SHORT).show();
            }
        });
        return null;
    }
}