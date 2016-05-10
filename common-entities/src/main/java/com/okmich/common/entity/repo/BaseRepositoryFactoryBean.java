/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.entity.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 *
 * @author Michael
 */
public class BaseRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, ID> {

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

        return new MyRepositoryFactory(entityManager);
    }

    /**
     *
     * @param <T>
     * @param <I>
     */
    private static class MyRepositoryFactory<T, ID extends Serializable, Q extends BaseEntityQueryCriteria>
            extends JpaRepositoryFactory {

        /**
         *
         */
        private EntityManager entityManager;

        /**
         * constructor
         *
         * @param entityManager
         */
        public MyRepositoryFactory(EntityManager entityManager) {
            super(entityManager);

            this.entityManager = entityManager;
        }

//        @Override
        protected Object getTargetRepository(RepositoryMetadata metadata) {
            return new BaseRepositoryImpl<>((Class<T>) metadata.getDomainType(), entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            // The RepositoryMetadata can be safely ignored, it is used by the JpaRepositoryFactory
            //to check for QueryDslJpaRepository's which is out of scope.
            return BaseRepositoryImpl.class;
        }
    }
}
