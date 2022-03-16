package de.promove.autokss.web.common.table;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import java.util.List;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 *         Date: 21.11.2010
 *         Time: 22:53:08
 */
public interface TableBean<T> {

    void onRowSelect(SelectEvent event);

    void onRowUnselect(UnselectEvent event);

    boolean isTableButtonDisabled();

    boolean isNonLockButtonDisabled();

    List<T> getItems();

    boolean isLockButtonRendered();
}
