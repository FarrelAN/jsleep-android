package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import android.content.Intent;
import java.lang.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Context;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.*;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request.*;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import java.util.*;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static Account cookies;
    String name;
    static ArrayList<Room> roomList = new ArrayList<Room>();

    List<String> nameList;
    List<Room> temp;
    List<Room> ret;
    ListView roomlistview;
    EditText pagenum;
    BaseApiService mApiService;
    static BaseApiService mApiServiceStatic;
    Context mContext;
    Button nextpage, prevpage, aboutme;
    int currentPage;
    boolean count = false;
    Account empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
                startActivity(getIntent());
            }
        };

        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mApiServiceStatic = UtilsApi.getApiService();
        mContext = this;

        nextpage = findViewById(R.id.nextbutton);
        prevpage = findViewById(R.id.prevbutton);

        ret = getRoomList(0, 10);

        pagenum = findViewById(R.id.text_pagenumber);
        roomlistview = findViewById(R.id.listview);
        roomlistview.setOnItemClickListener(this::onItemClick);

        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (temp.size() > currentPage) {
                    currentPage = 1;
                    return;
                }
                currentPage++;
                try {
                    ret = getRoomList(currentPage - 0, 5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        prevpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage <= 1) {
                    currentPage = 1;
                    Toast.makeText(mContext, "Currently on first page", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPage++;
                try {
                    ret = getRoomList(currentPage - 1, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pagenum.setText(String.valueOf(currentPage+1));
    }

    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            finish();
            startActivity(getIntent());
        }
    };

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.topmenu, menu);
            return true;
        }

        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case R.id.account_button:
                    Intent move1 = new Intent(MainActivity.this, AboutMe.class);
                    startActivity(move1 );
                    return true;
                case R.id.add_button:
                    Intent move2 = new Intent(MainActivity.this, CreateRoomActivity.class);
                    startActivity(move2);
                    return true;
                case R.id.home_button:
                    Intent move3 = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(move3);
                    return true;
                case R.id.refresh_button:
                    finish();
                    startActivity(getIntent());
                    return true;
                case R.id.logout_button:
                    Intent move4 = new Intent(MainActivity.this, LoginActivity.class);
                    finish();
                    startActivity(move4);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        public boolean onPrepareOptionsMenu (Menu menu)
        {
            MenuItem registerroom = menu.findItem(R.id.add_button);
            MenuItem home = menu.findItem(R.id.home_button);
            home.setVisible(false);
            if (cookies.renter == null) {
                registerroom.setVisible(false);
            } else {
                registerroom.setVisible(true);
            }
            return true;
        }

        protected List<Room> getRoomList (int page, int pageSize) {
            mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
                @Override
                public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                    if (response.isSuccessful()) {
                        temp = response.body();
                        nameList = getName(temp);
                        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, R.layout.listview_config, nameList);
                        roomlistview = (ListView) findViewById(R.id.listview);
                        roomlistview.setAdapter(itemAdapter);
                    }
                }
                @Override
                public void onFailure(Call<List<Room>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            return null;
        }

        protected static Account reloadAccount (int id) {
            mApiServiceStatic.getAccount(id).enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    if (response.isSuccessful()) {
                        MainActivity.cookies = response.body();
                    }
                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            return null;
        }

        public static ArrayList<String> getName (List <Room> list) {
            ArrayList<String> getname = new ArrayList<String>();
            int i;
            for(i = 0; i < list.size(); i++) {
                getname.add(list.get(i).name);
            }
            return getname;
        }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, DetailRoomActivity.class);
        DetailRoomActivity.tempRoom = temp.get(position);
        startActivity(intent);
    }
}