package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.Account;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.BaseApiService;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.UtilsApi;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText name, email,  password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        name = findViewById(R.id.register_name);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        Button mainActivity = findViewById(R.id.register_button);

        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Account account = requestRegister();
            }
        });
    }

    protected Account requestRegister(){
        mApiService.register(name.getText().toString(), email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){

                    MainActivity.cookies = response.body();

                    Intent go = new Intent(RegisterActivity.this,
                            LoginActivity.class);

                    startActivity(go);
                    Toast.makeText(mContext, "Register Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Account Already Exist", Toast.LENGTH_SHORT).show();
            }
        });

        return null;
    }




}
