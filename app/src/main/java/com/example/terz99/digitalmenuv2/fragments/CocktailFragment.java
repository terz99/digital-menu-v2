package com.example.terz99.digitalmenuv2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.terz99.digitalmenuv2.Item;
import com.example.terz99.digitalmenuv2.R;
import com.example.terz99.digitalmenuv2.adapters.ItemAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CocktailFragment extends Fragment {


    private static final int COCKTAIL_ID = 1;

    private static final String TAG = CocktailFragment.class.getSimpleName();

    private ArrayList<Item> cacheList = null;
    private ArrayList<Item> words;

    public CocktailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);



        // ArrayList of the object Word which stores the data
        if(cacheList != null){
            words = cacheList;
            cacheList = null;
        } else {
            words = addData();
        }

        // A custom adapter (WordAdapter) which helps us arrange the items in the ListView
        final ItemAdapter adapter = new ItemAdapter(getContext(), words);
        RecyclerView listView = (RecyclerView) rootView.findViewById(R.id.listview);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listView.setAdapter(adapter);

        Log.v(TAG, TAG + " has been created");

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, TAG + " has stopped");
        cacheList = words;
    }

    /**
     * This method returns the data grabbed from the resource files
     * @return words - an ArrayList of the object Item
     */
    private ArrayList<Item> addData() {

        ArrayList<Item> words = new ArrayList<Item>();
        words.add(new Item("Lavender White Lady", 10.60, "4cl - Appleton Estate Rum, " +
                "Orange curacao, Lime juice, Orgeat, Grenadine, Pineapple juice", R.mipmap.ic_launcher,
                COCKTAIL_ID));
        words.add(new Item("Absolut Gangster", 12.60, "4 cl - Absolut Elyx, " +
                "Lillet, " +
                "Sugar Syrup, " +
                "Vanilla Bitters", R.mipmap.ic_launcher, COCKTAIL_ID));
        words.add(new Item("Lavender Spring Sour", 11.40, "4cl - Vodka, Orange Juice, Lime.", R.mipmap.ic_launcher,
                COCKTAIL_ID));
        words.add(new Item("Hendrick's Summer Garden", 14.60, "4cl - Vodka, rum, brandy.", R.mipmap.ic_launcher,
                COCKTAIL_ID));
        words.add(new Item("Mai Tai", 11.20, "4cl - Appleton 15 Year Old Rum" +
                "Lime juice, " +
                "Orgeat, " +
                "Sugar syrup, " +
                "Orange curacao, " +
                "1 Mint leaf.", R.mipmap.ic_launcher, COCKTAIL_ID));
        words.add(new Item("Lychee Collins", 10.50, "4cl - Beefeater Gin, " +
                "Lychee liqueur, " +
                "Lychee juice.", R.mipmap.ic_launcher,
                COCKTAIL_ID));
        words.add(new Item("Passion Rum Punch", 10.40, "4cl - Appleton Estate Rum, " +
                "Orange curacao, " +
                "Lime juice, " +
                "Orgeat, " +
                "Grenadine, " +
                "Pineapple juice.", R.mipmap.ic_launcher,
                COCKTAIL_ID));
        words.add(new Item("El Hefe", 12.70, "4cl - Plantation Guatemala & Belize Rum" +
                "Lillet, " +
                "Maraschino Syrup, " +
                "Dry Curacao.", R.mipmap.ic_launcher, COCKTAIL_ID));
        words.add(new Item("Wild Berry Spritzer", 10.00, "Vodka, rum, brandy", R.mipmap.ic_launcher,
                COCKTAIL_ID));
        words.add(new Item("Hunter's Grog", 12.90, "Grapefruit Juice, " +
                "Lemon Juice, " +
                "Bitter Orange Marmalade, " +
                "Ginger Beer.", R.mipmap.ic_launcher,
                COCKTAIL_ID));
        return words;
    }

}
