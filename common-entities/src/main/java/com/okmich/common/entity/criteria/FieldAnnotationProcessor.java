package com.okmich.common.entity.criteria;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since February 26, 2008, 4:27 AM
 */
public class FieldAnnotationProcessor {

	/**
	 * Creates a new instance of FieldAnnotationProcessor
	 */
	public FieldAnnotationProcessor() {
	}

	public static Map<String, String> processField(Object object,
			String fieldName) {
		Map<String, String> returnMap = new HashMap<String, String>();
		try {
			Field field = getInstanceVariable(object.getClass(), fieldName);
			field.setAccessible(true);
			if (field.isAnnotationPresent(RelatedEntity.class)) {
				RelatedEntity annotation = field
						.getAnnotation(RelatedEntity.class);
				returnMap.put("entityAlias", annotation.entityAlias());
				returnMap
						.put("referencedEntity", annotation.referencedEntity());
				if (field.isAnnotationPresent(NestedRelatedEntity.class)) {
					NestedRelatedEntity nestedAnnotation = field
							.getAnnotation(NestedRelatedEntity.class);
					returnMap.put("nestedEntityAlias",
							nestedAnnotation.nestedEntityAlias());
					returnMap.put("nestedEntityReferenced",
							nestedAnnotation.nestedEntityReferenced());
				}
			} else {
				return null;
			}
		} catch (SecurityException ex) {
			ex.printStackTrace();
		}
		return returnMap;
	}

	/**
	 * loops through an entire hierarchy of class object in search of a
	 * particular field. returns null if the search is unsuccessful, else
	 * returns the Field object.
	 * 
	 * @param Class
	 *            the class object to search
	 * @param String
	 *            - the field name
	 * @return Field or null if the search is unsuccessful
	 */
	public static <T> Field getInstanceVariable(
			@SuppressWarnings("rawtypes") Class cls, String fieldName) {
		Field returnField = null;
		while (cls != null) {
			try {
				returnField = cls.getDeclaredField(fieldName);
				if (returnField != null) {
					break;
				}
			} catch (NoSuchFieldException ex) {
			}
			cls = cls.getSuperclass();
		}
		return returnField;
	}

}