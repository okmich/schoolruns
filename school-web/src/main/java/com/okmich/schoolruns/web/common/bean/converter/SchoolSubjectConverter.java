/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean.converter;

import com.okmich.schoolruns.common.entity.SchoolSubject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Michael
 */
@FacesConverter("schoolSubjectConverter")
public class SchoolSubjectConverter implements Converter {

    public SchoolSubjectConverter() {
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return new SchoolSubject(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        }
        Integer schoolSubjectId = ((SchoolSubject) o).getSchoolSubjectId();
        return schoolSubjectId != null ? schoolSubjectId.toString() : null;
    }
}
