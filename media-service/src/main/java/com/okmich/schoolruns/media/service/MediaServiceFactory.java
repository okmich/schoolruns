/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.media.service;

import java.io.Serializable;

/**
 *
 * @author Michael
 * @since Aug 5, 2013
 * @company Okmich Ltd.
 */
public final class MediaServiceFactory implements Serializable {

    private MediaServiceFactory() {
        //do nothing
    }

    /**
     *
     *
     * @param userName
     * @param password
     * @return ImageService - an implementation of the photo store endpoint
     * @throws IllegalArgumentException - if the username and password fail to
     * authenticate the system on the file cloud store
     */
    public static synchronized ImageService getImageService(String userName, String password)
            throws IllegalArgumentException {
        return new PicasaImageServiceImpl(userName, password);
    }
}
