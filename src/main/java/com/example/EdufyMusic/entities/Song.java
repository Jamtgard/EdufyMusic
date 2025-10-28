package com.example.EdufyMusic.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// ED-110-SJ
@Entity
@Table(name = "song")
public class Song {

    // TODO - active valid code (remove comment-function)

// Attributes ----------------------------------------------------------------------------------------------------------

    // Identity-based attributes ---------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_id")
    private Long id;

    @Column(name = "song_title", nullable = false, length = 50)
    private String title;

    @Column(name = "song_url", nullable = false)
    private String url;

    @Column(name = "song_length", nullable = false)
    private LocalTime Length;

    @Column(name = "song_release_date", nullable = false)
    private LocalDate releaseDate;

    /*
    @ManyToMany
    @JoinColumn(name = "song_genres")
    private List<Genre> genres;

    @ManyToMany
    @JoinColumn(name = "song_creators", nullable = false)
    private List<Creator> creators = new ArrayList<Creator>();
     */

    // Functional Attributes -------------------------------------------------------------------------------------------

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlbumTrack> albumTracks = new ArrayList<>();

    @Column(name = "song_number_of_streams")
    private Long numberOfStreams;

    @Column(name = "song_active")
    private boolean active;

// Constructors --------------------------------------------------------------------------------------------------------

    public Song() {}

    public Song(
            String title,
            String url,
            LocalTime Length,
            LocalDate releaseDate,
            List<Genre> genres,
            List<Creator> creators,
            List<AlbumTrack> albumTracks,
            Long numberOfStreams,
            boolean active)
    {
        this.title = title;
        this.url = url;
        this.Length = Length;
        this.releaseDate = releaseDate;
        //this.genres = genres;
        //this.creators = creators;
        this.albumTracks = albumTracks;
        this.numberOfStreams = numberOfStreams;
        this.active = active;
    }

    public Song(
            Long id,
            String title,
            String url,
            LocalTime Length,
            LocalDate releaseDate,
            List<Genre> genres,
            List<Creator> creators,
            List<AlbumTrack> albumTracks,
            Long numberOfStreams,
            boolean active)
    {
        this.id = id;
        this.title = title;
        this.url = url;
        this.Length = Length;
        this.releaseDate = releaseDate;
        //this.genres = genres;
        //this.creators = creators;
        this.albumTracks = albumTracks;
        this.numberOfStreams = numberOfStreams;
        this.active = active;
    }

    public Song(Song song)
    {
        this.id = song.id;
        this.title = song.title;
        this.url = song.url;
        this.Length = song.Length;
        this.releaseDate = song.releaseDate;
        //this.genres = song.genres;
        //this.creators = song.creators;
        this.albumTracks = song.albumTracks;
        this.numberOfStreams = song.numberOfStreams;
        this.active = song.active;
    }

// Getters & Setters ---------------------------------------------------------------------------------------------------

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public LocalTime getLength() {return Length;}
    public void setLength(LocalTime length) {Length = length;}

    public LocalDate getReleaseDate() {return releaseDate;}
    public void setReleaseDate(LocalDate releaseDate) {this.releaseDate = releaseDate;}

    /*
    public List<Genre> getGenres() {return genres;}
    public void setGenres(List<Genre> genres) {this.genres = genres;}

    public List<Creator> getCreators() {return creators;}
    public void setCreators(List<Creator> creators) {this.creators = creators;}
     */

    public List<AlbumTrack> getAlbumTracks() {return albumTracks;}
    public void setAlbumTracks(List<AlbumTrack> albumTracks) {this.albumTracks = albumTracks;}

    public Long getNumberOfStreams() {return numberOfStreams;}
    public void setNumberOfStreams(Long numberOfStreams) {this.numberOfStreams = numberOfStreams;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

// toString ------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", Length=" + Length +
                ", releaseDate=" + releaseDate +
                //", genres=" + genres +
                //", creators=" + creators +
                ", albumTracks=" + albumTracks +
                ", numberOfStreams=" + numberOfStreams +
                ", active=" + active +
                '}';
    }
}
