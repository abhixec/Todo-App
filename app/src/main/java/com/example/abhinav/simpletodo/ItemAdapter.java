package com.example.abhinav.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by janabhi on 2/23/17.
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item, parent, false);
        }
        TextView itemText= (TextView) convertView.findViewById(R.id.textView);
        itemText.setText(item.text);
        if(item.priority == "High"){
            itemText.setAllCaps(true);
            itemText.setTextColor(Color.RED);
        }
        else if(item.priority == "Medium"){
            itemText.setTextColor(Color.MAGENTA);
        }
        else{
            itemText.setTextColor(Color.DKGRAY);
        }
        return convertView;
    }
}
