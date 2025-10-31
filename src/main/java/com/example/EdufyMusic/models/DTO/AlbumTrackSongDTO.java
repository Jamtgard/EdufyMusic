package com.example.EdufyMusic.models.DTO;

import java.time.LocalTime;

// ED-75-SJ
public class AlbumTrackSongDTO {

    private Integer trackIndex;
    private Long songId;
    private String songTitle;
    private LocalTime songLength;
    private String songUrl;

    public AlbumTrackSongDTO() {}

    public AlbumTrackSongDTO(Integer trackIndex, Long songId, String songTitle, LocalTime songLength, String songUrl) {
        this.trackIndex = trackIndex;
        this.songId = songId;
        this.songTitle = songTitle;
        this.songLength = songLength;
        this.songUrl = songUrl;
    }

    public Integer getTrackIndex() { return trackIndex; }
    public void setTrackIndex(Integer trackIndex) { this.trackIndex = trackIndex; }

    public Long getSongId() { return songId; }
    public void setSongId(Long songId) { this.songId = songId; }

    public String getSongTitle() { return songTitle; }
    public void setSongTitle(String songTitle) { this.songTitle = songTitle; }

    public LocalTime getSongLength() { return songLength; }
    public void setSongLength(LocalTime songLength) { this.songLength = songLength; }

    public String getSongUrl() { return songUrl; }
    public void setSongUrl(String songUrl) { this.songUrl = songUrl; }

    @Override
    public String toString() {
        return "AlbumTrackSongDTO{" +
                "trackIndex=" + trackIndex +
                ", songId=" + songId +
                ", songTitle='" + songTitle + '\'' +
                ", songLength=" + songLength +
                ", songUrl='" + songUrl + '\'' +
                '}';
    }
}
