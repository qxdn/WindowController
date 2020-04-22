package com.sunbest.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    /**
     * 获取一周日期
     * @return
     */
    public static List<String> getWeek(){
        List<String> week=new ArrayList<String>();
        for (int i = 6 ; i >= 0; i--) {
            week.add(getPastDate(i));
        }
        return week;
    }


    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("M月d日");
        String result = format.format(today);
        return result;
    }
}
