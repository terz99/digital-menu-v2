package com.example.terz99.digitalmenuv2.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.terz99.digitalmenuv2.Item;
import com.example.terz99.digitalmenuv2.OrderItem;
import com.example.terz99.digitalmenuv2.R;
import com.example.terz99.digitalmenuv2.data.OrderContract;

import java.util.ArrayList;

/**
 * Created by terz99 on 4/25/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder>{

    private Context mContext;

    private ArrayList<OrderItem> mData;

    public OrderAdapter(Context context, ArrayList<OrderItem> data){
        mContext = context;
        mData = data;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {

        OrderItem currOrderItem = mData.get(position);

        holder.imageView.setImageResource(currOrderItem.getmImageId());

        holder.nameTextView.setText(currOrderItem.getmName());

        holder.quantityTextView.setText(String.valueOf(currOrderItem.getmQuantity()));

        double fullPrice = currOrderItem.getmPrice()*(double)currOrderItem.getmQuantity();

        holder.priceTextView.setText(String.valueOf(fullPrice) + "$");
    }

    @Override
    public int getItemCount() {
        return ((mData != null) ? mData.size() : 0);
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{

        TextView quantityTextView;
        TextView nameTextView;
        ImageView imageView;
        TextView priceTextView;

        public OrderItemViewHolder(View itemView) {
            super(itemView);

            quantityTextView = (TextView) itemView.findViewById(R.id.o_quantity);
            nameTextView = (TextView) itemView.findViewById(R.id.o_text);
            priceTextView = (TextView) itemView.findViewById(R.id.o_price);
            imageView = (ImageView) itemView.findViewById(R.id.o_image);
        }
    }
}
