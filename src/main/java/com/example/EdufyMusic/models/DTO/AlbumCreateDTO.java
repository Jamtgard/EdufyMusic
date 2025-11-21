package com.example.EdufyMusic.models.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

// ED-237-SJ
public class AlbumCreateDTO {

    @NotBlank private String title;

    @NotBlank private String url;

    @NotNull
    private LocalDate releaseDate;

    @Valid private List<SongCreateDTO> songs;

    @NotEmpty private List<Long> creatorIds;

    private boolean active;

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public LocalDate getReleaseDate() {return releaseDate;}
    public void setReleaseDate(LocalDate releaseDate) {this.releaseDate = releaseDate;}

    public List<SongCreateDTO> getSongs() {return songs;}
    public void setSongs(List<SongCreateDTO> songs) {this.songs = songs;}

    public List<Long> getCreatorIds() {return creatorIds;}
    public void setCreatorIds(List<Long> creatorIds) {this.creatorIds = creatorIds;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

    @Override
    public String toString() {
        return "AlbumCreateDTO{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", releaseDate=" + releaseDate +
                ", songs=" + songs +
                ", creatorIds=" + creatorIds +
                ", active=" + active +
                '}';
    }
}
