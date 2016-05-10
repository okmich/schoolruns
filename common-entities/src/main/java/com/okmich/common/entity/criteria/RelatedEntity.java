/*
 * RelatedEntity.java
 *
 * Created on February 16, 2008, 5:10 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.okmich.common.entity.criteria;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author m-enudi
 */
@Documented
@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RelatedEntity {
    /**
     * thiis method shows the alias that the developer may want to give to the
     * related entity. This alias will be used in the join query, so it must be
     * unique
     *
     * @return String
     */
    String entityAlias();
    /**
     * this is the Related entity name being referred to. It should be the name of the entity
     *
     * @return String.
     */
    String referencedEntity();
}
