package com.example.EdufyMusic.models.DTO;

import com.example.EdufyMusic.models.DTO.responses.CreatorDTO;
import com.example.EdufyMusic.models.DTO.responses.GenreDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// ED-74-SJ
@JsonInclude(JsonInclude.Include.NON_NULL) // ED-275-SJ
public class SongResponseDTO {

    private Long id;
    private String title;
    private String url;
    private LocalTime length;
    private LocalDate releaseDate;
    private boolean active;

    // ED-266-SJ
    private List<GenreDTO> genres;
    // ED-275-SJ
    private List<CreatorDTO> creators;

    private List<AlbumTrackInfoDTO> albumTracks;


    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public LocalTime getLength() {return length;}
    public void setLength(LocalTime length) {this.length = length;}

    public LocalDate getReleaseDate() {return releaseDate;}
    public void setReleaseDate(LocalDate releaseDate) {this.releaseDate = releaseDate;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

    public List<GenreDTO> getGenres() {return genres;}
    public void setGenres(List<GenreDTO> genres) {this.genres = genres;}

    public List<CreatorDTO> getCreators() {return creators;}
    public void setCreators(List<CreatorDTO> creators) {this.creators = creators;}

    public List<AlbumTrackInfoDTO> getAlbumTracks() {return albumTracks;}
    public void setAlbumTracks(List<AlbumTrackInfoDTO> albumTracks) {this.albumTracks = albumTracks;}

    @Override
    public String toString() {
        return "SongResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", releaseDate=" + releaseDate +
                ", active=" + active +
                ", genres=" + genres +
                ", creators=" + creators +
                ", albumTracks=" + albumTracks +
                '}';
    }
}

