package de.promove.autokss.web.util;

import de.promove.autokss.dao.generic.Filter;
import de.promove.autokss.dao.generic.MatchMode;
import org.primefaces.model.FilterMeta;

import java.util.HashMap;
import java.util.Map;

public class FilterConverter {

    public static Map<String, Filter> convertPrimefacesFilterMeta(Map<String, FilterMeta> filters) {
        Map<String, Filter> filterMap = new HashMap<>();
        for (Map.Entry<String, FilterMeta> entry : filters.entrySet()) {
            FilterMeta value = entry.getValue();
            filterMap.put(entry.getKey(), new Filter(value.getField(), value.getColumnKey(), value.getFilterValue(), MatchMode.of(value.getMatchMode().operator())));
        }
        return filterMap;
    }

}
