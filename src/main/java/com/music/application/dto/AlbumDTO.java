package com.music.application.dto;

import java.util.List;

public class AlbumDTO {

    private Integer albumId;
    private String title;
    private Integer artistId;
    private List<Integer> trackIds;

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public List<Integer> getTrackIds() {
        return trackIds;
    }

    public void setTrackIds(List<Integer> trackIds) {
        this.trackIds = trackIds;
    }
}
