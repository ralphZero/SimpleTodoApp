package com.example.to_do;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Item> {
    Context context;
    ArrayList<Item> arrayList;

    public CustomAdapter(Context context, ArrayList<Item> arrayList) {
        super(context, R.layout.custom_listview, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        TextView etCustomTask = convertView.findViewById(R.id.etCustomTask);
        etCustomTask.setText(arrayList.get(position).getTask());
        TextView etCustomDate = convertView.findViewById(R.id.etCustomDate);
        etCustomDate.setText(arrayList.get(position).getDate());
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        if (arrayList.get(position).isCompleted()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        ImageView priorityIndicator = convertView.findViewById(R.id.priorityIndicator);
        switch (arrayList.get(position).getPriority()) {
            case "High":
                priorityIndicator.setBackgroundResource(R.color.colorHigh);
                break;
            case "Medium":
                priorityIndicator.setBackgroundResource(R.color.colorMedium);
                break;
            case "Low":
                priorityIndicator.setBackgroundResource(R.color.colorLow);
                break;
            case "No":
                priorityIndicator.setBackgroundResource(android.R.color.darker_gray);
                break;
        }
        return convertView;
    }
}
