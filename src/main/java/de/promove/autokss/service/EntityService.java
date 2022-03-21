package de.promove.autokss.service;

import de.promove.autokss.dao.GenericDao;
import de.promove.autokss.dao.QueryFetch;
import de.promove.autokss.dao.QueryOrder;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.IdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
@Service
@Transactional
public class EntityService<T extends IdEntity> {

	@Autowired
	private GenericDao genericDao;

	private Class<T> clazz;

	public EntityService() {}

	public EntityService(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T findNamedQueryResult(String queryName, Map<String, Object> parameters) {
		return genericDao.findNamedQueryResult(clazz, queryName, parameters);
	}

	public List<T> listNamedQueryResult(String queryName, Map<String, Object> parameters) {
		return genericDao.listNamedQueryResult(clazz, queryName, parameters);
	}

	public List<T> listAll(QueryFetch... queryFetches) {
		return genericDao.listAll(clazz, queryFetches);
	}

	public List<T> list(QueryParameter queryParameter, QueryOrder queryOrder, QueryFetch... queryFetches) {
		return genericDao.list(clazz, queryParameter, queryOrder, queryFetches);
	}

	public List<T> list(QueryParameter queryParameter, QueryOrder queryOrder, int first, int max, QueryFetch... queryFetches) {
		return genericDao.list(clazz, queryParameter, queryOrder, first, max, queryFetches);
	}

	public Long count(QueryParameter queryParameter, QueryFetch... queryFetches) {
		return genericDao.count(clazz, queryParameter, queryFetches);
	}
	public Long count() {
		return genericDao.count(clazz);
	}

	public T findById(Object id) {
		return genericDao.findById(clazz, id);
	}

	public T findById(Object id, QueryFetch... queryFetches) {
		return genericDao.findById(clazz, id, queryFetches);
	}

	public T find(QueryParameter queryParameter, QueryFetch... queryFetches) {
		return genericDao.find(clazz, queryParameter, queryFetches);
	}

	public void persist(T entity) {
		genericDao.persist(entity);
	}

	public T merge(T entity) {
		return genericDao.merge(entity);
	}

	public void remove(T entity) {
		genericDao.remove(entity);
	}

	public void flushClear() {
		genericDao.flushClear();
	}

	public void detach(Object entity) {
		genericDao.detach(entity);
	}
}
