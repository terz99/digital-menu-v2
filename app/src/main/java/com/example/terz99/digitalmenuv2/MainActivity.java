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
import android.widget.Toast;

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

    // ID for query loader
    private static final int FETCH_DATA_LOADER_ID = 5;
    // ID for the insert loader
    private static final int MENU_LOADER_ID = 0;
    // Helper ArrayList<Item> to store data for a short amount of time
    private ArrayList<Item> mItems;
    public static ArrayList<Item> mPizzaData;
    public static ArrayList<Item> mWineData;
    public static ArrayList<Item> mCocktailData;

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {

        // create the MainActivity layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkDataBaseVersion()){
            addData();
        } else {
            if(mPizzaData == null || mPizzaData.size() == 0 || mWineData == null || mWineData.size() == 0 || mCocktailData == null || mCocktailData.size() == 0)
                getSupportLoaderManager().initLoader(FETCH_DATA_LOADER_ID, null, fetchLoaderCallbacks);
            else
                setupContent();
        }
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

            switch (id){

                case FETCH_DATA_LOADER_ID:

                    String[] projection = {
                            MenuContract.MenuEntry.COLUMN_NAME,
                            MenuContract.MenuEntry.COLUMN_DESCRIPTION,
                            MenuContract.MenuEntry.COLUMN_PRICE,
                            MenuContract.MenuEntry.COLUMN_PHOTO_ID,
                            MenuContract.MenuEntry.COLUMN_CATEGORY_ID
                    };

                    return new CursorLoader(MainActivity.this,
                            MenuContract.MenuEntry.CONTENT_URI,
                            projection,
                            null,
                            null,
                            null);

                default:
                    throw new IllegalArgumentException("Unknown loader id " + id);
            }
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            mCocktailData = new ArrayList<Item>();
            mWineData = new ArrayList<Item>();
            mPizzaData = new ArrayList<Item>();

            while(data.moveToNext()){

                String name = data.getString(data.getColumnIndex(MenuContract.MenuEntry.COLUMN_NAME));
                String description = data.getString(data.getColumnIndex(MenuContract.MenuEntry.COLUMN_DESCRIPTION));
                double price = Double.parseDouble(data.getString(data.getColumnIndex(MenuContract.MenuEntry.COLUMN_PRICE)));
                int imageId = data.getInt(data.getColumnIndex(MenuContract.MenuEntry.COLUMN_PHOTO_ID));

                int categoryId = data.getInt(data.getColumnIndex(MenuContract.MenuEntry.COLUMN_CATEGORY_ID));

                switch (categoryId){

                    case PIZZA_ID:
                        mPizzaData.add(new Item(name, price, description, imageId, categoryId));
                        break;
                    case WINE_ID:
                        mWineData.add(new Item(name, price, description, imageId, categoryId));
                        break;
                    case COCKTAIL_ID:
                        mCocktailData.add(new Item(name, price, description, imageId, categoryId));
                        break;
                }
            }

            setupContent();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            // Do nothing
        }
    };

    private void setupContent() {

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

    private LoaderManager.LoaderCallbacks<Integer> uploadLoaderCallbacks = new LoaderManager.LoaderCallbacks<Integer>() {
        @Override
        public Loader<Integer> onCreateLoader(int id, Bundle args) {
            return new UploadDataTask(MainActivity.this, mItems);
        }

        @Override
        public void onLoadFinished(Loader<Integer> loader, Integer data) {
            getSupportLoaderManager().initLoader(FETCH_DATA_LOADER_ID, null, fetchLoaderCallbacks);
        }

        @Override
        public void onLoaderReset(Loader<Integer> loader) {
            // Do nothing
        }
    };
}