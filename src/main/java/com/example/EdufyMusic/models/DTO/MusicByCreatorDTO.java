package com.example.EdufyMusic.models.DTO;

import java.util.List;

public class MusicByCreatorDTO {

    private List<Long> songIds;
    private List<Long> albumIds;

    public MusicByCreatorDTO() {}

    public MusicByCreatorDTO(List<Long> songIds, List<Long> albumIds) {
        this.songIds = songIds;
        this.albumIds = albumIds;
    }

    public List<Long> getSongIds() { return songIds; }
    public void setSongIds(List<Long> songIds) { this.songIds = songIds; }

    public List<Long> getAlbumIds() { return albumIds; }
    public void setAlbumIds(List<Long> albumIds) { this.albumIds = albumIds; }

    @Override
    public String toString() {
        return "MusicByCreatorDTO{" +
                "songIds=" + songIds +
                ", albumIds=" + albumIds +
                '}';
    }
}
