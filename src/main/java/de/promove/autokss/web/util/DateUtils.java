package de.promove.autokss.web.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date future() {
        return new Date(Calendar.getInstance().getTimeInMillis() * 100);
    }

}
