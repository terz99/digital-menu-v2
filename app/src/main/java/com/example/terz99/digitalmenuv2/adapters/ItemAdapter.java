package com.example.terz99.digitalmenuv2.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.IntegerRes;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.terz99.digitalmenuv2.Item;
import com.example.terz99.digitalmenuv2.MyOrderActivity;
import com.example.terz99.digitalmenuv2.R;
import com.example.terz99.digitalmenuv2.data.MenuContract;
import com.example.terz99.digitalmenuv2.data.OrderContract;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by terz99 on 3/15/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    // Log tag
    private static final String TAG = ItemAdapter.class.getSimpleName();

    // Context object which stores the Context from where the adapter is created from
    private Context mContext;
    // This boolean represents if the picture is clicked
    private boolean timesclicked = false;

    // List of items to be displayed on the UI
    private ArrayList<Item> mList;


    /**
     * Public constructor used to create ItemAdapter
     * @param context is the Context where this adapter is created in
     * @param list is a list of data which is meant to be displayed on the UI
     */
    public ItemAdapter(Context context, ArrayList<Item> list){
        mContext = context;
        mList = list;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Overridden method which creates (inflates) a brand new item in the UI
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item, parent, false));
    }


    // Overridden method which binds new data to an already created (recycled) view
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        // Get the item on position
        final Item currItem = mList.get(position);

        // Create a final instance of the holder because it is accessed in onClickListeners implemented below
        final ItemViewHolder IVHolder = holder;

        /**
         * Setting data to the views
          */
        holder.counterTextView.setText(String.valueOf(currItem.getmCounter()));
        holder.nameTextView.setText(currItem.getmName());
        if (holder.mLinearLayout.getBackground() == null){

            Bitmap myBitmap = BitmapFactory.decodeResource(mContext.getResources(), currItem.getmImageId());
            if (myBitmap != null && !myBitmap.isRecycled()) {
                Palette palette = Palette.from(myBitmap).generate();

                int muted = palette.getMutedColor(mContext.getResources().getColor(R.color.white));

                holder.mLinearLayout.setBackgroundColor(muted);
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        holder.priceTextView.setText(String.valueOf(decimalFormat.format(currItem.getmPrice())) + "$");
        holder.itemImageView.setMaxHeight(R.dimen.card_height);
        holder.itemImageView.setImageResource(currItem.getmImageId());
        holder.itemImageView.setVerticalFadingEdgeEnabled(true);
        holder.itemImageView.setAdjustViewBounds(true);
        holder.itemImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        holder.nameTextView.setHeight( holder.itemImageView.getHeight()/4 );

        /**
         * Setting onClick Listeners to the buttons
         */
        holder.buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase the counter/quantity of the current clicked item
                currItem.increaseCounter();
                IVHolder.counterTextView.setText(String.valueOf(currItem.getmCounter()));
            }
        });

        holder.buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrease the counter/quantity on the current clicked item if it is not less than one
                if (currItem.getmCounter() > 1){
                    currItem.decreaseCounter();
                    IVHolder.counterTextView.setText(String.valueOf(currItem.getmCounter()));
                }
            }
        });

        holder.mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,  currItem.getmName() + " " + mContext.getString(R.string.add_button_clicked), Toast.LENGTH_LONG).show();


                /**
                 * Insert/update data to the orderr table
                 * This happens when the user wants to order something from the menu
                 * The current added item's data is uploaded to the orderr table and then queried from
                 * MyOrderActivity.java
                 */
                ContentValues contentValues = new ContentValues();

                contentValues.put(OrderContract.OrderEntry.COLUMN_NAME, currItem.getmName());
                contentValues.put(OrderContract.OrderEntry.COLUMN_DESCRIPTION, currItem.getmDescription());
                contentValues.put(OrderContract.OrderEntry.COLUMN_PRICE, String.valueOf(currItem.getmPrice()));
                contentValues.put(OrderContract.OrderEntry.COLUMN_PHOTO_ID, currItem.getmImageId());

                String[] projection = {
                        OrderContract.OrderEntry.COLUMN_NAME,
                        OrderContract.OrderEntry.COLUMN_QUANTITY
                };

                String selection = OrderContract.OrderEntry.COLUMN_NAME + "=?";
                String[] selectionArgs = {currItem.getmName()};

                /**
                 * Check if there is already the same item on the order list
                 * If so, then update the item by adding the new quantity of the item to the previous
                 * Otherwise, insert the new item's data to the order table
                 */
                Cursor checkForItem = mContext.getContentResolver().query(OrderContract.OrderEntry.CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        null);

                if(checkForItem != null && checkForItem.getCount() > 0){

                    checkForItem.moveToNext();
                    int previousQuantity = checkForItem.getInt(checkForItem.getColumnIndex(OrderContract.OrderEntry.COLUMN_QUANTITY));
                    contentValues.put(OrderContract.OrderEntry.COLUMN_QUANTITY, currItem.getmCounter() + previousQuantity);
                    mContext.getContentResolver().update(OrderContract.OrderEntry.CONTENT_URI, contentValues, selection, selectionArgs);
                } else {
                    contentValues.put(OrderContract.OrderEntry.COLUMN_QUANTITY, currItem.getmCounter());
                    mContext.getContentResolver().insert(OrderContract.OrderEntry.CONTENT_URI, contentValues);
                }

                // Close the cursor if the cursor is still open
                if (checkForItem != null) {
                    checkForItem.close();
                }

                Log.e(TAG, "Added " + contentValues.toString());
            }
        });



        holder.dTextview.setText(String.valueOf(currItem.getmDescription()));

        holder.mCardView.setPreventCornerOverlap(false);



        holder.mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timesclicked) {
                    holder.mCardView.setCardElevation(getPixelsFromDPs(8));
                    expand(holder.dCardView);
                    timesclicked = true;
                }
                else {
                    holder.mCardView.setCardElevation(getPixelsFromDPs(2));
                    timesclicked = false;
                    collapse(holder.dCardView);
                }
            }
        });

    }

    private static void expand(final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();


        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(100);
        //a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };


        a.setDuration(100);
        //a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
    private int getPixelsFromDPs(int dps) {

        Resources r = mContext.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;



    }

    // Overridden method which the size of the list
    @Override
    public int getItemCount() {
        return ((mList != null) ? mList.size() : 0);
    }


    /**
     * Public class which is a View Holder
     * All the views from the UI are stored here
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView counterTextView;
        ImageButton buttonDown;
        ImageButton buttonUp;
        LinearLayout mLinearLayout;
        CardView mCardView;
        ImageButton mAddButton;
        LinearLayout mPopDown;
        TextView dTextview;
        CardView dCardView;
        ImageButton mInfoButton;

        /**
         * Public constructor which creates the View Holder and links all the element views from the UI
         * @param itemView is the root view of the item
         */
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
            mPopDown = (LinearLayout) itemView.findViewById(R.id.pop_up_card);
            dCardView = (CardView) itemView.findViewById(R.id.cardview_down);
            dTextview = (TextView) itemView.findViewById(R.id.desctription);
            mInfoButton = (ImageButton) itemView.findViewById(R.id.info_button);

            mCardView.setMaxCardElevation(getPixelsFromDPs(10));
            mCardView.setPreventCornerOverlap(false);
            mCardView.setCardElevation(getPixelsFromDPs(2));
            dCardView.setMaxCardElevation(getPixelsFromDPs(10));
            dCardView.setPreventCornerOverlap(false);
            dCardView.setCardElevation(2);

            dCardView.setVisibility(View.GONE);

            mAddButton = (ImageButton) itemView.findViewById(R.id.add_button);
        }
    }


}