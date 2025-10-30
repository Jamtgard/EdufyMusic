package com.example.EdufyMusic.models.entities;

import jakarta.persistence.*;

// ED-110-SJ
@Entity
@Table(name = "album_tracks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"album_id","song_id"}),
                @UniqueConstraint(columnNames = {"album_id","track_index"})
        })
public class AlbumTrack {

// Attributes ----------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "album_track_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(name = "track_index", nullable = false)
    private Integer trackIndex;

// Constructor ---------------------------------------------------------------------------------------------------------

    public AlbumTrack() {}

    public AlbumTrack(Album album, Song song, Integer trackIndex) {
        this.album = album;
        this.song = song;
        this.trackIndex = trackIndex;
    }

    public AlbumTrack(Long id, Album album, Song song, Integer trackIndex) {
        this.id = id;
        this.album = album;
        this.song = song;
        this.trackIndex = trackIndex;
    }

    public AlbumTrack(AlbumTrack albumTrack) {
        this.id = albumTrack.id;
        this.album = albumTrack.album;
        this.song = albumTrack.song;
        this.trackIndex = albumTrack.trackIndex;
    }

// Getters & Setters ---------------------------------------------------------------------------------------------------

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Album getAlbum() {return album;}
    public void setAlbum(Album album) {this.album = album;}

    public Song getSong() {return song;}
    public void setSong(Song song) {this.song = song;}

    public Integer getTrackIndex() {return trackIndex;}
    public void setTrackIndex(Integer trackIndex) {this.trackIndex = trackIndex;}

// toString ------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "AlbumTrack{" +
                "id=" + id +
                ", album=" + album +
                ", song=" + song +
                ", trackIndex=" + trackIndex +
                '}';
    }
}
