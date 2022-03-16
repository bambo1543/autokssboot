package de.promove.autokss.dao;

import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 19.09.2010
 * Time: 20:34:02
 * <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
public class QueryOrder {

    public enum OrderDirection {ASC(1), DESC(-1), UNSORTED(0);

        private int direction;

        OrderDirection(int direction) {
            this.direction = direction;
        }

        public static OrderDirection getByInt(int direction) {
            for (OrderDirection value : OrderDirection.values()) {
                if(value.direction == direction) {
                    return value;
                }
            }
            return null;
        }
    };

    private List<QueryOrderEntry> orders = null;

    private QueryOrder(SingularAttribute name, OrderDirection orderDirection) {
        this.orders = new ArrayList<QueryOrderEntry>();
        this.orders.add(new QueryOrderEntry(name, orderDirection));
    }

    private QueryOrder(String name, OrderDirection orderDirection) {
        this.orders = new ArrayList<QueryOrderEntry>();
        this.orders.add(new QueryOrderEntry(name, orderDirection));
    }

    public static QueryOrder by(SingularAttribute name) {
        return new QueryOrder(name, OrderDirection.ASC);
    }

    public static QueryOrder by(SingularAttribute name, OrderDirection orderDirection) {
        return new QueryOrder(name, orderDirection);
    }

    public static QueryOrder by(String name) {
        return new QueryOrder(name, OrderDirection.ASC);
    }

    public static QueryOrder by(String name, OrderDirection orderDirection) {
        return new QueryOrder(name, orderDirection);
    }

    public QueryOrder and(SingularAttribute name) {
        this.orders.add(new QueryOrderEntry(name, OrderDirection.ASC));
        return this;
    }

    public QueryOrder and(SingularAttribute name, OrderDirection orderDirection) {
        this.orders.add(new QueryOrderEntry(name, orderDirection));
        return this;
    }

    public QueryOrder and(String name) {
        this.orders.add(new QueryOrderEntry(name, OrderDirection.ASC));
        return this;
    }

    public QueryOrder and(String name, OrderDirection orderDirection) {
        this.orders.add(new QueryOrderEntry(name, orderDirection));
        return this;
    }

    public List<QueryOrderEntry> getOrders() {
        return this.orders;
    }

}
