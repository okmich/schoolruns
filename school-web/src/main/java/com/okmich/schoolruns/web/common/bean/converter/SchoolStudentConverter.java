/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean.converter;

import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Michael
 */
@FacesConverter("schoolStudentConverter")
public class SchoolStudentConverter implements Converter {

    public SchoolStudentConverter() {
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        SchoolStudentData schoolStudentData = new SchoolStudentData();
        schoolStudentData.setSchoolStudentId(Integer.valueOf(string));
        return schoolStudentData;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        }
        Integer schoolStudentId = ((SchoolStudentData) o).getSchoolStudentId();
        return schoolStudentId != null ? schoolStudentId.toString() : null;
    }
}
