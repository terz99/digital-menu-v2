package com.example.terz99.digitalmenuv2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WineFragment extends Fragment {


    private static final String TAG = WineFragment.class.getSimpleName();

    private ArrayList<Item> cacheList = null;
    private ArrayList<Item> words;

    public WineFragment() {
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
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        words.add(new Item("Merlot", 8.45, "750ml", R.mipmap.ic_launcher));
        return words;
    }

}
