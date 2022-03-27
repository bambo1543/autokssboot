package de.promove.autokss.service;

import de.promove.autokss.dao.GenericDao;
import de.promove.autokss.dao.QueryFetch;
import de.promove.autokss.dao.QueryOrder;
import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.IdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
@Service
@Transactional
public class GenericService {

	@Autowired
	private GenericDao genericDao;

	public <T> T findNamedQueryResult(Class<T> clazz, String queryName, Map<String, Object> parameters) {
		return genericDao.findNamedQueryResult(clazz, queryName, parameters);
	}

	public <T> List<T> listNamedQueryResult(Class<T> clazz, String queryName, Map<String, Object> parameters) {
		return genericDao.listNamedQueryResult(clazz, queryName, parameters);
	}

	public <T> List<T> listAll(Class<T> clazz, QueryFetch... queryFetches) {
		return genericDao.listAll(clazz, queryFetches);
	}

	public <T> List<T> listAll(Class<T> clazz, QueryOrder queryOrder, QueryFetch... queryFetches) {
		return genericDao.listAll(clazz, queryOrder, queryFetches);
	}

	public <T> List<T> list(Class<T> clazz, QueryParameter queryParameter, QueryOrder queryOrder, QueryFetch... queryFetches) {
		return genericDao.list(clazz, queryParameter, queryOrder, queryFetches);
	}

	public <T> List<T> list(Class<T> clazz, QueryParameter queryParameter, QueryOrder queryOrder, int first, int max, QueryFetch... queryFetches) {
		return genericDao.list(clazz, queryParameter, queryOrder, first, max, queryFetches);
	}

	public <T> Long count(Class<T> clazz, QueryParameter queryParameter, QueryFetch... queryFetches) {
		return genericDao.count(clazz, queryParameter, queryFetches);
	}

	public <T> T findById(Class<T> clazz, Object id) {
		return genericDao.findById(clazz, id);
	}

	public <T> T findById(Class<T> clazz, Object id, QueryFetch... queryFetches) {
		return genericDao.findById(clazz, id, queryFetches);
	}

	public <T> T find(Class<T> clazz, QueryParameter queryParameter, QueryFetch... queryFetches) {
		return genericDao.find(clazz, queryParameter, queryFetches);
	}

	public void persist(Object entity) {
		genericDao.persist(entity);
	}

	public void persistAll(Collection<?> entities) {
		for (Object entity : entities) {
			persist(entity);
		}
	}

	public <T> T merge(T entity) {
		return genericDao.merge(entity);
	}

	public <T extends IdEntity> void remove(T entity) {
		genericDao.remove(entity);
	}

	public void flushClear() {
		genericDao.flushClear();
	}

	public void detach(Object entity) {
		genericDao.detach(entity);
	}
}
