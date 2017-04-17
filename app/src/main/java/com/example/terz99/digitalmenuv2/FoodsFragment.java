package com.example.terz99.digitalmenuv2;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodsFragment extends Fragment {



    public FoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);



        // ArrayList of the object Word which stores the data
        ArrayList<Item> words = addData();

        // A custom adapter (WordAdapter) which helps us arrange the items in the ListView
        final ItemAdapter adapter = new ItemAdapter(getActivity(), words);
        ListView listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        

        return rootView;
    }

 


    /**
     * This method returns the data grabbed from the resource files
     * @return words - an ArrayList of the object Item
     */
    private ArrayList<Item> addData() {

        ArrayList<Item> words = new ArrayList<Item>();
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        words.add(new Item("Pizza", 15.65, "Salami, cheese, mushrooms", R.mipmap.ic_launcher));
        return words;
    }
}