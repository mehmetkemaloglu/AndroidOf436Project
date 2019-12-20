package com.visionless.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.StrictMode;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    public static final int port =8080;
    public static final String ipAddress= "54.245.129.91";
    private ArrayList<City> cities;
    private ListView listView;
    private CustomCityListAdapter listViewAdapter;
    public static int cityID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateEarthquakes();
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        for(City currentCity:cities){
           categories.add(currentCity.getName());
        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityID = i;
                Toast.makeText(MainActivity.this, ""+cities.get(i).getName()+" is selected", Toast.LENGTH_SHORT).show();
                vibrate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                vibrate();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateEarthquakes();
    }

    private void updateEarthquakes() {
        // initialize list
        cities = new ArrayList<City>();
        listView = (ListView) findViewById(R.id.city_list);
        listViewAdapter = new CustomCityListAdapter(this, cities);
        listView.setAdapter(listViewAdapter);

        try {
            Socket client = new Socket(ipAddress, port);
            PrintStream out = new PrintStream(client.getOutputStream());
            out.println("cityList");
            Scanner in = new Scanner(client.getInputStream());
            while (in.hasNext()){
                City currentCity = new City(in.next(), in.nextInt());
                cities.add(currentCity);
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        vibrate();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                City item = (City) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, ""+item.getName(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent (MainActivity.this, DistrictActivity.class);
                i.putExtra("city_name", item.getName());
                startActivity(i);
            }
        });



        listViewAdapter.notifyDataSetChanged();
    }
    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            if(cities.get(cityID).earthquakeNumber>100){
                v.vibrate(2500);
            }

        }
    }
}
