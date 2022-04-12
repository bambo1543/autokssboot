package de.promove.autokss.dao;

import de.promove.autokss.dao.QueryParameterEntry.Operator;
import de.promove.autokss.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.primefaces.model.FilterMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import jakarta.persistence.metamodel.SingularAttribute;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
public class QueryParameter {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final List<QueryParameterEntry> parameters;

	public QueryParameter(List<QueryParameterEntry> parameters) {
		this.parameters = parameters;
	}

	private final List<String> primitiveNumberTypes = List.of("int", "byte", "short", "long", "float", "double");

	public QueryParameter(Class<?> clazz, Map<String, FilterMeta> filters) {
        this(clazz, filters, Locale.getDefault());
    }
	public QueryParameter(Class<?> clazz, Map<String, FilterMeta> filters, Locale locale) {
		this.parameters = new ArrayList<>();
		for (String filterName : filters.keySet()) {
            FilterMeta filterMeta = filters.get(filterName);

            try {
                Object filterValue = filterMeta.getFilterValue();

                Class<?> type = clazz.getDeclaredField(filterMeta.getField()).getType();
                if(type.equals(String.class)) {
                    String filterString = (String) filterValue;
                    if(!filterString.startsWith("*") && !filterString.endsWith("*")) {
                        this.parameters.add(new QueryParameterEntry<>(filterName, filterValue, Operator.STARTS));
                    } else if(filterString.startsWith("*")) {
                        this.parameters.add(new QueryParameterEntry<>(filterName, filterString.substring(1), Operator.ENDS));
                    }
                } else if((Number.class.isAssignableFrom(type) || primitiveNumberTypes.contains(type.getName()))) {
                    if(filterValue instanceof String filterString) {
                        Operator operator = Operator.getOperator(filterString);
                        if(operator != null) {
                            if(filterString.length() > operator.getOperator().length()) {
                                String numberString = filterString.substring(operator.getOperator().length());
                                Number filterNumber = NumberFormat.getInstance(locale).parse(numberString);
                                this.parameters.add(new QueryParameterEntry<>(filterName, filterNumber, operator));
                            }
                        } else {
                            Number filterNumber = NumberFormat.getInstance(locale).parse(filterString);
                            this.parameters.add(new QueryParameterEntry<>(filterName, filterNumber));
                        }
                    } else {
                        this.parameters.add(new QueryParameterEntry<>(filterName, filterValue));
                    }
                } else if(type.equals(Boolean.class) || type.getName().equals("boolean")) {
                    if(filterValue instanceof String string) {
                        if(string.equals("1")) {
                            this.parameters.add(new QueryParameterEntry<>(filterName, Boolean.TRUE));
                        } else if(string.equals("2")) {
                            this.parameters.add(new QueryParameterEntry<>(filterName, Boolean.FALSE));
                        }
                    } else {
                        this.parameters.add(new QueryParameterEntry<>(filterName, filterValue));
                    }
                } else if(type.equals(Date.class)) {
                    if(filterValue instanceof List filterList) {
                        if(filterList.size() == 2 && filterList.get(0) instanceof LocalDate && filterList.get(1) instanceof LocalDate) {
                            this.parameters.add(new QueryParameterEntry<>(filterName, localDateToDateConversion((LocalDate) filterList.get(0)), Operator.GE));
                            this.parameters.add(new QueryParameterEntry<>(filterName, localDateToDateConversion((LocalDate) filterList.get(1)), Operator.LE));
                        }
                    } else if(filterValue instanceof LocalDate localDate) {
                        this.parameters.add(new QueryParameterEntry<>(filterName, localDateToDateConversion(localDate)));
                    }
                } else {
                    this.parameters.add(new QueryParameterEntry<>(filterName, filterValue));
                }
            } catch (NoSuchFieldException | ParseException e) {
                logger.error(e.getMessage(), e);
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

    private QueryParameter(SingularAttribute name, Object value, Operator operator) {
        this.parameters = new ArrayList<>();
        this.parameters.add(new QueryParameterEntry(name, value, operator));
    }

    private QueryParameter(String name, Object value) {
        this.parameters = new ArrayList<>();
        this.parameters.add(new QueryParameterEntry(name, value));
    }

    private QueryParameter(String name, Object value, Operator operator) {
        this.parameters = new ArrayList<>();
        this.parameters.add(new QueryParameterEntry(name, value, operator));
    }

    public static QueryParameter with(SingularAttribute name, Object value) {
        return new QueryParameter(name, value);
    }

    public static QueryParameter with(SingularAttribute name, Operator operator, Object value) {
        return new QueryParameter(name, value, operator);
    }

    public static QueryParameter with(String name, Object value) {
        return new QueryParameter(name, value);
    }

    public static QueryParameter with(String name, Operator operator, Object value) {
        return new QueryParameter(name, value, operator);
    }

    public QueryParameter and(SingularAttribute name, Object value) {
        this.parameters.add(new QueryParameterEntry(name, value));
        return this;
    }

    public QueryParameter and(SingularAttribute name, Operator operator, Object value) {
        this.parameters.add(new QueryParameterEntry(name, value, operator));
        return this;
    }

    public QueryParameter and(String name, Object value) {
        this.parameters.add(new QueryParameterEntry(name, value));
        return this;
    }

    public QueryParameter and(String name, Operator operator, Object value) {
        this.parameters.add(new QueryParameterEntry(name, value, operator));
        return this;
    }

    public List<QueryParameterEntry> parameters() {
        return this.parameters;
    }

}
