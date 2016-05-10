/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean.converter;

import com.okmich.schoolruns.common.entity.Facility;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Michael
 */
@FacesConverter("facilityConverter")
public class FacilityConverter implements Converter {

    public FacilityConverter() {
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return new Facility(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        }
        Integer facilityId = ((Facility) o).getFacilityId();
        return facilityId != null ? facilityId.toString() : null;
    }
}
