package com.jsp.wds.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    static Date toDate(String date) {

        String format = "yyyy-MM-dd";

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);

        String customDate = dateFormatter.format(dateFormatter.parse(date));

        DateFormat customDateFormat = new SimpleDateFormat(format);

        Date newDate = null;

        try {
            newDate = customDateFormat.parse(customDate);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        return newDate;

    }
}
