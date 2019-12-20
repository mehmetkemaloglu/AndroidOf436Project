package com.visionless.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class DistrictActivity extends AppCompatActivity {

    private ArrayList<District> districts;
    private ListView listView;
    private CustomDistrictListAdapter listViewAdapter;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateEarthquakes();
    }

    private void updateEarthquakes() {
        // initialize list
        districts = new ArrayList<District>();
        listView = (ListView) findViewById(R.id.district_list);
        listViewAdapter = new CustomDistrictListAdapter(this, districts);
        listView.setAdapter(listViewAdapter);

        Intent i = getIntent();
        cityName = i.getStringExtra("city_name");

        // TODO receive districts of cityName

        try {
            Socket client = new Socket(MainActivity.ipAddress, MainActivity.port);
            PrintStream out = new PrintStream(client.getOutputStream());
            out.println("districtList "+cityName);
            Scanner in = new Scanner(client.getInputStream());
            while (in.hasNext()){
                District currentDistrict = new District(in.next(), in.nextInt());
                districts.add(currentDistrict);
            }


            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                District item = (District) parent.getItemAtPosition(position);
                Toast.makeText(DistrictActivity.this, ""+item.getName(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent (DistrictActivity.this, DistrictDetailsActivity.class);
                i.putExtra("district_name", item.getName());
                i.putExtra("city_name", cityName);
                startActivity(i);
            }
        });

        listViewAdapter.notifyDataSetChanged();
    }
}
