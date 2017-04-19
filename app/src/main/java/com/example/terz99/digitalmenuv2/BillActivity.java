package com.example.terz99.digitalmenuv2;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class BillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        FloatingActionButton requestBillFab =
                (FloatingActionButton) findViewById(R.id.b_request_bill_fab);

        requestBillFab.setImageBitmap(textAsBitmap("BILL", 40, Color.WHITE));

        requestBillFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show an alert dialog to confirm order
                showConfirmationDialog();

            }
        });
    }

    /**
     * This method builds an alert dialog and displays it.
     * This method also sets the functions of the negative and the positive button of the
     * dialog
     */
    private void showConfirmationDialog() {

        AlertDialog.Builder alertDialBuilder = new AlertDialog.Builder(this);

        alertDialBuilder.setMessage(R.string.bill_request_dialog_msg);

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
                Toast.makeText(BillActivity.this, R.string.bill_request_successful, Toast.LENGTH_LONG)
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
}
