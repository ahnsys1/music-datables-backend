package com.music.application.dto;

import java.util.ArrayList;
import java.util.List;

public class TrackDTO {

    private Integer trackId;
    private String name;
    private String composer;
    private Integer milliseconds;
    private Integer bytes;
    private Double unitPrice;
    private Integer albumId;
    private Integer mediaTypeId;
    private Integer genreId;
    private List<Integer> invoiceLineIds = new ArrayList<>();
    private List<Integer> playlistIds = new ArrayList<>();

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public Integer getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Integer milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getMediaTypeId() {
        return mediaTypeId;
    }

    public void setMediaTypeId(Integer mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public List<Integer> getInvoiceLineIds() {
        return invoiceLineIds;
    }

    public void setInvoiceLineIds(List<Integer> invoiceLineIds) {
        this.invoiceLineIds = invoiceLineIds;
    }

    public List<Integer> getPlaylistIds() {
        return playlistIds;
    }

    public void setPlaylistIds(List<Integer> playlistIds) {
        this.playlistIds = playlistIds;
    }
}
