package com.example.EdufyMusic.models.DTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SongResponseDTO {

    private Long id;
    private String title;
    private List<String> creatorUsernames;
    private List<String> genreNames;
    private String url;
    private LocalTime length;
    private LocalDate releaseDate;
    private Long timesStreamed;
    private boolean active;

    private List<AlbumTrackInfoDTO> albumTracks;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public List<String> getCreatorUsernames() {return creatorUsernames;}
    public void setCreatorUsernames(List<String> creatorUsernames) {this.creatorUsernames = creatorUsernames;}

    public List<String> getGenreNames() {return genreNames;}
    public void setGenreNames(List<String> genreNames) {this.genreNames = genreNames;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public LocalTime getLength() {return length;}
    public void setLength(LocalTime length) {this.length = length;}

    public LocalDate getReleaseDate() {return releaseDate;}
    public void setReleaseDate(LocalDate releaseDate) {this.releaseDate = releaseDate;}

    public Long getTimesStreamed() {return timesStreamed;}
    public void setTimesStreamed(Long timesStreamed) {this.timesStreamed = timesStreamed;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

    public List<AlbumTrackInfoDTO> getAlbumTracks() {return albumTracks;}
    public void setAlbumTracks(List<AlbumTrackInfoDTO> albumTracks) {this.albumTracks = albumTracks;}

    @Override
    public String toString() {
        return "SongResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creatorUsernames=" + creatorUsernames +
                ", genreNames=" + genreNames +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", releaseDate=" + releaseDate +
                ", timesStreamed=" + timesStreamed +
                ", active=" + active +
                ", albumTracks=" + albumTracks +
                '}';
    }
}

