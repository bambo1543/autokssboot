package de.promove.autokss.web.converter;

import de.promove.autokss.model.Bereich;
import de.promove.autokss.model.IdEntity;
import de.promove.autokss.service.AbstractEntityService;
import de.promove.autokss.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public abstract class AbstractConverter<T extends IdEntity> implements Converter {

    private final Class<T> clazz;

    @Autowired
    protected GenericService genericService;

    public AbstractConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    public final T getAsObject(FacesContext facesContext, UIComponent uiComponent, String id) {
        return genericService.findById(clazz, id);
    }

    public final String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof IdEntity) {
            return getAsString(facesContext, uiComponent, (T) o);
        }
        return null;
    }

    public final String getAsString(FacesContext facesContext, UIComponent uiComponent, T o) {
        return o.getId();
    }
}