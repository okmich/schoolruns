package com.okmich.common.entity.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Michael
 *
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable, Q extends BaseEntityQueryCriteria>
        extends JpaRepository<T, ID>, Serializable {

    List<T> retrieveList(
            Q q);
}
