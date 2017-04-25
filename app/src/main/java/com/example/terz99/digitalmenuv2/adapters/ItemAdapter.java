package com.example.terz99.digitalmenuv2.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.drawable.PictureDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.terz99.digitalmenuv2.Item;
import com.example.terz99.digitalmenuv2.R;

import java.text.DecimalFormat;
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

        Bitmap myBitmap = BitmapFactory.decodeResource(mContext.getResources(), currItem.getmImageId());
        if (myBitmap != null && !myBitmap.isRecycled()) {
            Palette palette = Palette.from(myBitmap).generate();

            int vibrant = palette.getVibrantColor(mContext.getResources().getColor(R.color.white));
            int vibrantLight = palette.getLightVibrantColor(mContext.getResources().getColor(R.color.white));
            int muted = palette.getMutedColor(mContext.getResources().getColor(R.color.white));

            holder.mLinearLayout.setBackgroundColor(muted);
        }




        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        holder.priceTextView.setText(String.valueOf(decimalFormat.format(currItem.getmPrice())) + "$");



        holder.itemImageView.setMaxHeight(R.dimen.card_height);
        holder.itemImageView.setImageResource(currItem.getmImageId());
        holder.itemImageView.setVerticalFadingEdgeEnabled(true);
        holder.itemImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        holder.nameTextView.setHeight( holder.itemImageView.getHeight()/4 );

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

        holder.mCardView.setPreventCornerOverlap(false);
    }

    @Override
    public int getItemCount() {
        return ((mList != null) ? mList.size() : 0);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView counterTextView;
        ImageButton buttonDown;
        ImageButton buttonUp;
        LinearLayout mLinearLayout;
        CardView mCardView;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.LL);
            mCardView = (CardView) itemView.findViewById(R.id.CV);
            itemImageView = (ImageView) itemView.findViewById(R.id.picture);
            nameTextView = (TextView) itemView.findViewById(R.id.name_textview);
            priceTextView = (TextView) itemView.findViewById(R.id.price_textview);
            counterTextView = (TextView) itemView.findViewById(R.id.counter_textview);
            buttonUp = (ImageButton) itemView.findViewById(R.id.top_button);
            buttonDown = (ImageButton) itemView.findViewById(R.id.bottom_button);

        }



    }


}