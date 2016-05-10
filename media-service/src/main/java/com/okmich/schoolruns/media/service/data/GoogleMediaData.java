/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.media.service.data;

import java.io.Serializable;

/**
 *
 * @author Michael
 * @since Aug 23, 2013
 * @company Okmich Ltd.
 */
public class GoogleMediaData implements Serializable {

    private String userId;
    private String albumId;

    protected GoogleMediaData() {
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the albumId
     */
    public String getAlbumId() {
        return albumId;
    }

    /**
     * @param albumId the albumId to set
     */
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
