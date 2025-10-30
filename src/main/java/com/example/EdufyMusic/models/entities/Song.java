package com.example.EdufyMusic.models.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// ED-110-SJ
@Entity
@Table(name = "song")
public class Song {

// Attributes ----------------------------------------------------------------------------------------------------------

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

    // ED-114-SJ
    @ElementCollection
    @CollectionTable(
            name = "song_genres",
            joinColumns = @JoinColumn(name = "song_id"))
    @Column(name = "genre_id", nullable = false)
    private List<Long> songGenreIds = new ArrayList<>();

    // ED-113-SJ
    @ElementCollection
    @CollectionTable(
            name = "song_creators",
            joinColumns = @JoinColumn(name = "song_id"))
    @Column(name = "creator_id", nullable = false)
    private List<Long> songCreatorIds = new ArrayList<>();

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
            List<Long> songGenreIds,
            List<Long> songCreatorIds,
            List<AlbumTrack> albumTracks,
            Long numberOfStreams,
            boolean active )
    {
        this.title = title;
        this.url = url;
        this.Length = Length;
        this.releaseDate = releaseDate;
        this.songCreatorIds = songCreatorIds;
        this.songGenreIds = songGenreIds;;
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
            List<Long> songCreatorIds,
            List<Long> songGenreIds,
            List<AlbumTrack> albumTracks,
            Long numberOfStreams,
            boolean active)
    {
        this.id = id;
        this.title = title;
        this.url = url;
        this.Length = Length;
        this.releaseDate = releaseDate;
        this.songCreatorIds = songCreatorIds;
        this.songGenreIds = songGenreIds;
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
        this.songCreatorIds = song.songCreatorIds;
        this.songGenreIds = song.songGenreIds;
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

    public List<Long> getSongGenreIds() {return songGenreIds;}
    public void setSongGenreIds(List<Long> songGenreIds) {this.songGenreIds = songGenreIds;}

    public List<Long> getSongCreatorIds() {return songCreatorIds;}
    public void setSongCreatorIds(List<Long> songCreatorIds) {this.songCreatorIds = songCreatorIds;}

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
                ", songGenreIds=" + songGenreIds +
                ", songCreatorIds=" + songCreatorIds +
                ", albumTracks=" + albumTracks +
                ", numberOfStreams=" + numberOfStreams +
                ", active=" + active +
                '}';
    }
}
