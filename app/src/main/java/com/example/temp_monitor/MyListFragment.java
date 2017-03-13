package com.example.temp_monitor;


import android.content.Intent;
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

public class MyListFragment extends ListFragment {
    String test = "testi fail";
    ArrayList<String> datelist;
    ArrayAdapter myAdapter;
    Intent historyintent;
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast toast = Toast.makeText(getActivity(), "Position = "+position+" Date ="+datelist.get(position), Toast.LENGTH_SHORT);
        //toast.show();
        historyintent = new Intent(getActivity().getApplicationContext(),HistoryActivity.class);
        historyintent.putExtra("date", datelist.get(position));
        startActivity(historyintent);
    }
    public void getList(ArrayList<String> s){
        Log.d("MyListFragment", "Testi string = " + s.size());
        //datelist.add("testi");
        //dates = s;
        datelist = s;
        myAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_selectable_list_item, s);
        setListAdapter(myAdapter);
    }
}
