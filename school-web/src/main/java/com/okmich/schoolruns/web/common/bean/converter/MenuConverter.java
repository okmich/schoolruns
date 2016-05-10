/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean.converter;

import com.okmich.schoolruns.common.entity.Menu;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Michael
 */
@FacesConverter("menuConverter")
public class MenuConverter implements Converter {

    public MenuConverter() {
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return new Menu(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        }
        Integer menuId = ((Menu) o).getMenuId();
        return menuId != null ? menuId.toString() : null;
    }
}
