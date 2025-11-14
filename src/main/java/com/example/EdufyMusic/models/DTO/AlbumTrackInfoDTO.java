package com.example.EdufyMusic.models.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

// ED-74-SJ
@JsonInclude(JsonInclude.Include.NON_NULL) // ED-275-SJ
public class AlbumTrackInfoDTO {

    private Long albumId;
    private String albumTitle;
    private Integer trackIndex;

    public AlbumTrackInfoDTO(){}

    public AlbumTrackInfoDTO(Long albumId, String albumTitle, Integer trackIndex)
    {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.trackIndex = trackIndex;
    }

    public Long getAlbumId() {return albumId;}
    public void setAlbumId(Long albumId) {this.albumId = albumId;}

    public String getAlbumTitle() {return albumTitle;}
    public void setAlbumTitle(String albumTitle) {this.albumTitle = albumTitle;}

    public Integer getTrackIndex() {return trackIndex;}
    public void setTrackIndex(Integer trackIndex) {this.trackIndex = trackIndex;}

    @Override
    public String toString() {
        return "AlbumTrackInfoDTO{" +
                "albumId=" + albumId +
                ", albumTitle='" + albumTitle + '\'' +
                ", trackIndex=" + trackIndex +
                '}';
    }
}
