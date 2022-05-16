package de.promove.autokss.dao.generic;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.metamodel.Attribute;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 *         Date: 15.10.10
 *         Time: 17:12
 */
public class QueryFetchEntry {

    private Attribute attribute;

    private JoinType joinType;

    public QueryFetchEntry(Attribute attribute, JoinType joinType) {
        this.attribute = attribute;
        this.joinType = joinType;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

}
