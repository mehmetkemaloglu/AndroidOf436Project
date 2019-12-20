package com.visionless.earthquake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomDistrictListAdapter extends ArrayAdapter<District> {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<District> districts;

    public CustomDistrictListAdapter(Context context, ArrayList<District> districts) {
        super(context,0, districts);
        this.context = context;
        this.districts = districts;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return districts.size();
    }

    @Override
    public District getItem(int position) {
        return districts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return districts.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.district_list_item, null);

            holder = new ViewHolder();
            holder.districtName = (TextView) convertView.findViewById(R.id.district_name);
            holder.earthquakeNumber = (TextView) convertView.findViewById(R.id.district_earthquake_number);
            convertView.setTag(holder);

        }
        else{
            //Get viewholder we already created
            holder = (ViewHolder)convertView.getTag();
        }

        District district = districts.get(position);
        if(district != null){
            holder.districtName.setText(district.getName());
            holder.earthquakeNumber.setText(""+district.getEarthquakeNumber());
        }
        return convertView;
    }

    //View Holder Pattern for better performance
    private static class ViewHolder {
        TextView districtName;
        TextView earthquakeNumber;
    }
}