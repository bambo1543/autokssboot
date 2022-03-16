package de.promove.autokss.web.common.crud;

import de.promove.autokss.model.IdEntity;
import org.primefaces.model.LazyDataModel;

import java.util.List;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
public abstract class LazyIdEntityDataModel<T extends IdEntity> extends LazyDataModel<T>{

	@Override
	public T getRowData(String rowKey) {
		List<T> items = getWrappedData();
		for (T item : items) {
			if(item.getId().equals(rowKey)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public String getRowKey(T item) {
		return item.getId();
	}

}
