package de.promove.autokss.dao;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Date: 23.09.2010
 * Time: 19:57:51
 * <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
public class QueryParameterEntry<X, T> {

    public enum Operator {
        GE(">="), LE("<="), GT(">"),  LT("<"), EQ("="), STARTS("*"), CONTAINS("*"), ENDS("*");

        private String operator;

        Operator(String operator) {
            this.operator = operator;

        }

        public String getOperator() {
            return operator;
        }

        public static Operator getOperator(String operator) {
            for (Operator o : values()) {
                if(operator.startsWith(o.operator) && operator.length() >= o.operator.length()) {
                    return o;
                }
            }
            return null;
        }
    }

    private SingularAttribute<X, T> attribute;

	private String attributeName;

    private T value;

    private Operator operator = Operator.EQ;

    public QueryParameterEntry(SingularAttribute<X, T> attribute, T value) {
        this.attribute = attribute;
        this.value = value;
    }

    public QueryParameterEntry(SingularAttribute<X, T> attribute, T value, Operator operator) {
        this.attribute = attribute;
        this.value = value;
        this.operator = operator;
    }

    public QueryParameterEntry(String attribute, T value) {
        this.attributeName = attribute;
        this.value = value;
    }

    public QueryParameterEntry(String attribute, T value, Operator operator) {
        this.attributeName = attribute;
        this.value = value;
        this.operator = operator;
    }

    public SingularAttribute<X, T> getAttribute() {
        return attribute;
    }

	public String getAttributeName() {
		if(attribute != null) {
			return attribute.getName();
		}
		return attributeName;
	}

    public T getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }
}

