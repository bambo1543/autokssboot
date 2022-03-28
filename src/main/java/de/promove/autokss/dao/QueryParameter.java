package de.promove.autokss.dao;

import de.promove.autokss.model.IdEntity;
import org.primefaces.model.FilterMeta;

import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
public class QueryParameter {

    private List<QueryParameterEntry> parameters;

	public QueryParameter(List<QueryParameterEntry> parameters) {
		this.parameters = parameters;
	}

	public QueryParameter(Map<String, FilterMeta> filters) {
		this.parameters = new ArrayList<>();
		for (String filterName : filters.keySet()) {
            FilterMeta filterMeta = filters.get(filterName);

            Object filterValue = filterMeta.getFilterValue();
            if(filterValue instanceof String) {
                String filterString = (String) filterValue;
                if(!filterString.startsWith("*") && !filterString.endsWith("*")) {
                    this.parameters.add(new QueryParameterEntry(filterName, filterValue, QueryParameterEntry.Operator.STARTS));
                } else if(filterString.startsWith("*")) {
                    this.parameters.add(new QueryParameterEntry(filterName, filterString.substring(1), QueryParameterEntry.Operator.ENDS));
                }
            } else if(filterValue instanceof List) {
                List filterList = (List) filterValue;
                if(filterList.size() == 2 && filterList.get(0) instanceof LocalDate && filterList.get(1) instanceof LocalDate) {
                    this.parameters.add(new QueryParameterEntry(filterName, localDateToDateConversion((LocalDate) filterList.get(0)), QueryParameterEntry.Operator.GE));
                    this.parameters.add(new QueryParameterEntry(filterName, localDateToDateConversion((LocalDate) filterList.get(1)), QueryParameterEntry.Operator.LE));
                }
            } else if(filterValue instanceof LocalDate) {
                LocalDate localDate = (LocalDate) filterValue;
                this.parameters.add(new QueryParameterEntry(filterName, localDateToDateConversion(localDate)));
            } else {
                this.parameters.add(new QueryParameterEntry(filterName, filterValue));
            }
		}
	}

	private Date localDateToDateConversion(LocalDate localDate) {
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //local date + atStartOfDay() + default time zone + toInstant() = Date
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    private QueryParameter(SingularAttribute name, Object value) {
        this.parameters = new ArrayList<>();
        this.parameters.add(new QueryParameterEntry(name, value));
    }

    private QueryParameter(SingularAttribute name, Object value, QueryParameterEntry.Operator operator) {
        this.parameters = new ArrayList<>();
        this.parameters.add(new QueryParameterEntry(name, value, operator));
    }

    private QueryParameter(String name, Object value) {
        this.parameters = new ArrayList<>();
        this.parameters.add(new QueryParameterEntry(name, value));
    }

    private QueryParameter(String name, Object value, QueryParameterEntry.Operator operator) {
        this.parameters = new ArrayList<>();
        this.parameters.add(new QueryParameterEntry(name, value, operator));
    }

    public static QueryParameter with(SingularAttribute name, Object value) {
        return new QueryParameter(name, value);
    }

    public static QueryParameter with(SingularAttribute name, QueryParameterEntry.Operator operator, Object value) {
        return new QueryParameter(name, value, operator);
    }

    public static QueryParameter with(String name, Object value) {
        return new QueryParameter(name, value);
    }

    public static QueryParameter with(String name, QueryParameterEntry.Operator operator, Object value) {
        return new QueryParameter(name, value, operator);
    }

    public QueryParameter and(SingularAttribute name, Object value) {
        this.parameters.add(new QueryParameterEntry(name, value));
        return this;
    }

    public QueryParameter and(SingularAttribute name, QueryParameterEntry.Operator operator, Object value) {
        this.parameters.add(new QueryParameterEntry(name, value, operator));
        return this;
    }

    public QueryParameter and(String name, Object value) {
        this.parameters.add(new QueryParameterEntry(name, value));
        return this;
    }

    public QueryParameter and(String name, QueryParameterEntry.Operator operator, Object value) {
        this.parameters.add(new QueryParameterEntry(name, value, operator));
        return this;
    }

    public List<QueryParameterEntry> parameters() {
        return this.parameters;
    }

}
