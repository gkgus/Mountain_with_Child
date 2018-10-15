package com.example.chh85.mountain_with_child;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;

public class rec_checked {

    public static String rec_checked_sql_sentence(CheckBox freecharge, CheckBox charge){
        String checked_sql = "";
        if(freecharge.isChecked()){
           checked_sql = checked_sql.concat("or charge =\"무료\"");
        }

        if(charge.isChecked()){
            checked_sql = checked_sql.concat("or charge <> \"무료\"");
        }

        checked_sql = "("+checked_sql+")";
        checked_sql= checked_sql.replaceFirst("or","");

        return checked_sql;
    }
}
