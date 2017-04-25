package com.example.terz99.digitalmenuv2;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.terz99.digitalmenuv2.adapters.OrderAdapter;
import com.example.terz99.digitalmenuv2.data.OrderContract;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class MyOrderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ORDER_LOADER_ID = 10;

    private RecyclerView mRecyclerView;

    private OrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        // Get link from the floating action button
        FloatingActionButton oSubmitOrderFab =
                (FloatingActionButton) findViewById(R.id.o_submit_order_fab);

        // Set the floating action button to say OK instead of having a picture
        // we call textAsBitmap() method because the setImageBitmap accepts only bitmap as a
        // parameter and textAsBitmap returns some text in type of Bitmaps
        oSubmitOrderFab.setImageBitmap(textAsBitmap("OK", 40, Color.WHITE));


        // Set on click listener to the order button
        oSubmitOrderFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show an alert dialog to confirm order
                showConfirmationDialog();

            }
        });

        getSupportLoaderManager().initLoader(ORDER_LOADER_ID, null, this);
    }

    /**
     * This method builds an alert dialog and displays it.
     * This method also sets the functions of the negative and the positive button of the
     * dialog
     */
    private void showConfirmationDialog() {

        AlertDialog.Builder alertDialBuilder = new AlertDialog.Builder(this);

        alertDialBuilder.setMessage(R.string.confirmation_dialog_msg);

        alertDialBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });

        alertDialBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MyOrderActivity.this, R.string.order_successful, Toast.LENGTH_LONG)
                        .show();
                finish();
            }
        });

        AlertDialog alertDialog = alertDialBuilder.create();
        alertDialog.show();
    }

    /**
     * This method transforms some text into a Bitmap object
     * @param text the text which will be transformed into a Bitmap Object
     * @param textSize the size of the text
     * @param textColor the color of the text
     * @return a Bitmap object displaying text
     */
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id){

            case ORDER_LOADER_ID:

                String[] projection = {
                        OrderContract.OrderEntry.COLUMN_NAME,
                        OrderContract.OrderEntry.COLUMN_PRICE,
                        OrderContract.OrderEntry.COLUMN_QUANTITY,
                        OrderContract.OrderEntry.COLUMN_PHOTO_ID
                };

                return new CursorLoader(this,
                        OrderContract.OrderEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);

            default:
                throw new IllegalArgumentException("Unknown loader id: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()){

            case ORDER_LOADER_ID:
                setupContent(data);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    void setupContent(Cursor cursor){

        mRecyclerView = (RecyclerView) findViewById(R.id.o_listview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new OrderAdapter(this, cursor);

        mRecyclerView.setAdapter(mAdapter);
    }
}
