package com.example.terz99.digitalmenuv2;

import android.content.Context;

/**
 * Created by User on 27.4.2017.
 */

public class BillRequest {

    private Context mContext;

    private boolean isBillRequested;

    public BillRequest(Context mContext, boolean isBillRequested) {
        this.isBillRequested = isBillRequested;
        this.mContext = mContext;
    }

    public boolean isBillRequested() {
        return isBillRequested;
    }

    public void setBillRequested(boolean billRequested) {
        isBillRequested = billRequested;
    }
}
