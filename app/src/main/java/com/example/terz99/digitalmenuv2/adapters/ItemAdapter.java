package com.example.terz99.digitalmenuv2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.terz99.digitalmenuv2.Item;
import com.example.terz99.digitalmenuv2.R;

import java.util.ArrayList;

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

        final Item currItem = mList.get(position);

        final ItemViewHolder IVHolder = holder;

        holder.counterTextView.setText(String.valueOf(currItem.getmCounter()));

        holder.nameTextView.setText(currItem.getmName());

        holder.priceTextView.setText(String.valueOf(currItem.getmPrice()));

        holder.itemImageView.setImageResource(currItem.getmImageId());

        holder.buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currItem.increaseCounter();
                IVHolder.counterTextView.setText(String.valueOf(currItem.getmCounter()));
            }
        });

        holder.buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currItem.getmCounter() > 1){
                    currItem.decreaseCounter();
                    IVHolder.counterTextView.setText(String.valueOf(currItem.getmCounter()));
                }
            }
        });
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
        ImageButton buttonDown;
        ImageButton buttonUp;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            itemImageView = (ImageView) itemView.findViewById(R.id.item_imageview);
            nameTextView = (TextView) itemView.findViewById(R.id.name_textview);
            priceTextView = (TextView) itemView.findViewById(R.id.price_textview);
            counterTextView = (TextView) itemView.findViewById(R.id.counter_textview);
            buttonUp = (ImageButton) itemView.findViewById(R.id.top_button);
            buttonDown = (ImageButton) itemView.findViewById(R.id.bottom_button);
        }



    }


}