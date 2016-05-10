/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.media.service;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaStreamSource;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.okmich.schoolruns.media.service.data.ImageFile;
import com.okmich.schoolruns.media.service.data.PhotoAlbum;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 * @since Aug 5, 2013
 * @company Okmich Ltd.
 */
public class PicasaImageServiceImpl implements ImageService {

    private PicasawebService picasawebService;
    private static final String USER_ID = "USER_ID";
    private static final String ALBUM_ID = "ALBUM_ID";

    /**
     *
     *
     * @param user
     * @param password
     * @throws IllegalArgumentException - if username and password fail
     * authentications
     */
    protected PicasaImageServiceImpl(String user, String password)
            throws IllegalArgumentException {

        picasawebService = new PicasawebService("schoolruns.com");
        try {
            picasawebService.setUserCredentials(user, password);
        } catch (AuthenticationException ex) {
            Logger.getLogger(PicasaImageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ImageFile addPhoto(ImageFile imageFile) throws Exception {
        if (imageFile.getUserId() == null || imageFile.getAlbumId() == null) {
            throw new IllegalArgumentException("INVALID_USER_ID_ALBUM_ID");
        }
        String albumPostUrlString = "https://picasaweb.google.com/data/feed/api/user/USER_ID/albumid/ALBUM_ID";

        albumPostUrlString = albumPostUrlString.replaceAll(USER_ID, imageFile.getUserId());
        albumPostUrlString = albumPostUrlString.replaceAll(ALBUM_ID, imageFile.getAlbumId());

        URL albumPostUrl;
        try {
            albumPostUrl = new URL(albumPostUrlString);
        } catch (MalformedURLException ex) {
            throw new Exception(ex.getMessage());
        }

        PhotoEntry photoEntry = new PhotoEntry();
        photoEntry.setTitle(new PlainTextConstruct(imageFile.getTitle()));
        photoEntry.setDescription(new PlainTextConstruct(imageFile.getDescription()));
        photoEntry.setClient(imageFile.getClient());

        MediaStreamSource mediaSource = new MediaStreamSource(
                imageFile.getInputStream(), imageFile.getContentType());
        photoEntry.setMediaSource(mediaSource);
        try {
            photoEntry = picasawebService.insert(albumPostUrl, photoEntry);
            if (photoEntry != null) {
                imageFile.setUrl(photoEntry.getMediaGroup().getContents().get(0).getUrl());
            }
        } catch (IOException | ServiceException ex) {
            Logger.getLogger(PicasaImageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
        return imageFile;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public PhotoAlbum createPhotoAlbum(PhotoAlbum photoAlbum) throws Exception {
        String albumPostUrlString = "https://picasaweb.google.com/data/feed/api/user/USER_ID";
        AlbumEntry myAlbum = new AlbumEntry();
        albumPostUrlString = albumPostUrlString.replaceAll(USER_ID, photoAlbum.getUserId());

        URL albumPostUrl;
        try {
            albumPostUrl = new URL(albumPostUrlString);
        } catch (MalformedURLException ex) {
            throw new Exception(ex.getMessage());
        }
        myAlbum.setTitle(new PlainTextConstruct(photoAlbum.getTitle()));
        myAlbum.setDescription(new PlainTextConstruct(photoAlbum.getDescription()));

        AlbumEntry insertedEntry;
        try {
            insertedEntry = picasawebService.insert(albumPostUrl, myAlbum);
            photoAlbum.setAlbumId(insertedEntry == null ? null : insertedEntry.getGphotoId());
        } catch (IOException | ServiceException ex) {
            Logger.getLogger(PicasaImageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
        return photoAlbum;
    }
}