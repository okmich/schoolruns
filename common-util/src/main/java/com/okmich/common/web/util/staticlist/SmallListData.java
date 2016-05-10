/*
 * SmallListData.java
 *
 * Created on October 26, 2007, 4:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.okmich.common.web.util.staticlist;

/**
 *
 * @author OKALI CHIGOZIE DAMIAN
 */
public class SmallListData {

    private String listKey;
    private String listDescription;

    /**
     * Creates a new instance of SmallListData
     */
    public SmallListData() {
    }

    /**
     * Constructor
     *
     * @param newListDescription The listDescription to set
     * @param newListKey The listKey to set
     */
    public SmallListData(final String newListKey, final String newListDescription) {
        this.listDescription = newListDescription;
        this.listKey = newListKey;
    }

    public String getListKey() {
        return listKey;
    }

    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    @Override
    public String toString() {
        return new StringBuilder("[").append(listKey).append(", ").
                append(listDescription).append("]").toString();
    }
}
