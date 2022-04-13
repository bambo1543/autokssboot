package de.promove.autokss.web.converter.date;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@AllArgsConstructor
public abstract class AbstractDateConverter implements Converter<Date> {

    private final DateFormat format;

    @Override
    public Date getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Date value) {
        return format.format(value);
    }
}
