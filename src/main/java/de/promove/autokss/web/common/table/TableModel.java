package de.promove.autokss.web.common.table;

import java.util.List;

/**
 * Title: <br/>
 * Outline: <br/>
 * Description: <br/>
 * <br/>
 * Copyright: (c) 2010 <br/>
 * Company: TESIS SYSware GmbH <br/>
 * <br/>
 * Creation Date: 21.11.2010 <br/>
 *
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 * @since 1.0.0
 */
public class TableModel<T> {

    protected Class<T> clazz;

    protected List<T> items;

    protected T selectedItem;

    protected T editItem;

    public TableModel(Class<T> clazz) {
        this.clazz = clazz;
        try {
            editItem = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEditItem() throws IllegalAccessException, InstantiationException {
        items.add(editItem);
        selectedItem = editItem;
        editItem = clazz.newInstance();
    }

    public void addItemAfterSelected(T item) {
        int index = items.indexOf(selectedItem);
        items.add(index + 1, item);
    }

    public T removeSelectedItem() {
        T item = selectedItem;
        selectedItem = null;
        items.remove(item);
        return item;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
    }

    public T getEditItem() {
        return editItem;
    }

    public void setEditItem(T editItem) {
        this.editItem = editItem;
    }

}
