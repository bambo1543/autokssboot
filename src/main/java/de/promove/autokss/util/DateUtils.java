package de.promove.autokss.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date future() {
        return new Date(Calendar.getInstance().getTimeInMillis() * 100);
    }

    public static Date localDateToDateConversion(LocalDate localDate) {
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //local date + atStartOfDay() + default time zone + toInstant() = Date
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

}
