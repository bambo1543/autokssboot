package de.promove.autokss.web.converter;

import de.promove.autokss.model.IdEntity;
import de.promove.autokss.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;

public abstract class AbstractEntityConverter<T extends IdEntity> implements Converter<T> {

    private final Class<T> clazz;

    @Autowired
    protected GenericService genericService;

    public AbstractEntityConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    public final T getAsObject(FacesContext facesContext, UIComponent uiComponent, String id) {
        return genericService.findById(clazz, id);
    }

    public final String getAsString(FacesContext facesContext, UIComponent uiComponent, T o) {
        if(o == null) return null;

        return o.getId();
    }
}