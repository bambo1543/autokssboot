package de.promove.autokss.web.converter.date;

import org.springframework.stereotype.Component;

import java.text.DateFormat;

@Component
public class DateTimeConverter extends AbstractDateConverter {

    public DateTimeConverter() {
        super(DateFormat.getDateTimeInstance());
    }
}
