package com.visionless.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class DistrictDetailsActivity extends AppCompatActivity {

    TextView earthquakeNumber;
    int count ;
    String cityName =  "";
    String districtName =  "";
    TextView cityName2;
    TextView districtName2;
    ArrayList<String> logs;
    String pastData="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_details);

        earthquakeNumber = findViewById(R.id.detail_earthquake_number);
        Intent i = getIntent();
        cityName = i.getStringExtra("city_name");
        districtName = i.getStringExtra("district_name");

        cityName2 = findViewById(R.id.details_city_name);
        cityName2.setText(cityName);

        districtName2 = findViewById(R.id.details_district_name);
        districtName2.setText(districtName);

        try {
            Socket client = new Socket(MainActivity.ipAddress, MainActivity.port);
            PrintStream out = new PrintStream(client.getOutputStream());
            out.println("districtDetails " + cityName + " " + districtName);
            Scanner in = new Scanner(client.getInputStream());
            count=in.nextInt();
            pastData=in.next();

            earthquakeNumber.setText(""+count);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // LOg List View
        logs = new ArrayList<String>();
        ArrayList<String> logs2 = new ArrayList<String>();
        pastData=pastData.substring(1,pastData.length()-1);
        while(pastData.contains("|")){

           int index = pastData.substring(1).indexOf('|');
           if (index>0){
               logs2.add(pastData.substring(1,index+1));
               pastData=pastData.substring(index+1);
           }
           else{
               break;
           }
        }
        for (int j=logs2.size()-1;j>=0;j--){
            logs.add(logs2.get(j));
        }



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, logs);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        ListView list = (ListView) findViewById(R.id.log_list);
        list.setAdapter(dataAdapter);
        list.setItemsCanFocus(false);


    }

    public void alert(View v){
        Toast.makeText(this, "ALERTTT!", Toast.LENGTH_SHORT).show();
        earthquakeNumber.setText(""+(++count));
        try {
            Socket client = new Socket(MainActivity.ipAddress, MainActivity.port);
            PrintStream out = new PrintStream(client.getOutputStream());
            out.println("update " + cityName + " " + districtName);


            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
