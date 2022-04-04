package de.promove.autokss.dao;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.metamodel.Attribute;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 29.09.2010
 * Time: 21:33:05
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
public class QueryFetch {

    private List<QueryFetchEntry> attributeNames = new ArrayList<QueryFetchEntry>();

    public QueryFetch(Attribute attributeName) {
        attributeNames.add(new QueryFetchEntry(attributeName, JoinType.INNER));
    }

    public QueryFetch(Attribute attributeName, JoinType joinType) {
        attributeNames.add(new QueryFetchEntry(attributeName, joinType));
    }

    public static QueryFetch withInnerJoin(Attribute attributeName) {
        return new QueryFetch(attributeName, JoinType.INNER);
    }

    public static QueryFetch withLeftJoin(Attribute attributeName) {
        return new QueryFetch(attributeName, JoinType.LEFT);
    }

    public static QueryFetch withRighJoin(Attribute attributeName) {
        return new QueryFetch(attributeName, JoinType.RIGHT);
    }

    public QueryFetch andInnerJoin(Attribute attributeName) {
        attributeNames.add(new QueryFetchEntry(attributeName, JoinType.INNER));
        return this;
    }

    public QueryFetch andLeftJoin(Attribute attributeName) {
        attributeNames.add(new QueryFetchEntry(attributeName, JoinType.LEFT));
        return this;
    }

    public QueryFetch andRightJoin(Attribute attributeName) {
        attributeNames.add(new QueryFetchEntry(attributeName, JoinType.RIGHT));
        return this;
    }

    public List<QueryFetchEntry> getFetches() {
        return attributeNames;
    }
}
