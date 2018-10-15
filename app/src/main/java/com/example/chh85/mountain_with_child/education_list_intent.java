package com.example.chh85.mountain_with_child;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class education_list_intent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_list_intent);

        Intent inIntent = getIntent();

        String sql_name = inIntent.getStringExtra("sql_name");


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        final List<String> detail_list = databaseAccess.getQuotes("SELECT name,type,program,location_detail,"
                +"age,date_detail,when_apply,apply_freq,etc,howto,admin,phonenum,detail_info " +
                "FROM forest_education_list2 where name=\"" + sql_name+"\";",13);

        //String name =  databaseAccess.getQuotes("SELECT name from forest_education_list2 where name=\"" + sql_name+"\";").get(0);
        //String type =  databaseAccess.getQuotes("SELECT type from forest_education_list2 where name=\"" + sql_name+"\";").get(0);

        databaseAccess.close();
        System.out.println("this is detail list!"+sql_name);
        System.out.println("this is detail list!"+detail_list);
        /*
        * "SELECT name,type,program,location_detail," +"age,date_detail,when_apply,apply_freq,etc,admin,phonenum,detail_info FROM forest_education_list2" +
                "where id ="+position_str)
        * */



        //TextViews....
        TextView name_tv = (TextView) findViewById(R.id.name);
        name_tv.setText(detail_list.get(0));

        TextView type_tv = (TextView) findViewById(R.id.type);
        type_tv.setText(detail_list.get(1));

        TextView program_tv = (TextView) findViewById(R.id.program);
        program_tv.setText(detail_list.get(2));

        TextView location_detail_tv = (TextView) findViewById(R.id.location_detail);
        location_detail_tv.setText(detail_list.get(3));

        TextView age_tv = (TextView) findViewById(R.id.age);
        age_tv .setText(detail_list.get(4));

        TextView date_detail_tv = (TextView) findViewById(R.id.date_detail);
        date_detail_tv.setText(detail_list.get(5));

        TextView when_apply_tv = (TextView) findViewById(R.id.when_apply);
        when_apply_tv.setText(detail_list.get(6));

        TextView apply_freq_tv = (TextView) findViewById(R.id.apply_freq);
        apply_freq_tv.setText(detail_list.get(7));

        TextView etc_tv = (TextView) findViewById(R.id.etc);
        etc_tv.setText(detail_list.get(8));

        TextView howto_tv = (TextView) findViewById(R.id.howto);
        howto_tv .setText(detail_list.get(9));

        TextView admin_tv = (TextView) findViewById(R.id.admin);
        admin_tv.setText(detail_list.get(10));

        TextView phonenum_tv = (TextView) findViewById(R.id.phonenum);
        phonenum_tv.setText(detail_list.get(11));

        TextView detail_info_tv = (TextView) findViewById(R.id.detail_info);
        detail_info_tv.setText(detail_list.get(12));


         ImageButton btnDial = (ImageButton) findViewById(R.id.btnDial);
         btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenum_foruse = detail_list.get(11);
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
                 String web_address = detail_list.get(12);
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
