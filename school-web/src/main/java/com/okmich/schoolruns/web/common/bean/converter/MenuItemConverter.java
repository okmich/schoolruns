/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean.converter;

import com.okmich.schoolruns.common.entity.MenuItem;
import com.okmich.schoolruns.common.entity.Module;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Michael
 */
@FacesConverter("menuItemConverter")
public class MenuItemConverter implements Converter {

    public MenuItemConverter() {
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return new MenuItem(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        }
        Integer menuItemId = ((MenuItem) o).getMenuItemId();
        return menuItemId != null ? menuItemId.toString() : null;
    }
}
