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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.terz99.digitalmenuv2.adapters.CategoryAdapter;
import com.example.terz99.digitalmenuv2.data.MenuDbHelper;
import com.example.terz99.digitalmenuv2.fragments.CocktailFragment;

import java.util.ArrayList;

import static com.example.terz99.digitalmenuv2.fragments.CocktailFragment.*;

public class MainActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {

        // create the MainActivity layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkDataBaseVersion()){
            addData();
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

        ArrayList<Item> words = new ArrayList<Item>();
        words.add(new Item("Lavender White Lady", 10.60, "4cl - Appleton Estate Rum, " +
                "Orange curacao, Lime juice, Orgeat, Grenadine, Pineapple juice",
                R.drawable.lavender_white_lady,
                COCKTAIL_ID));
        words.add(new Item("Absolut Gangster", 12.60, "4 cl - Absolut Elyx, " +
                "Lillet, " +
                "Sugar Syrup, " +
                "Vanilla Bitters", R.drawable.absolut_gangster, COCKTAIL_ID));
        words.add(new Item("Lavender Spring Sour", 11.40, "4cl - Vodka, Orange Juice, Lime.",
                R.drawable.lavender_spring_sour,
                COCKTAIL_ID));
        words.add(new Item("Hendrick's Summer Garden", 14.60, "4cl - Vodka, rum, brandy.",
                R.drawable.henrick_summer_gardem,
                COCKTAIL_ID));
        words.add(new Item("Mai Tai", 11.20, "4cl - Appleton 15 Year Old Rum" +
                "Lime juice, " +
                "Orgeat, " +
                "Sugar syrup, " +
                "Orange curacao, " +
                "1 Mint leaf.", R.drawable.mai_tai, COCKTAIL_ID));
        words.add(new Item("Lychee Collins", 10.50, "4cl - Beefeater Gin, " +
                "Lychee liqueur, " +
                "Lychee juice.", R.drawable.lychee_collins,
                COCKTAIL_ID));
        words.add(new Item("Passion Rum Punch", 10.40, "4cl - Appleton Estate Rum, " +
                "Orange curacao, " +
                "Lime juice, " +
                "Orgeat, " +
                "Grenadine, " +
                "Pineapple juice.", R.drawable.passion_rum_punch,
                COCKTAIL_ID));
        words.add(new Item("El Hefe", 12.70, "4cl - Plantation Guatemala & Belize Rum" +
                "Lillet, " +
                "Maraschino Syrup, " +
                "Dry Curacao.", R.drawable.el_jefe, COCKTAIL_ID));
        words.add(new Item("Wild Berry Spritzer", 10.00, "4cl - Vodka, rum, brandy",
                R.drawable.wild_berry_spritzer,
                COCKTAIL_ID));
        words.add(new Item("Hunter's Grog", 12.90, "Grapefruit Juice, " +
                "Lemon Juice, " +
                "Bitter Orange Marmalade, " +
                "Ginger Beer.", R.drawable.hunter_grog,
                COCKTAIL_ID));
    }

    /**
     * @return if there is a newer version of the database
     */
    private boolean checkDataBaseVersion() {

        // Get link from the shared preferences. The database version is stored in shared
        // preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(preferences.contains(getString(R.string.database_version_key))){

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
                return true;
            }
        }
        // Otherwise, return false
        return false;
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
}