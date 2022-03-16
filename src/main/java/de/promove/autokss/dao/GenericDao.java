/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.promove.autokss.dao;

import de.promove.autokss.model.IdEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is which provides some convenient methods for doing CRUD operations.
 * See how to use it in the GenericDaoTest
 *
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
@Repository
@Qualifier(value = "genericDao")
public class GenericDao {

	@PersistenceContext
	protected EntityManager em;

	// Named Query Methods

	public <T> T findNamedQueryResult(Class<T> clazz, String queryName, Map<String, Object> parameters) {
		TypedQuery<T> query = createTypedNamedQuery(clazz, queryName, parameters);
		return query.getSingleResult();
	}

	public <T> List<T> listNamedQueryResult(Class<T> clazz, String queryName, Map<String, Object> parameters) {
		TypedQuery<T> query = createTypedNamedQuery(clazz, queryName, parameters);
		return query.getResultList();
	}

	private <T> TypedQuery<T> createTypedNamedQuery(Class<T> clazz, String name, Map<String, Object> parameters) {
		TypedQuery<T> query = em.createNamedQuery(name, clazz);
		for (String paramName : parameters.keySet()) {
			query.setParameter(paramName, parameters.get(paramName));
		}
		return query;
	}

	// Get Methods; returns List<T>

	public <T> List<T> listAll(Class<T> clazz, QueryFetch... queryFetches) {
		return list(clazz, null, null, queryFetches);
	}

	public <T> List<T> list(Class<T> clazz, QueryParameter queryParameter, QueryOrder queryOrder, QueryFetch... queryFetches) {
		CriteriaQuery<T> query = createCriteriaQuery(clazz, queryParameter, queryOrder, queryFetches);
		return listCriteriaQueryResult(query);
	}

	public <T> List<T> list(Class<T> clazz, QueryParameter queryParameter, QueryFetch... queryFetches) {
		CriteriaQuery<T> query = createCriteriaQuery(clazz, queryParameter, null, queryFetches);
		return listCriteriaQueryResult(query);
	}

	public <T> List<T> list(Class<T> clazz, QueryParameter queryParameter, QueryOrder queryOrder, int first, int max, QueryFetch... queryFetches) {
		CriteriaQuery<T> query = createCriteriaQuery(clazz, queryParameter, queryOrder, queryFetches);
		return listCriteriaQueryResult(query, first, max);
	}

	public <T> Long count(Class<T> clazz, QueryParameter queryParameter, QueryFetch... queryFetches) {
		return createCountCriteriaQuery(clazz, queryParameter, queryFetches);
	}

	// find Methods; returns T

	public <T> T findById(Class<T> clazz, Object id) {
		return em.find(clazz, id);
	}

	public <T> T findById(Class<T> clazz, Object id, QueryFetch... queryFetches) {
		return find(clazz, QueryParameter.with("id", id), queryFetches);
	}

	public <T> T find(Class<T> clazz, QueryParameter queryParameter, QueryFetch... queryFetches) {
		CriteriaQuery<T> query = createCriteriaQuery(clazz, queryParameter, null, queryFetches);
		return findCriteriaQueryResult(query);
	}

	private <T> CriteriaQuery<T> createCriteriaQuery(Class<T> clazz, QueryParameter queryParameter, QueryOrder queryOrder, QueryFetch... queryFetches) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(clazz);
		Root<T> root = query.from(clazz);

		if (queryFetches != null) {
			applyQueryFetches(root, queryFetches);
		}

		if (queryParameter != null) {
			List<Predicate> predicates = createPredicates(cb, root, queryParameter);
			query.where(predicates.toArray(new Predicate[predicates.size()]));
		}

		if (queryOrder != null) {
			List<Order> orders = createOrders(cb, root, queryOrder);
			query.orderBy(orders);
		}

		query.distinct(true);
		return query;
	}

	private Long createCountCriteriaQuery(Class clazz, QueryParameter queryParameter, QueryFetch... queryFetches) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root root = query.from(clazz);
		query = query.select(cb.countDistinct(root));

		if (queryFetches != null) {
			applyQueryFetches(root, queryFetches);
		}

		if (queryParameter != null) {
			List<Predicate> predicates = createPredicates(cb, root, queryParameter);
			query.where(predicates.toArray(new Predicate[predicates.size()]));
		}

		query.distinct(true);
		return em.createQuery(query).getSingleResult();
	}

	private <T> void applyQueryFetches(Root<T> root, QueryFetch[] queryFetches) {
		for (QueryFetch queryFetch : queryFetches) {
			Fetch<Object, Object> recentFetch = null;
			for (QueryFetchEntry entry : queryFetch.getFetches()) {
				String attributeName = entry.getAttribute().getName();
				JoinType joinType = entry.getJoinType();
				if (recentFetch == null) {
					recentFetch = root.fetch(attributeName, joinType);
				} else {
					recentFetch = recentFetch.fetch(attributeName, joinType);
				}
			}
		}
	}

	private <T> List<Order> createOrders(CriteriaBuilder cb, Root<T> root, QueryOrder queryOrder) {
		List<Order> orders = new ArrayList<Order>();
		for (QueryOrderEntry entry : queryOrder.getOrders()) {
			String attribute = entry.getAttributeName();
			QueryOrder.OrderDirection direction = entry.getDirection();
			if (QueryOrder.OrderDirection.ASC.equals(direction)) {
				orders.add(cb.asc(root.get(attribute)));
			} else if (QueryOrder.OrderDirection.DESC.equals(direction)) {
				orders.add(cb.desc(root.get(attribute)));
			}
		}
		return orders;
	}

	private <T> List<Predicate> createPredicates(CriteriaBuilder cb, Root<T> root, QueryParameter queryParameter) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (queryParameter.parameters() != null) {
			for (QueryParameterEntry entry : queryParameter.parameters()) {
				Expression path = root.get(entry.getAttributeName());

				Object value = entry.getValue();
				Predicate predicate = null;
				if (QueryParameterEntry.Operator.EQ.equals(entry.getOperator())) {
					predicate = cb.equal(path, value);
				} else if (value instanceof Number) {
					Number number = (Number) value;
					if (QueryParameterEntry.Operator.GT.equals(entry.getOperator())) {
						predicate = cb.gt(path, number);
					} else if (QueryParameterEntry.Operator.GE.equals(entry.getOperator())) {
						predicate = cb.ge(path, number);
					} else if (QueryParameterEntry.Operator.LT.equals(entry.getOperator())) {
						predicate = cb.lt(path, number);
					} else if (QueryParameterEntry.Operator.LE.equals(entry.getOperator())) {
						predicate = cb.le(path, number);
					}
				} else if (value instanceof Comparable) {
					Comparable comp = (Comparable) value;

					if (QueryParameterEntry.Operator.GT.equals(entry.getOperator())) {
						predicate = cb.greaterThan(path, comp);
					} else if (QueryParameterEntry.Operator.GE.equals(entry.getOperator())) {
						predicate = cb.greaterThanOrEqualTo(path, comp);
					} else if (QueryParameterEntry.Operator.LT.equals(entry.getOperator())) {
						predicate = cb.lessThan(path, comp);
					} else if (QueryParameterEntry.Operator.LE.equals(entry.getOperator())) {
						predicate = cb.lessThanOrEqualTo(path, comp);
					}

					if (value instanceof String) {
						String string = (String) value;
						if (QueryParameterEntry.Operator.STARTS.equals(entry.getOperator())) {
							predicate = cb.like(path, string + "%");
						} else if (QueryParameterEntry.Operator.CONTAINS.equals(entry.getOperator())) {
							predicate = cb.like(path, "%" + string + "%");
						} else if (QueryParameterEntry.Operator.ENDS.equals(entry.getOperator())) {
							predicate = cb.like(path, "%" + string);
						}
					}

				}
				if (predicate != null) {
					predicates.add(predicate);
				}
			}
		}
		return predicates;
	}

	protected <T> T findCriteriaQueryResult(CriteriaQuery<T> criteriaQuery) {
		T result = null;
		try {
			result = em.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException e) {
			// Nothing found; return null
		}
		return result;
	}

	protected <T> List<T> listCriteriaQueryResult(CriteriaQuery<T> criteriaQuery, int first, int max) {
		TypedQuery<T> query = em.createQuery(criteriaQuery);
		query.setFirstResult(first);
		query.setMaxResults(max);
		return query.getResultList();
	}

	protected <T> List<T> listCriteriaQueryResult(CriteriaQuery<T> criteriaQuery) {
		TypedQuery<T> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Transactional
	public void persist(Object entity) {
		em.persist(entity);
	}

	@Transactional
	public <T> T merge(T entity) {
		return em.merge(entity);
	}

	@Transactional
	public <T extends IdEntity> void remove(T entity) {
		IdEntity attachedEntity = findById(entity.getClass(), entity.getId());
		em.remove(attachedEntity);
	}

	public void flushClear() {
		em.flush();
		em.clear();
	}

	public void detach(Object entity) {
		em.detach(entity);
	}

}
