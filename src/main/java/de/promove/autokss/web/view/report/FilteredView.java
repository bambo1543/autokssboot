package de.promove.autokss.web.view.report;

import org.primefaces.model.FilterMeta;

import java.util.Map;

public interface FilteredView {

    Class<?> getClazz();

    String getI18nFieldName(String entityField);

    Map<String, FilterMeta> getFilterMap();

}
