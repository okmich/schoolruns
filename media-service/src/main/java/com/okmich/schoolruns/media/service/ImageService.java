/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.media.service;

import com.okmich.schoolruns.media.service.data.ImageFile;
import com.okmich.schoolruns.media.service.data.PhotoAlbum;
import java.io.Serializable;

/**
 *
 * @author Michael
 * @since Aug 5, 2013
 * @company Okmich Ltd.
 */
public interface ImageService extends Serializable {

    /**
     *
     *
     * @param photoAlbum
     * @return
     * @throws Exception
     */
    PhotoAlbum createPhotoAlbum(PhotoAlbum photoAlbum) throws Exception;
//
//    List<AlbumData> listAlbums();
//
//    void tagAlbum();
//
//    CommentData addCommentToAlbum(CommentData commentData);

    /**
     *
     * @param imageFile
     * @return ImageFile
     * @throws Exception
     */
    ImageFile addPhoto(ImageFile imageFile) throws Exception;
//    List<PhotoData> addPhotos(List<InputStream> inputStreams, Map<String, String> properties);
//
//    CommentData addCommentToPhoto(CommentData commentData);
//
//    List<CommentData> listPhotoComments();
//    
//    List<CommentData> listPhotoTags();
}
