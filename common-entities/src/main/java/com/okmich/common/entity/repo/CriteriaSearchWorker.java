/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.entity.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.BaseQueryCriteria;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Michael
 */
public class CriteriaSearchWorker<T extends Serializable, Q extends BaseEntityQueryCriteria>
        implements Serializable {

    /**
     *
     */
    private final String PRIMARY_ENTITY = "{PRIMARY_ENTITY}";

    public CriteriaSearchWorker() {
    }

    /**
     *
     *
     * @param entityManager - entityManager
     * @param q - implementation of a {@link  BaseEntityQueryCriteria}
     * @return List<T> - a list of entity object {@link T} that matches the
     * criteria in the object {@code q}
     * @throws IllegalArgumentException - if the query criteria class passed in
     * is configured to always require at least one criteria but the current
     * object has none
     */
    public List<T> findByCriteria(EntityManager entityManager, Q q) {
        String queryString = buildQuery(q);
        Query query = entityManager.createQuery(queryString);
//        if (q.isWhereClauseRequired() && q.getParameters().isEmpty()) {
//            throw new IllegalArgumentException(ErrorConstants.ERROR_NO_CRITERIA_SET);
//        }
        setQueryParameter(query, q.getParameters());
        return query.getResultList();
    }

    /**
     *
     *
     */
    private String buildQuery(BaseQueryCriteria baseQueryBuilder) {
        String entityName = baseQueryBuilder.getEntityName();
        String sqlScript = baseQueryBuilder.getSqlString();
        sqlScript = sqlScript.replace(PRIMARY_ENTITY, entityName);
        return sqlScript;
    }

    /**
     * sets the various parameters on the JPQL using the map that is passed in.
     *
     * @param Query - the query object to be set
     * @Map - parameters
     */
    private void setQueryParameter(Query query, Map<String, Object> parameters) {
        Set<String> keySet = parameters.keySet();
        if (keySet.size() > 0) {
            for (String placeHolder : keySet) {
                Object object = parameters.get(placeHolder);

                if (object instanceof String) {
                    object = ((String) object).toLowerCase();
                }
                query.setParameter(placeHolder, object);
            }
        }
    }
}