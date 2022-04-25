package de.promove.autokss.web.common.crud;

import de.promove.autokss.dao.QueryFetch;
import de.promove.autokss.dao.QueryOrder;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.LockedEntity;
import de.promove.autokss.model.NamedEntity;
import de.promove.autokss.service.GenericService;
import de.promove.autokss.web.common.MessageBundleLoader;
import de.promove.autokss.web.util.GrowlMessenger;
import de.promove.autokss.web.util.MessageFactory;
import de.promove.autokss.web.util.exporter.MyColoredPdfExporter;
import de.promove.autokss.web.view.report.FilteredView;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.ValueHolder;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class AbstractCrudView<T extends NamedEntity> implements Serializable, FilteredView {

	@Serial
	private static final long serialVersionUID = 1234560L;

	@Autowired
	@Qualifier(value = "genericService")
	protected GenericService genericService;

	@Getter
	protected Class<T> clazz;
	@Getter
	protected String visibleSection = "";

	@Getter
	protected LazyDataModel<T> dataModel;
	@Getter
	@Setter
	protected T selectedItem;
	@Getter
	@Setter
	protected T editItem;
	protected QueryFetch[] itemsQueryFetch;
	protected QueryFetch[] editItemQueryFetch;

	protected boolean modifiable = true;

	@Getter
	private PDFOptions pdfOptions;

	@Getter
	private Map<String, FilterMeta> filterMap = new HashMap<>();

	private String reportHeader = null;
	@Getter
	private MyColoredPdfExporter pdfExporter;

	public AbstractCrudView(Class<T> clazz) {
		this(clazz, null, new QueryFetch[0], new QueryFetch[0]);
	}

	public AbstractCrudView(Class<T> clazz, String reportHeader) {
		this(clazz, reportHeader, new QueryFetch[0], new QueryFetch[0]);
	}

	public AbstractCrudView(Class<T> clazz, QueryFetch[] editItemQueryFetch) {
		this(clazz, null, new QueryFetch[0], editItemQueryFetch);
	}

	public AbstractCrudView(Class<T> clazz, String reportHeader, QueryFetch[] itemsQueryFetch, QueryFetch[] editItemQueryFetch) {
		this.clazz = clazz;
		this.reportHeader = reportHeader;
		this.itemsQueryFetch = itemsQueryFetch;
		this.editItemQueryFetch = editItemQueryFetch;
	}

	@PostConstruct
	public void init() {
		visibleSection = "table";

		dataModel = new LazyIdEntityDataModel<>() {

			@Override
			public int count(Map<String, FilterMeta> map) {
				return countItems(map);
			}

			@Override
			public List<T> load(int first, int pageSize, Map<String, SortMeta> sortMap, Map<String, FilterMeta> filterMap) {
				AbstractCrudView.this.filterMap = filterMap;
				List<T> items = loadItems(first, pageSize, sortMap, filterMap);
				this.setRowCount(countItems(filterMap));
				return items;
			}
		};

		pdfExporter = new MyColoredPdfExporter(reportHeader == null ? MessageFactory.getMessage("entity." + clazz.getSimpleName().toLowerCase() + ".plural") : reportHeader, this);

		pdfOptions = new PDFOptions();
		pdfOptions.setCellFontSize("12");
		pdfOptions.setFontName("Courier");
		pdfOptions.setOrientation(PDFOrientationType.LANDSCAPE);
	}

	protected List<T> loadItems(int first, int pageSize, Map<String, SortMeta> sortMap, Map<String, FilterMeta> filterMap) {
		QueryParameter queryParameter = createQueryParameter(filterMap);
		queryParameter = appendQueryParameters(queryParameter);
		return genericService.list(clazz, queryParameter, createQueryOrder(sortMap), first, pageSize, itemsQueryFetch);
	}

	protected QueryParameter appendQueryParameters(QueryParameter queryParameter) {
		return queryParameter;
	}

	protected QueryOrder createQueryOrder(Map<String, SortMeta> sortMap) {
		QueryOrder order = null;

		for (String key : sortMap.keySet()) {
			SortMeta sortMeta = sortMap.get(key);
			if(order == null) {
				order = QueryOrder.by(key, QueryOrder.OrderDirection.getByInt(sortMeta.getOrder().intValue()));
			} else {
				order.and(key, QueryOrder.OrderDirection.getByInt(sortMeta.getOrder().intValue()));
			}
		}
		return order;
	}

	protected int countItems(Map<String, FilterMeta> filterMap) {
		QueryParameter queryParameter = createQueryParameter(filterMap);
		return genericService.count(clazz, appendQueryParameters(queryParameter)).intValue();
	}

	protected QueryParameter createQueryParameter(Map<String, FilterMeta> filters) {
		return new QueryParameter(clazz, filters, FacesContext.getCurrentInstance().getViewRoot().getLocale());
	}

	public String getEntityName() {
		return MessageFactory.getMessage("entity." + clazz.getSimpleName().toLowerCase() + ".singular");
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

	public boolean isAddable() {
		return true;
	}

	public boolean isEditable() {
		return isModifiable();
	}

	public boolean isDeletable() {
		return isModifiable();
	}

	protected boolean isModifiable() {
		if (modifiable && selectedItem != null) {
			if (selectedItem instanceof LockedEntity lockedEntity) {
				return !lockedEntity.isLocked();
			}
			return true;
		}
		return false;
	}

	public String formatColumn(UIColumn column) {
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

	public String getFilename() {
		return (reportHeader == null ? MessageFactory.getMessage("entity." + clazz.getSimpleName().toLowerCase()+ ".plural") : reportHeader)
				+ " " + DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
	}

	public String getI18nFieldName(String entityField) {
		return MessageFactory.getMessage("entity." + clazz.getSimpleName().toLowerCase() + "." + entityField);
	}

}

