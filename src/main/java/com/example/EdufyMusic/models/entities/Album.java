package com.example.EdufyMusic.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// ED-111-SJ
@Entity
@Table(name = "album")
public class Album {

// Attributes ----------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @Column(name = "album_title", nullable = false)
    private String title;

    @Column(name = "album_url", nullable = false)
    private String url;

    @Column(name = "album_length", nullable = false)
    private LocalTime length;

    @Column(name = "album_release_date")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("trackIndex asc")
    private List<AlbumTrack> albumTracks = new ArrayList<>();

    @Column(name = "album_number_of_streams")
    private Long numberOfStreams;

    @Column(name = "album_active")
    private boolean active;

// Constructors --------------------------------------------------------------------------------------------------------

    public Album() {}

    public Album(
            String title,
            String url,
            LocalTime length,
            LocalDate releaseDate,
            List<AlbumTrack> albumTracks,
            Long numberOfStreams,
            boolean active )
    {
        this.title = title;
        this.url = url;
        this.length = length;
        this.releaseDate = releaseDate;
        this.albumTracks = albumTracks;
        this.numberOfStreams = numberOfStreams;
        this.active = active;
    }

    public Album(
            Long id,
            String title,
            String url,
            LocalTime length,
            LocalDate releaseDate,
            List<AlbumTrack> albumTracks,
            Long numberOfStreams,
            boolean active )
    {
        this.id = id;
        this.title = title;
        this.url = url;
        this.length = length;
        this.releaseDate = releaseDate;
        this.albumTracks = albumTracks;
        this.numberOfStreams = numberOfStreams;
        this.active = active;
    }

    public Album(Album album)
    {
        this.title = album.title;
        this.url = album.url;
        this.length = album.length;
        this.releaseDate = album.releaseDate;
        this.albumTracks = album.albumTracks;
        this.numberOfStreams = album.numberOfStreams;
        this.active = album.active;
    }

// Getters & Setters ---------------------------------------------------------------------------------------------------

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

    public List<AlbumTrack> getAlbumTracks() {return albumTracks;}
    public void setAlbumTracks(List<AlbumTrack> albumTracks) {this.albumTracks = albumTracks;}

    public Long getNumberOfStreams() {return numberOfStreams;}
    public void setNumberOfStreams(Long numberOfStreams) {this.numberOfStreams = numberOfStreams;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

// toString ------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", releaseDate=" + releaseDate +
                ", albumTracks=" + albumTracks +
                ", numberOfStreams=" + numberOfStreams +
                ", active=" + active +
                '}';
    }
}
