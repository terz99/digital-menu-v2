/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.terz99.digitalmenuv2;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.terz99.digitalmenuv2.adapters.CategoryAdapter;
import com.example.terz99.digitalmenuv2.data.MenuContract;
import com.example.terz99.digitalmenuv2.data.MenuDbHelper;
import com.example.terz99.digitalmenuv2.fragments.CocktailFragment;
import com.example.terz99.digitalmenuv2.fragments.PizzaFragment;
import com.example.terz99.digitalmenuv2.fragments.WineFragment;

import java.util.ArrayList;

import static com.example.terz99.digitalmenuv2.fragments.CocktailFragment.*;
import static com.example.terz99.digitalmenuv2.fragments.PizzaFragment.*;
import static com.example.terz99.digitalmenuv2.fragments.WineFragment.*;

public class MainActivity extends AppCompatActivity{

    // ID for the wine loader
    public static final int WINE_LOADER_ID = 2;
    // ID for the cocktail loader
    public static final int COCKTAIL_LOADER_ID = 3;
    // ID for the pizza loader
    public static final int PIZZA_LOADER_ID = 1;
    // ID for the insert loader
    private static final int MENU_LOADER_ID = 0;
    // Helper ArrayList<Item> to store data for a short amount of time
    private ArrayList<Item> mItems;

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {

        // create the MainActivity layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkDataBaseVersion()){
            addData();
        }

        try {
            if(PizzaFragment.mData == null || PizzaFragment.mData.getCount() == 0)
                getSupportLoaderManager().initLoader(PIZZA_LOADER_ID, null, fetchLoaderCallbacks);
            if(WineFragment.mData == null || WineFragment.mData.getCount() == 0)
                getSupportLoaderManager().initLoader(WINE_LOADER_ID, null, fetchLoaderCallbacks);
            if(CocktailFragment.mData == null || CocktailFragment.mData.getCount() == 0)
                getSupportLoaderManager().initLoader(COCKTAIL_LOADER_ID, null, fetchLoaderCallbacks);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // get the viewpager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // declare a new adapter for the viewpager
        CategoryAdapter pagerAdapter = new CategoryAdapter(getSupportFragmentManager(), this);
        // set the adapter to the viewpager
        viewPager.setAdapter(pagerAdapter);
        // get the tabLayout resource
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        // set tabLayout in sync with the viewpager
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * This method adds the data to the newer version of the database
     * This is a beta-version of the app method, it will not be used in the real version
     */
    private void addData() {

        mItems = new ArrayList<Item>();

        // Adding pizzas
        mItems.add(new Item("Pizza Amerikano", 19.20, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));
        mItems.add(new Item("Pizza Amerikano", 17.00, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));
        mItems.add(new Item("Pizza Amerikano", 18.90, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));
        mItems.add(new Item("Pizza Amerikano", 19.00, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));
        mItems.add(new Item("Pizza Amerikano", 16.00, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));
        mItems.add(new Item("Pizza Amerikano", 16.70, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));
        mItems.add(new Item("Pizza Amerikano", 18.80, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));
        mItems.add(new Item("Pizza Amerikano", 16.40, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));
        mItems.add(new Item("Pizza Amerikano", 17.80, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));
        mItems.add(new Item("Pizza Amerikano", 19.30, "Salami, cheese, mushrooms", R.drawable.pizza_amerikano, PIZZA_ID));

        // Adding cocktails
        mItems.add(new Item("Lavender White Lady", 10.60, "4cl - Appleton Estate Rum, " +
                "Orange curacao, Lime juice, Orgeat, Grenadine, Pineapple juice",
                R.drawable.lavender_white_lady,
                COCKTAIL_ID));
        mItems.add(new Item("Absolut Gangster", 12.60, "4 cl - Absolut Elyx, " +
                "Lillet, " +
                "Sugar Syrup, " +
                "Vanilla Bitters", R.drawable.absolut_gangster, COCKTAIL_ID));
        mItems.add(new Item("Lavender Spring Sour", 11.40, "4cl - Vodka, Orange Juice, Lime.",
                R.drawable.lavender_spring_sour,
                COCKTAIL_ID));
        mItems.add(new Item("Hendrick's Summer Garden", 14.60, "4cl - Vodka, rum, brandy.",
                R.drawable.henrick_summer_gardem,
                COCKTAIL_ID));
        mItems.add(new Item("Mai Tai", 11.20, "4cl - Appleton 15 Year Old Rum" +
                "Lime juice, " +
                "Orgeat, " +
                "Sugar syrup, " +
                "Orange curacao, " +
                "1 Mint leaf.", R.drawable.mai_tai, COCKTAIL_ID));
        mItems.add(new Item("Lychee Collins", 10.50, "4cl - Beefeater Gin, " +
                "Lychee liqueur, " +
                "Lychee juice.", R.drawable.lychee_collins,
                COCKTAIL_ID));
        mItems.add(new Item("Passion Rum Punch", 10.40, "4cl - Appleton Estate Rum, " +
                "Orange curacao, " +
                "Lime juice, " +
                "Orgeat, " +
                "Grenadine, " +
                "Pineapple juice.", R.drawable.passion_rum_punch,
                COCKTAIL_ID));
        mItems.add(new Item("El Hefe", 12.70, "4cl - Plantation Guatemala & Belize Rum" +
                "Lillet, " +
                "Maraschino Syrup, " +
                "Dry Curacao.", R.drawable.el_jefe, COCKTAIL_ID));
        mItems.add(new Item("Wild Berry Spritzer", 10.00, "4cl - Vodka, rum, brandy",
                R.drawable.wild_berry_spritzer,
                COCKTAIL_ID));
        mItems.add(new Item("Hunter's Grog", 12.90, "Grapefruit Juice, " +
                "Lemon Juice, " +
                "Bitter Orange Marmalade, " +
                "Ginger Beer.", R.drawable.hunter_grog,
                COCKTAIL_ID));

        // Adding wines
        mItems.add(new Item("Reisling", 33.75, "750ml", R.drawable.pizza_amerikano, WINE_ID));
        mItems.add(new Item("Ge", 32.30, "750ml", R.drawable.pizza_amerikano, WINE_ID));
        mItems.add(new Item("Chardonnay", 31.90, "750ml", R.drawable.pizza_amerikano, WINE_ID));
        mItems.add(new Item("Sauvigon Blanc", 24.50, "750ml", R.drawable.pizza_amerikano, WINE_ID));
        mItems.add(new Item("Syrah", 22.60, "750ml", R.drawable.pizza_amerikano, WINE_ID));
        mItems.add(new Item("Merlot", 23.60, "750ml", R.drawable.pizza_amerikano, WINE_ID));
        mItems.add(new Item("Cabernet Saubignon", 25.20, "750ml", R.drawable.pizza_amerikano, WINE_ID));
        mItems.add(new Item("Pinot Noir", 31.90, "750ml", R.drawable.pizza_amerikano, WINE_ID));

        getSupportLoaderManager().initLoader(MENU_LOADER_ID, null, uploadLoaderCallbacks);
    }

    /**
     * @return if there is a newer version of the database
     */
    private boolean checkDataBaseVersion() {

        Log.e(TAG, "Checking database version...");

        // Get link from the shared preferences. The database version is stored in shared
        // preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Get the current version stored in the shared preferences
        int databaseVersionFromPreferences = Integer.parseInt(
                preferences.getString(getString(R.string.database_version_key),
                        getString(R.string.database_version_default_value)));

        // See the current version stored in the shared preferences is equal to the
        // current version of the database. If so, then update the shared preferences
        // and return true
        if(databaseVersionFromPreferences != MenuDbHelper.getDatabaseVersion()){
            databaseVersionFromPreferences = MenuDbHelper.getDatabaseVersion();
            SharedPreferences.Editor preferenceEditor = preferences.edit();
            preferenceEditor.putString(getString(R.string.database_version_key),
                    String.valueOf(databaseVersionFromPreferences));
            preferenceEditor.apply();
            Log.e(TAG, "" + databaseVersionFromPreferences);
            return true;
        } else {
            return false;
        }
    }

    // Overriden method which inflates the options menu on the App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Overriden method which is called whenever a options menu item is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Get the id of the clicked item
        int clickedItemId = item.getItemId();

        // Check which item was clicked and open adequate activity according to the item clicked
        switch (clickedItemId){

            case R.id.action_bill:

                Intent openBillActivityIntent = new Intent(this, BillActivity.class);
                startActivity(openBillActivityIntent);
                return true;
            case R.id.action_my_order:

                Intent openMyOrderActivityIntent = new Intent(this, MyOrderActivity.class);
                startActivity(openMyOrderActivityIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> fetchLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {


            Log.e(TAG, "" + id);

            String[] projection;
            String[] selectionArgs;
            String selection;

            switch (id){

                case MainActivity.PIZZA_LOADER_ID:

                    projection = new String[]{
                            MenuContract.MenuEntry.COLUMN_NAME,
                            MenuContract.MenuEntry.COLUMN_DESCRIPTION,
                            MenuContract.MenuEntry.COLUMN_PRICE,
                            MenuContract.MenuEntry.COLUMN_PHOTO_ID
                    };

                    selection = MenuContract.MenuEntry.COLUMN_CATEGORY_ID + "=?";

                    selectionArgs = new String[]{String.valueOf(PizzaFragment.PIZZA_ID)};

                    return new CursorLoader(MainActivity.this,
                            MenuContract.MenuEntry.CONTENT_URI,
                            projection,
                            selection,
                            selectionArgs,
                            null);

                case MainActivity.WINE_LOADER_ID:

                    projection = new String[]{
                            MenuContract.MenuEntry.COLUMN_NAME,
                            MenuContract.MenuEntry.COLUMN_DESCRIPTION,
                            MenuContract.MenuEntry.COLUMN_PRICE,
                            MenuContract.MenuEntry.COLUMN_PHOTO_ID
                    };

                    selection = MenuContract.MenuEntry.COLUMN_CATEGORY_ID + "=?";

                    selectionArgs = new String[]{String.valueOf(WineFragment.WINE_ID)};

                    return new CursorLoader(MainActivity.this,
                            MenuContract.MenuEntry.CONTENT_URI,
                            projection,
                            selection,
                            selectionArgs,
                            null);

                case MainActivity.COCKTAIL_LOADER_ID:

                    projection = new String[]{
                            MenuContract.MenuEntry.COLUMN_NAME,
                            MenuContract.MenuEntry.COLUMN_DESCRIPTION,
                            MenuContract.MenuEntry.COLUMN_PRICE,
                            MenuContract.MenuEntry.COLUMN_PHOTO_ID
                    };

                    selection = MenuContract.MenuEntry.COLUMN_CATEGORY_ID + "=?";

                    selectionArgs = new String[]{String.valueOf(CocktailFragment.COCKTAIL_ID)};

                    return new CursorLoader(MainActivity.this,
                            MenuContract.MenuEntry.CONTENT_URI,
                            projection,
                            selection,
                            selectionArgs,
                            null);

                default:
                    throw new IllegalArgumentException("Unknown loader id " + id);
            }
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            switch (loader.getId()){

                case PIZZA_LOADER_ID:
                    Log.e(TAG, "Added pizza data");
                    PizzaFragment.mData = data;
                    break;
                case WINE_LOADER_ID:
                    Log.e(TAG, "Added wine data");
                    WineFragment.mData = data;
                    break;
                case COCKTAIL_LOADER_ID:
                    Log.e(TAG, "Added cocktail data");
                    CocktailFragment.mData = data;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown loader id " + loader.getId());
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            // Do nothing
        }
    };

    private LoaderManager.LoaderCallbacks<Integer> uploadLoaderCallbacks = new LoaderManager.LoaderCallbacks<Integer>() {
        @Override
        public Loader<Integer> onCreateLoader(int id, Bundle args) {
            return new UploadDataTask(MainActivity.this, mItems);
        }

        @Override
        public void onLoadFinished(Loader<Integer> loader, Integer data) {
            // Do nothing
        }

        @Override
        public void onLoaderReset(Loader<Integer> loader) {
            // Do nothing
        }
    };
}