package com.example.terz99.digitalmenuv2;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.res.Resources;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by terz99 on 3/15/17.
 */

public class ItemAdapter extends ArrayAdapter<Item>{


    // this instance of the Object Context stores the Activity
    // where we come from
    private Context context;

    // this variable stores the background color of the
    // respective category
    private int backgroundColorResourceId;

    /*
        Constructor
        @param: context - the activity to where the items in the list are going to be displayed
        @param: words - the data which is going to be displayed
     */
    public ItemAdapter(Context context, ArrayList<Item> words){
        /*
            We pass all the data to the super constructor and we write the layout as 0 since we
            do not need it
         */
        super(context, 0, words);
        this.context = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            /*
                Using view from the scrap views (reusable views)
             */
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        /*
            Finding the Views from the listItemView
         */
        Item currentWord = getItem(position);

        ImageView itemImageView = (ImageView) listItemView.findViewById(R.id.item_imageview);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name_textview);
        TextView priceTextView = (TextView) listItemView.findViewById(R.id.price_textview);
        TextView counterTextView = (TextView) listItemView.findViewById(R.id.counter_textview);

        itemImageView.setImageResource(currentWord.getmImageId());
        nameTextView.setText(currentWord.getmName());
        priceTextView.setText(String.valueOf(currentWord.getmPrice()));
        counterTextView.setText(String.valueOf(currentWord.getmCounter()));

        return listItemView;
    }
}