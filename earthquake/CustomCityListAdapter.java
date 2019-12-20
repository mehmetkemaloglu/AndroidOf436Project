package com.visionless.earthquake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomCityListAdapter extends ArrayAdapter<City> {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<City> cities;

    public CustomCityListAdapter(Context context, ArrayList<City> cities) {
        super(context,0, cities);
        this.context = context;
        this.cities = cities;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public City getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cities.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.city_list_item, null);

            holder = new ViewHolder();
            holder.cityName = (TextView) convertView.findViewById(R.id.city_name);
            holder.earthquakeNumber = (TextView) convertView.findViewById(R.id.city_earthquake_number);
            convertView.setTag(holder);

        }
        else{
            //Get viewholder we already created
            holder = (ViewHolder)convertView.getTag();
        }

        City city = cities.get(position);
        if(city != null){
            holder.cityName.setText(city.getName());
            holder.earthquakeNumber.setText(""+city.getEarthquakeNumber());
        }
        return convertView;
    }

    //View Holder Pattern for better performance
    private static class ViewHolder {
        TextView cityName;
        TextView earthquakeNumber;
    }
}