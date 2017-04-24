package com.example.terz99.digitalmenuv2;

/**
 * Created by terz99 on 4/17/17.
 */

public class Item {

    private String mName;
    private double mPrice;
    private String mDescription;
    private int mImageId;
    private int mCounter;
    private int mCategoryId;

    public Item(String mName, double mPrice, String mDescription, int mImageId, int mCategoryId) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mDescription = mDescription;
        this.mImageId = mImageId;
        mCounter = 1;
        this.mCategoryId = mCategoryId;
    }

    public String getmName() {
        return mName;
    }

    public double getmPrice() {
        return mPrice;
    }

    public String getmDescription() {
        return mDescription;
    }

    public int getmImageId() {
        return mImageId;
    }

    public int getmCounter() {
        return mCounter;
    }

    public int getmCategoryId() {
        return mCategoryId;
    }

    public void setmCounter(int mCounter) {
        this.mCounter = mCounter;
    }

    public void increaseCounter() {
        this.mCounter = mCounter+1;
    }

    public void decreaseCounter() {
        this.mCounter = mCounter-1;
    }
}
