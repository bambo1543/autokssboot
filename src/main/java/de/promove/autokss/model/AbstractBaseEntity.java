package de.promove.autokss.model;

import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements IdEntity, Serializable, Cloneable {

    @Id
    private String id = UUID.randomUUID().toString();

    private transient List<PropertyChangeListener> listeners = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractBaseEntity that = (AbstractBaseEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Object clone() {
        try {
            AbstractBaseEntity clone = (AbstractBaseEntity) BeanUtils.cloneBean(this);
            clone.setId(UUID.randomUUID().toString());
            return clone;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    public void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
        if(oldValue == null && newValue != null ||
                oldValue != null && newValue == null ||
                !Objects.equals(oldValue, newValue)) {
            PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
            for (PropertyChangeListener listener : listeners) {
                listener.propertyChange(event);
            }
        }
    }
}
