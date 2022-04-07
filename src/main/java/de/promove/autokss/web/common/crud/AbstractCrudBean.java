package de.promove.autokss.web.common.crud;

import de.promove.autokss.dao.QueryFetch;
import de.promove.autokss.dao.QueryOrder;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.IdEntity;
import de.promove.autokss.model.LockedEntity;
import de.promove.autokss.model.NamedEntity;
import de.promove.autokss.service.GenericService;
import de.promove.autokss.web.util.GrowlMessenger;
import de.promove.autokss.web.util.MessageFactory;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class AbstractCrudBean<T extends NamedEntity> implements Serializable {

	@Autowired
	@Qualifier(value = "genericService")
	protected GenericService genericService;

	protected Class<T> clazz;

	protected String visibleSection = "";

	protected LazyDataModel<T> dataModel;
	protected T selectedItem;
	protected T editItem;
	protected QueryFetch[] itemsQueryFetch;
	protected QueryFetch[] editItemQueryFetch;

	public AbstractCrudBean(Class<T> clazz) {
		this(clazz, new QueryFetch[0], new QueryFetch[0]);
	}

	public AbstractCrudBean(Class<T> clazz, QueryFetch[] editItemQueryFetch) {
		this(clazz, new QueryFetch[0], editItemQueryFetch);
	}

	public AbstractCrudBean(Class<T> clazz, QueryFetch[] itemsQueryFetch, QueryFetch[] editItemQueryFetch) {
		this.clazz = clazz;
		this.itemsQueryFetch = itemsQueryFetch;
		this.editItemQueryFetch = editItemQueryFetch;
	}

	@PostConstruct
	public void init() {
		visibleSection = "table";

		dataModel = new LazyIdEntityDataModel<>() {
			@Override
			public int count(Map<String, FilterMeta> map) {
				return 0;
			}

			@Override
			public List<T> load(int first, int pageSize, Map<String, SortMeta> sortMap, Map<String, FilterMeta> filterMap) {
				List<T> items = loadItems(first, pageSize, sortMap, filterMap);
				this.setRowCount(countItems(filterMap));
				return items;
			}

		};
	}

	public String getVisibleSection() {
		return visibleSection;
	}

	protected List<T> loadItems(int first, int pageSize, Map<String, SortMeta> sortMap, Map<String, FilterMeta> filterMap) {
		QueryOrder order = null;

		for (String key : sortMap.keySet()) {
			SortMeta sortMeta = sortMap.get(key);
			if(order == null) {
				order = QueryOrder.by(key, QueryOrder.OrderDirection.getByInt(sortMeta.getOrder().intValue()));
			} else {
				order.and(key, QueryOrder.OrderDirection.getByInt(sortMeta.getOrder().intValue()));
			}
		}

		return genericService.list(clazz, createQueryParameter(filterMap), order, first, pageSize, itemsQueryFetch);
	}

	protected int countItems(Map<String, FilterMeta> filterMap) {
		return genericService.count(clazz, createQueryParameter(filterMap)).intValue();
	}

	protected QueryParameter createQueryParameter(Map<String, FilterMeta> filters) {
		return new QueryParameter(clazz, filters);
	}

	public String getEntityName() {
		return MessageFactory.getMessage("entity." + clazz.getSimpleName() + ".singular");
	}

	public LazyDataModel<T> getDataModel() {
		return dataModel;
	}

	public T getEditItem() {
		return editItem;
	}

	public void add() {
		try {
			editItem = clazz.getConstructor().newInstance();
			visibleSection = "form";
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public void edit() {
		editItem = genericService.findById(clazz, selectedItem.getId(), editItemQueryFetch);
		visibleSection = "form";
	}

	public void save() {
		if(editItem.equals(selectedItem)) {
			mergeEditItem(editItem);
			GrowlMessenger.publish(MessageFactory.getMessage("action.edit.growl.summary", getEntityName()),
					MessageFactory.getMessage("action.edit.growl.detail", getEntityName(), editItem.getName()));
		} else {
			persistEditItem(editItem);
			GrowlMessenger.publish(MessageFactory.getMessage("action.add.growl.summary", getEntityName()),
					MessageFactory.getMessage("action.add.growl.detail", getEntityName(), editItem.getName()));
		}
		resetOnEditOrAddFinished();
	}

	protected void mergeEditItem(T editItem) {
		genericService.merge(editItem);
	}

	protected void persistEditItem(T editItem) {
		genericService.persist(editItem);
	}

	public void cancel() {
		resetOnEditOrAddFinished();
	}

	protected void resetOnEditOrAddFinished() {
		editItem = null;
		visibleSection = "table";
	}

	public void delete() {
		T item = selectedItem;
		genericService.remove(item);
		GrowlMessenger.publish(MessageFactory.getMessage("action.delete.growl.summary", getEntityName()),
				MessageFactory.getMessage("action.delete.growl.detail", getEntityName(), item.getName()));
		resetOnDeleteFinished();
	}

	protected void resetOnDeleteFinished() {
		selectedItem = null;
	}

	public T getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(T selectedItem) {
		this.selectedItem = selectedItem;
	}

	public boolean isAddable() {
		return true;
	}

	public boolean isEditable() {
		return isModifyable();
	}

	public boolean isDeleteable() {
		return isModifyable();
	}

	private boolean isModifyable() {
		if (selectedItem != null) {
			if (selectedItem instanceof LockedEntity) {
				LockedEntity lockedEntity = (LockedEntity) selectedItem;
				return !lockedEntity.isLocked();
			}
			return true;
		}
		return false;
	}

}
