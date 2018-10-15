package com.example.chh85.mountain_with_child;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.Spinner;

public class checkbox_checked {

        public static String checked_sql_sentence(String sql_updated, CheckBox all_age, CheckBox child, CheckBox ele_school, CheckBox mid_sch, CheckBox teen
        , CheckBox spring, CheckBox summer, CheckBox fall, CheckBox winter){
            String sql_age_sentence="";
            String sql_season_sentence="";

            ////////////AGE
            if(all_age.isChecked()){
                sql_updated = sql_age_sentence.concat("or age =\"전연령\"");
                sql_age_sentence = sql_updated ;
            }

            if(child.isChecked()){
                sql_updated  = sql_age_sentence.concat("or age like \"%유아%\"");
                sql_age_sentence = sql_updated ;
            }

            if(ele_school.isChecked()){
                sql_updated  = sql_age_sentence.concat("or age like \"%초등학생%\"");
                sql_age_sentence = sql_updated ;
            }
            if(mid_sch.isChecked()){
                sql_updated  = sql_age_sentence.concat("or age like \"%중학생%\"");
                sql_age_sentence = sql_updated ;
            }
            if(teen.isChecked()){
                sql_updated  = sql_age_sentence.concat("or age like \"%청소년%\"");
                sql_age_sentence = sql_updated ;
            }

            ///////////////LOCATION_DETAIL
            if(spring.isChecked()){
                sql_updated  = sql_season_sentence.concat("or date_search like \"봄%\"");
                sql_season_sentence = sql_updated ;
            }
            if(summer.isChecked()){
                sql_updated  = sql_season_sentence.concat("or date_search like \"%여름%\"");
                sql_season_sentence = sql_updated ;
            }
            if(fall.isChecked()){
                sql_updated  = sql_season_sentence.concat("or date_search like \"%가을%\"");
                sql_season_sentence = sql_updated ;
            }
            if(winter.isChecked()){
                sql_updated  = sql_season_sentence.concat("or date_search like \"%겨울%\"");
                sql_season_sentence = sql_updated ;
            }





            sql_age_sentence= sql_age_sentence.replaceFirst("or","");
            sql_season_sentence =sql_season_sentence.replaceFirst("or","");

            if(sql_age_sentence != ""){
                sql_age_sentence = "("+sql_age_sentence+")";
            }
            if(sql_season_sentence != ""){
                sql_season_sentence = "("+sql_season_sentence+")";
            }
            if(sql_age_sentence !="" && sql_season_sentence!=""){
                sql_updated = sql_age_sentence+"and"+sql_season_sentence;
            } else {
                sql_updated = sql_age_sentence+sql_season_sentence;
            }

        return sql_updated ;
    }

}
