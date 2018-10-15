package com.example.chh85.mountain_with_child;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mt_edu2 extends AppCompatActivity {

    String currentTab = "edu_list_tab";
    String sql_name = "SELECT name FROM forest_education_list2 order by location";
    String sql_location = "SELECT location_detail FROM forest_education_list2 order by location";
    private  SimpleAdapter simpleAdapter;
    List<String> name_list_for_select;
    String sql_updated="";
    String location_from_spinner="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mt_edu2);


        final TabHost tabHost = findViewById(R.id.tabhost);

        tabHost.setup();

        TabHost.TabSpec edu_search_tab = tabHost.newTabSpec("edu_search");
        edu_search_tab.setContent(R.id.edu_search);
        edu_search_tab.setIndicator("검색 조건");
        tabHost.addTab(edu_search_tab);

        TabHost.TabSpec edu_list_tab = tabHost.newTabSpec("edu_list_tab");
        edu_list_tab.setContent(R.id.edu_list);
        edu_list_tab.setIndicator("체험 목록");
        tabHost.addTab(edu_list_tab);

        ///edu_list_tab//////////////////////////////////////////////////////////////

        if(currentTab.equals("edu_search")) {
            tabHost.setCurrentTab(0);
        } else {
            tabHost.setCurrentTab(1);
        }

        //list view for edu_list. show forest education class in list
        final ListView education_list = (ListView) findViewById(R.id.education_list);



       final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        final CheckBox all_age = (CheckBox)findViewById(R.id.all_age);
        final CheckBox child = (CheckBox)findViewById(R.id.child);
        final CheckBox ele_school = (CheckBox)findViewById(R.id.ele_sch);
        final CheckBox mid_school = (CheckBox)findViewById(R.id.mid_sch);
        final CheckBox teen = (CheckBox)findViewById(R.id.teen);

        final CheckBox spring = (CheckBox)findViewById(R.id.spring);
        final CheckBox summer = (CheckBox)findViewById(R.id.summer);
        final CheckBox fall = (CheckBox)findViewById(R.id.fall);
        final CheckBox winter = (CheckBox)findViewById(R.id.winter);

        final Spinner location_spinner = (Spinner) findViewById(R.id.location_spinner);

        final String[] str_array_location = {"없음","서울시","수도권","강원권","충청권","경상권","전라권","제주도"};
        ArrayAdapter<String> spinner_adapter;
        spinner_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,str_array_location);
        location_spinner.setAdapter(spinner_adapter);

        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(location_spinner.getItemAtPosition(position)=="없음"){
                    location_from_spinner="";
                } else {
                    location_from_spinner = "and location=" + "\"" + (String) location_spinner.getItemAtPosition(position) + "\"";
                    System.out.println(location_from_spinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Context context = this;
        Button apply_btn = (Button) findViewById(R.id.apply_btn);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql_updated  = checkbox_checked.checked_sql_sentence(sql_updated ,all_age,child,ele_school,mid_school,teen,spring,summer,fall,winter);
                System.out.println("SELECT name FROM forest_education_list2 where"+sql_updated );

                //검색 후 모두 체크 해제하였을 때 오류가 뜸.. sql_updated가 공백이면 원래 sql 적용하도록.
                if(sql_updated ==""){
                    if(location_from_spinner !=""){   //근데 location만 선택했을수도 있음..
                        String temp_location_from_spinner = location_from_spinner.replaceFirst("and","");
                        sql_name = "SELECT name FROM forest_education_list2 where " +temp_location_from_spinner+ " order by location";
                        sql_location = "SELECT location_detail FROM forest_education_list2 where" +temp_location_from_spinner+ "order by location";
                     } else {
                        sql_name = "SELECT name FROM forest_education_list2 order by location";
                        sql_location = "SELECT location_detail FROM forest_education_list2 order by location";
                     }
                } else {
                    sql_name = "SELECT name FROM forest_education_list2 where" + sql_updated + location_from_spinner+"order by location";
                    sql_location = "SELECT location_detail FROM forest_education_list2 where "+ sql_updated+location_from_spinner+"order by location";
                }
                Toast.makeText(getApplicationContext(),"검색 조건이 적용되었습니다",Toast.LENGTH_SHORT).show();
                System.out.println(sql_name);
                tabHost.setCurrentTab(1);
                databaseAccess.open();
                name_list_for_select=hashmap_create.list_create(databaseAccess,education_list, context,sql_name,sql_location);

            }
        });

        name_list_for_select= hashmap_create.list_create(databaseAccess,education_list, this, sql_name,sql_location);

        education_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent list_detail_intent = new Intent(getApplicationContext(),education_list_intent.class);
                list_detail_intent.putExtra("sql_name",name_list_for_select.get(position));
                startActivity(list_detail_intent);
            }
        });






    }//oncreate statement

}
