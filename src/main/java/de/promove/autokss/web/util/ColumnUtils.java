package de.promove.autokss.web.util;

import de.promove.autokss.model.NamedEntity;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.ValueHolder;
import jakarta.faces.context.FacesContext;
import org.primefaces.component.api.UIColumn;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ColumnUtils {

    public static String formatColumn(UIColumn column) {
        FacesContext context = FacesContext.getCurrentInstance();
        Object value = null;
        if(column.getField() != null) {
            value = UIColumn.createValueExpressionFromField(context, "item", column.getField()).getValue(context.getELContext());
        } else if(column.getChildren().size() == 1) {
            UIComponent uiComponent = column.getChildren().get(0);
            if(uiComponent instanceof ValueHolder valueHolder) {
                value = valueHolder.getValue();
            }
        }
        if(value instanceof Date) {
            return SimpleDateFormat.getDateInstance().format(value);
        } else if(value instanceof Number) {
            NumberFormat numberFormat = DecimalFormat.getInstance();
            numberFormat.setMinimumFractionDigits(2);
            numberFormat.setMaximumFractionDigits(2);
            return numberFormat.format(value);
        } else if(value instanceof NamedEntity namedEntity) {
            return namedEntity.getName();
        }
        return null;
    }

}
