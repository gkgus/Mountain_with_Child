package com.example.chh85.mountain_with_child;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class hashmap_create {

    public static List<String> list_create(DatabaseAccess databaseAccess, ListView education_list_param, final Context mt_edu_context, String name, String location) {
        final List<String> name_list = databaseAccess.getQuotes(name, 1);
        List<String> location_list = databaseAccess.getQuotes(location, 1);
        databaseAccess.close();

        //creating basic_list..for title and description
        final ArrayList<HashMap<String, String>> edu_list_hash = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> item;


        for (int i = 0; i < name_list.size(); i++) {
            item = new HashMap<String, String>();
            item.put("item1", name_list.get(i));
            item.put("item2", location_list.get(i));
            edu_list_hash.add(item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(mt_edu_context, edu_list_hash, android.R.layout.simple_list_item_2,
                new String[]{"item1", "item2"},
                new int[]{android.R.id.text1, android.R.id.text2});
        education_list_param.setAdapter(simpleAdapter);

        /*
        education_list_param.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent list_detail_intent = new Intent(mt_edu_context,education_list_intent.class);
                list_detail_intent.putExtra("sql_name", edu_list_hash.get(position));
                mt_edu_context.startActivity(list_detail_intent);
            }
        });

*/
        return name_list;
    }

}
