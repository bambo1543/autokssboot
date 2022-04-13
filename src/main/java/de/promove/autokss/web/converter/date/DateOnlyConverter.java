package de.promove.autokss.web.converter.date;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateOnlyConverter extends AbstractDateConverter {

    public DateOnlyConverter() {
        super(DateFormat.getDateInstance());
    }
}
