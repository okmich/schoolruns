/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.media.service.data;

/**
 *
 * @author Michael
 * @since Aug 5, 2013
 * @company Okmich Ltd.
 */
public class PhotoAlbum extends GoogleMediaData {

    private String id;
    private String title;
    private String description;
    

    public PhotoAlbum() {
    }

    /**
     *
     * @param id
     */
    public PhotoAlbum(String id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
