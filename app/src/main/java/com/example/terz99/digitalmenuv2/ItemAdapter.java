package com.example.terz99.digitalmenuv2;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private Context mContext;

    private ArrayList<Item> mList;

    public ItemAdapter(Context context, ArrayList<Item> list){
        mContext = context;
        mList = list;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        Item currItem = mList.get(position);

        holder.counterTextView.setText(String.valueOf(currItem.getmCounter()));

        holder.nameTextView.setText(currItem.getmName());

        holder.priceTextView.setText(String.valueOf(currItem.getmPrice()));

        holder.itemImageView.setImageResource(currItem.getmImageId());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView counterTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            itemImageView = (ImageView) itemView.findViewById(R.id.item_imageview);
            nameTextView = (TextView) itemView.findViewById(R.id.name_textview);
            priceTextView = (TextView) itemView.findViewById(R.id.price_textview);
            counterTextView = (TextView) itemView.findViewById(R.id.counter_textview);
        }
    }
}