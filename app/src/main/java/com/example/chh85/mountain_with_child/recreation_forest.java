package com.example.chh85.mountain_with_child;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.List;

public class recreation_forest extends AppCompatActivity {
    String currentTab = "rec_list";
    List<String> rec_name_list_for_select;
    String rec_sql_name = "SELECT name FROM forest_recreation_2";
    String rec_sql_location = "SELECT location_detail from forest_recreation_2;";
    String spinner_sql ="";
    String sql_updated ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreation_forest);

        final TabHost rec_tabHost = findViewById(R.id.tabhost2);

        rec_tabHost.setup();

        TabHost.TabSpec rec_search_tab = rec_tabHost.newTabSpec("rec_search_list");
        rec_search_tab.setContent(R.id.rec_search_lay);
        rec_search_tab.setIndicator("검색 조건");
        rec_tabHost.addTab(rec_search_tab);

        TabHost.TabSpec rec_list_tab = rec_tabHost.newTabSpec("rec_list");
        rec_list_tab.setContent(R.id.rec_list_lay);
        rec_list_tab.setIndicator("휴양림 목록");
        rec_tabHost.addTab(rec_list_tab);



        if(currentTab.equals("rec_list")) {
            rec_tabHost.setCurrentTab(1);
        } else {
            rec_tabHost.setCurrentTab(0);
        }
        final ListView rec_list_view = (ListView) findViewById(R.id.rec_list_view);
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        final CheckBox free_charge = (CheckBox)findViewById(R.id.free_charge);
        final CheckBox charge = (CheckBox)findViewById(R.id.charge);

        final Spinner rec_location_spinner = (Spinner) findViewById(R.id.rec_location_spinner);

        final String[] str_array_location = {"없음","수도권","강원도","충청도","경상도","전라도","제주도"};
        final ArrayAdapter<String> spinner_adapter;
        spinner_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,str_array_location);
        rec_location_spinner.setAdapter(spinner_adapter);
        rec_location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(rec_location_spinner.getItemAtPosition(position)=="없음"){
                    spinner_sql = "";
                } else {
                    spinner_sql = "and location=" + "\"" + (String) rec_location_spinner.getItemAtPosition(position) + "\"";
                    System.out.println(spinner_sql);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Context rec_context = this;

        Button rec_apply_btn = (Button) findViewById(R.id.rec_apply_btn);
        rec_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sql_updated  = rec_checked.rec_checked_sql_sentence(free_charge,charge);

                if(sql_updated ==""){
                    spinner_sql=spinner_sql.replaceFirst("and","");
                    sql_updated=sql_updated.concat(spinner_sql);
                } else {
                    sql_updated=sql_updated.concat(spinner_sql);
                }

                System.out.println("SELECT name FROM forest_recreation_2 where "+sql_updated );

                if(sql_updated==""){
                    rec_sql_name = "SELECT name FROM forest_recreation_2";
                    rec_sql_location = "SELECT location_detail from forest_recreation_2;";
                } else {
                    rec_sql_name = "SELECT name FROM forest_recreation_2 where "+sql_updated;
                    rec_sql_location = "SELECT location_detail FROM forest_recreation_2 where "+sql_updated;
                }
                Toast.makeText(getApplicationContext(),"검색 조건이 적용되었습니다",Toast.LENGTH_SHORT).show();
                System.out.println(rec_sql_name);
                rec_tabHost.setCurrentTab(1);
                databaseAccess.open();
                rec_name_list_for_select= hashmap_create.list_create(databaseAccess,rec_list_view, rec_context, rec_sql_name,rec_sql_location);
            }
        });



        databaseAccess.open();
        rec_name_list_for_select= hashmap_create.list_create(databaseAccess,rec_list_view, this, rec_sql_name,rec_sql_location);
        rec_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent list_detail_intent = new Intent(getApplicationContext(),recreation_list_intent.class);
                list_detail_intent.putExtra("rec_sql_name",rec_name_list_for_select.get(position));
                startActivity(list_detail_intent);
            }
        });
    }
}
