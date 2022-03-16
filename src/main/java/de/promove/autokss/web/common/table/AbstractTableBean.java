package de.promove.autokss.web.common.table;

import de.promove.autokss.model.LockedEntity;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 * @since 1.0.0
 */
public abstract class AbstractTableBean<T extends LockedEntity> implements TableBean, Serializable {

    @Override
    public void onRowSelect(SelectEvent event) {
    }

    @Override
    public void onRowUnselect(UnselectEvent event) {
    }

    protected abstract TableModel<T> getModel();

    @Override
    public boolean isTableButtonDisabled() {
        return getModel().getSelectedItem() == null;
    }

    @Override
    public boolean isNonLockButtonDisabled() {
        if(!isTableButtonDisabled()) {
            return getModel().getSelectedItem().isLocked();
        }
        return isTableButtonDisabled();
    }

    @Override
    public boolean isLockButtonRendered() {
        return !isTableButtonDisabled() && !getModel().getSelectedItem().isLocked();
    }

    @Override
    public abstract List<T> getItems();

}
