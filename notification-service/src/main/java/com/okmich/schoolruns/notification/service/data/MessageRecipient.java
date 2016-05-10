/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.data;

import com.okmich.common.BaseData;


/**
 *
 * @author Michael
 */
public class MessageRecipient extends BaseData {

    private Object id;
    private String name;
    private String address;

    /**
     *
     * @param id - the id of the entity
     * @param name - the name of the entity
     * @param address - the message address.
     */
    public MessageRecipient(Object id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    /**
     * @return the id
     */
    public Object getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Object id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
