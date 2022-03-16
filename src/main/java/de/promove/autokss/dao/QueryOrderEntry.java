package de.promove.autokss.dao;

import javax.persistence.metamodel.SingularAttribute;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 *         Date: 15.10.10
 *         Time: 17:13
 */
public class QueryOrderEntry {

    private SingularAttribute attribute;

	private String attributeName;

    private QueryOrder.OrderDirection direction;

    public QueryOrderEntry(SingularAttribute attribute, QueryOrder.OrderDirection direction) {
        this.attribute = attribute;
        this.direction = direction;
    }

	public QueryOrderEntry(String attributeName, QueryOrder.OrderDirection direction) {
		this.attributeName = attributeName;
		this.direction = direction;
	}

	public SingularAttribute getAttribute() {
        return attribute;
    }

	public String getAttributeName() {
		if(getAttribute() != null) {
			return attribute.getName();
		}
		return attributeName;
	}

	public QueryOrder.OrderDirection getDirection() {
        return direction;
    }

}
