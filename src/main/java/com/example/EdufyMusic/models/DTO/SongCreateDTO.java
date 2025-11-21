package com.example.EdufyMusic.models.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SongCreateDTO {

    @NotEmpty private String title;

    @NotBlank private String url;

    @NotNull  private LocalTime length;

    @NotNull private LocalDate releaseDate;

    private Long albumId;

    private Integer trackIndex;

    @Valid  private List<Long> creatorIds;

    @Valid private List<Long> genreIds;

    @Valid private AlbumCreateDTO album;

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

    public AlbumCreateDTO getAlbum() {return album;}
    public void setAlbum(AlbumCreateDTO album) {this.album = album;}

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
                ", album=" + album +
                ", active=" + active +
                '}';
    }
}
