package com.music.application.dto;

import java.util.List;

public class PlaylistDTO {

    private Integer playlistId;
    private String name;
    private List<Integer> trackIds;

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getTrackIds() {
        return trackIds;
    }

    public void setTrackIds(List<Integer> trackIds) {
        this.trackIds = trackIds;
    }
}
