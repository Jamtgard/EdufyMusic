package com.example.EdufyMusic.models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SongCreateDTO {

    @NotEmpty private String title;

    @NotBlank private String url;

    @NotEmpty private LocalTime length;

    @NotEmpty private LocalDate releaseDate;

    @NotEmpty private Long albumId;

    @NotEmpty private Integer trackIndex;

    @NotEmpty private List<Long> creatorIds;

    @NotEmpty private List<Long> genreIds;

    private boolean active;

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public LocalTime getLength() {return length;}
    public void setLength(LocalTime length) {this.length = length;}

    public LocalDate getReleaseDate() {return releaseDate;}
    public void setReleaseDate(LocalDate releaseDate) {this.releaseDate = releaseDate;}

    public Long getAlbumId() {return albumId;}
    public void setAlbumId(Long albumId) {this.albumId = albumId;}

    public Integer getTrackIndex() {return trackIndex;}
    public void setTrackIndex(Integer trackIndex) {this.trackIndex = trackIndex;}

    public List<Long> getCreatorIds() {return creatorIds;}
    public void setCreatorIds(List<Long> creatorIds) {this.creatorIds = creatorIds;}

    public List<Long> getGenreIds() {return genreIds;}
    public void setGenreIds(List<Long> genreIds) {this.genreIds = genreIds;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

    @Override
    public String toString() {
        return "SongCreateDTO{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", releaseDate=" + releaseDate +
                ", albumId=" + albumId +
                ", trackIndex=" + trackIndex +
                ", creatorId=" + creatorIds +
                ", genreIds=" + genreIds +
                ", active=" + active +
                '}';
    }
}
