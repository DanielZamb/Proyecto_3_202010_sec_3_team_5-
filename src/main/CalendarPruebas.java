package main;

import controller.Controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class CalendarPruebas {
    public static void main(String[] args) throws IOException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DAY_OF_WEEK, 5);
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.getTime());
        String sDate = "2018/01/12-13:10:4.000";
        sDate = sDate.replace('-','T');
        sDate = sDate + "Z";
        System.out.println(sDate);
    }
}
