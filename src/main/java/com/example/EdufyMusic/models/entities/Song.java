package com.example.EdufyMusic.models.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlbumTrack> albumTracks = new ArrayList<>();

    // ED-281-SJ
    @ElementCollection
    @CollectionTable(name = "song_user_history", joinColumns = @JoinColumn(name = "song_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "times_played")
    private Map<Long, Long> userHistory = new HashMap<>();

    @Column(name = "song_active")
    private boolean active;

// Constructors --------------------------------------------------------------------------------------------------------

    public Song() {}

    public Song(
            String title,
            String url,
            LocalTime Length,
            LocalDate releaseDate,
            List<AlbumTrack> albumTracks,
            Map<Long, Long> userHistory,
            boolean active )
    {
        this.title = title;
        this.url = url;
        this.Length = Length;
        this.releaseDate = releaseDate;
        this.albumTracks = albumTracks;
        this.userHistory = userHistory;
        this.active = active;
    }

    public Song(
            Long id,
            String title,
            String url,
            LocalTime Length,
            LocalDate releaseDate,
            List<AlbumTrack> albumTracks,
            Map<Long, Long> userHistory,
            boolean active)
    {
        this.id = id;
        this.title = title;
        this.url = url;
        this.Length = Length;
        this.releaseDate = releaseDate;
        this.albumTracks = albumTracks;
        this.userHistory = userHistory;
        this.active = active;
    }

    public Song(Song song)
    {
        this.id = song.id;
        this.title = song.title;
        this.url = song.url;
        this.Length = song.Length;
        this.releaseDate = song.releaseDate;
        this.albumTracks = song.albumTracks;
        this.userHistory = song.userHistory;
        // TODO get copy of songs numberOfStreams/timesPlayed
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

    public List<AlbumTrack> getAlbumTracks() {return albumTracks;}
    public void setAlbumTracks(List<AlbumTrack> albumTracks) {this.albumTracks = albumTracks;}

    public Map<Long, Long> getUserHistory() {return userHistory;}
    public void setUserHistory(Map<Long, Long> userHistory) {this.userHistory = userHistory;}

    public Long getTimesPlayed() {return userHistory.values().stream().mapToLong(Long::longValue).sum();}

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
                ", albumTracks=" + albumTracks +
                ", timesPlayed=" + getTimesPlayed() +
                ", active=" + active +
                '}';
    }
}
