package com.example.chh85.mountain_with_child;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class recreation_list_intent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreation_list_intent);

        Intent rec_Intent = getIntent();

        String rec_sql_name = rec_Intent.getStringExtra("rec_sql_name");


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        final List<String> rec_detail_list = databaseAccess.getQuotes("SELECT name, type, location_detail" +
                ",charge, accomodation, facility, phone_number, web_site " +
                "FROM forest_recreation_2 where name=\"" + rec_sql_name+"\";",8);

        //String name =  databaseAccess.getQuotes("SELECT name from forest_education_list2 where name=\"" + sql_name+"\";").get(0);
        //String type =  databaseAccess.getQuotes("SELECT type from forest_education_list2 where name=\"" + sql_name+"\";").get(0);

        databaseAccess.close();
        //System.out.println("this is detail list!"+rec_sql_name);

        TextView rec_name_tv = (TextView) findViewById(R.id.name);
        rec_name_tv.setText(rec_detail_list.get(0));

        TextView rec_type_tv = (TextView) findViewById(R.id.type);
        rec_type_tv.setText(rec_detail_list.get(1));

        TextView rec_location_detail = (TextView) findViewById(R.id.location_detail);
        rec_location_detail.setText(rec_detail_list.get(2));

        TextView rec_charge = (TextView) findViewById(R.id.charge);
        rec_charge.setText(rec_detail_list.get(3));

        TextView rec_accomodation = (TextView) findViewById(R.id.accomodation);
        rec_accomodation.setText(rec_detail_list.get(4));

        TextView rec_facility = (TextView) findViewById(R.id.facility);
        rec_facility.setText(rec_detail_list.get(5));

        TextView rec_phone_number = (TextView) findViewById(R.id.phonenum);
        rec_phone_number.setText(rec_detail_list.get(6));

        TextView rec_web_site = (TextView) findViewById(R.id.web_site);
        rec_web_site.setText(rec_detail_list.get(7));



        ImageButton btnDial = (ImageButton) findViewById(R.id.btnDial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenum_foruse = rec_detail_list.get(6);
                phonenum_foruse.replaceAll("-","");
                phonenum_foruse.replaceAll("\\)","");
                Uri uri = Uri.parse("tel:"+phonenum_foruse);
                Intent dialintent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(dialintent);
            }
        });

        ImageButton btnWeb = (ImageButton) findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String web_address = rec_detail_list.get(7);
                if (web_address.contains("go.kr")||web_address.contains(".net")||web_address.contains(".com")){
                    Uri weburi = Uri.parse(web_address);
                    Intent webintent = new Intent(Intent.ACTION_VIEW,weburi);
                    startActivity(webintent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"죄송합니다. 해당 자료가 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
