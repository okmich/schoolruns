/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.web.util.fileimport;

import com.okmich.common.BaseData;
import java.util.List;

/**
 *
 * @author m-enudi
 */
public class ExcelFileImportData extends BaseData {

    private String field1;
    private String field2;
    private String field3;
    private List<String> columnNames;
    private List<List<String>> data;

    public ExcelFileImportData() {
    }

    /**
     * @return the columnNames
     */
    public List<String> getColumnNames() {
        return columnNames;
    }

    /**
     * @param _columnNames the columnNames to set
     */
    public void setColumnNames(List<String> _columnNames) {
        this.columnNames = _columnNames;
    }

    /**
     * @return the data
     */
    public List<List<String>> getData() {
        return data;
    }

    /**
     * @param _data the data to set
     */
    public void setData(List<List<String>> _data) {
        this.data = _data;
    }

    /**
     * @return the field1
     */
    public String getField1() {
        return field1;
    }

    /**
     * @param field1 the field1 to set
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }

    /**
     * @return the field2
     */
    public String getField2() {
        return field2;
    }

    /**
     * @param field2 the field2 to set
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }

    /**
     * @return the field3
     */
    public String getField3() {
        return field3;
    }

    /**
     * @param field3 the field3 to set
     */
    public void setField3(String field3) {
        this.field3 = field3;
    }
}