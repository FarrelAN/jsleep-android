package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.model.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    public static Account cookies;
    String name;
    static ArrayList<Room> roomList = new ArrayList<Room>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();
        File file = new File(getFilesDir(), "randomRoomList.json");
        MenuItem item;
        try {
            InputStream filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            Room[] temp = gson.fromJson(reader, Room[].class);
            Collections.addAll(roomList, temp);
        }catch(Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> namelist = new ArrayList<String>();
        for(Room room : roomList) {
            namelist .add(room.name);
        }
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                namelist );
        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account_button:
                Intent aboutme = new Intent(MainActivity.this, AboutMe.class);
                startActivity(aboutme);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}