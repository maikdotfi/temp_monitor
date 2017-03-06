package com.example.temp_monitor;


import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mike on 6.3.2017.
 */

public class MyListFragment extends ListFragment implements ListView.OnItemClickListener {
    String test = "testi fail";
    ArrayList<String> datelist;
    String[] dates;
    ArrayAdapter myAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        //ListView lvdatelist = (ListView) view.findViewById(R.id.lvdatelist);
        //datelist = new ArrayList<String>();
        //dates = new String[datelist.size()];
        //dates = datelist.toArray(dates);
        //Log.d("MyListFragment", "dates = " + dates.length);
        //myAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, dates);
        //lvdatelist.setAdapter(myAdapter);
        //setListAdapter(myAdapter);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
    public void getList(String[] s){
        Log.d("MyListFragment", "Testi string = " + s.length);
        //datelist.add("testi");
        dates = s;
        myAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, dates);
        setListAdapter(myAdapter);
    }
}
