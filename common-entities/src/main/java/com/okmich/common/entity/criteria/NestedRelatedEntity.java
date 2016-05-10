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
public @interface NestedRelatedEntity {
    /**
     *
     *
     */
    String nestedEntityAlias();
    /**
     * this is the Related entity name being referred to. It should be the name of the entity
     *
     * @return String.
     */
    String nestedEntityReferenced();
}
