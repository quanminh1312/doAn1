package com.example.doan1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    //    Comic
    public ItemAdapter(Context context, ArrayList<Item> items){
        super(context, 0 ,items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_comic_list, parent, false);
        }
        //Get the current item object
        Item currentItem = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.image_view);
        TextView nametextView = convertView.findViewById(R.id.text_view_name);

        imageView.setImageResource(currentItem.getImageResId());
        nametextView.setText(currentItem.getName());
        return convertView;
    }
}


