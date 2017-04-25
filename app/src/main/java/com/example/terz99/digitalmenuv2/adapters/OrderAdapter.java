package com.example.terz99.digitalmenuv2.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.terz99.digitalmenuv2.Item;
import com.example.terz99.digitalmenuv2.OrderItem;
import com.example.terz99.digitalmenuv2.R;
import com.example.terz99.digitalmenuv2.data.OrderContract;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by terz99 on 4/25/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder>{

    private Context mContext;

    private ArrayList<OrderItem> mData;

    private boolean timesclicked = false;

    public OrderAdapter(Context context, ArrayList<OrderItem> data){
        mContext = context;
        mData = data;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false));
    }


    private int getPixelsFromDPs(int dps) {

        Resources r = mContext.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;

    }

    @Override
    public void onBindViewHolder(final OrderItemViewHolder holder, int position) {

        OrderItem currOrderItem = mData.get(position);
        final OrderAdapter.OrderItemViewHolder OVHolder = holder;

        holder.imageView.setImageResource(currOrderItem.getmImageId());

        holder.nameTextView.setText(currOrderItem.getmName());

        holder.quantityTextView.setText(String.valueOf(currOrderItem.getmQuantity()));

        double fullPrice = currOrderItem.getmPrice()*(double)currOrderItem.getmQuantity();


        if (holder.mLinearLayout.getBackground() == null){

            Bitmap myBitmap = BitmapFactory.decodeResource(mContext.getResources(), currOrderItem.getmImageId());
            if (myBitmap != null && !myBitmap.isRecycled()) {
                Palette palette = Palette.from(myBitmap).generate();

                int muted = palette.getMutedColor(mContext.getResources().getColor(R.color.white));

                holder.mLinearLayout.setBackgroundColor(muted);
            }
        }


        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timesclicked) {
                    holder.mCardView.setCardElevation(getPixelsFromDPs(10));
                    timesclicked = true;
                }
                else {
                    holder.mCardView.setCardElevation(getPixelsFromDPs(2));
                    timesclicked = false;
                }
            }
        });

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        holder.priceTextView.setText(String.valueOf(decimalFormat.format(fullPrice)) + "$");
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
        CardView mCardView;
        LinearLayout mLinearLayout;

        public OrderItemViewHolder(View itemView) {
            super(itemView);

            quantityTextView = (TextView) itemView.findViewById(R.id.o_quantity);
            nameTextView = (TextView) itemView.findViewById(R.id.o_text);
            priceTextView = (TextView) itemView.findViewById(R.id.o_price);
            imageView = (ImageView) itemView.findViewById(R.id.o_image);
            mCardView = (CardView) itemView.findViewById(R.id.o_CV);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.o_LL);


            mCardView.setMaxCardElevation(getPixelsFromDPs(10));
            mCardView.setPreventCornerOverlap(false);
            mCardView.setCardElevation(getPixelsFromDPs(2));
        }
    }
}
