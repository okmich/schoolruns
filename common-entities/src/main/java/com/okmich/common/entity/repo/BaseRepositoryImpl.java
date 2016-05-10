/**
 *
 */
package com.okmich.common.entity.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.BaseQueryCriteria;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * @author Michael
 *
 */
public class BaseRepositoryImpl<T, ID extends Serializable, Q extends BaseEntityQueryCriteria>
        extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID, Q> {

    @Resource
    private EntityManager entityManager;
    /**
     * EMPTY_STRING
     */
    //private final static String EMPTY_STRING = " ";
    /**
     * PRIMARY_ENTITY
     */
    private final String PRIMARY_ENTITY = "{PRIMARY_ENTITY}";

    /**
     * constructor
     */
    public BaseRepositoryImpl(Class<T> entityClass, EntityManager _entityManager) {
        super(entityClass, _entityManager);
        //set this entitymanager
        this.entityManager = _entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> retrieveList(
            Q q) {

        String queryString = buildQuery(q);
        Query query = entityManager.createQuery(queryString);
        setQueryParameter(query, q.getParameters());
        return query.getResultList();
    }

    /**
     *
     *
     */
    private String buildQuery(BaseQueryCriteria baseQueryBuilder) {
        String sqlScript = baseQueryBuilder.getSqlString();
        sqlScript = sqlScript.replace(PRIMARY_ENTITY,
                baseQueryBuilder.getEntityName());
        System.out.println(sqlScript);
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