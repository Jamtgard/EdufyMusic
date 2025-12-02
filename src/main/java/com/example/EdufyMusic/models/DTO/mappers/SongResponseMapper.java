package com.example.EdufyMusic.models.DTO.mappers;

import com.example.EdufyMusic.models.DTO.AlbumTrackInfoDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.models.entities.AlbumTrack;
import com.example.EdufyMusic.models.entities.Song;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// ED-74-SJ
@Component
public class SongResponseMapper {

    public static SongResponseDTO toDtoWithId(Song song)
    {
        if (song == null) return null;

        SongResponseDTO dto = new SongResponseDTO();
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setUrl(song.getUrl());
        dto.setLength(song.getLength());
        dto.setReleaseDate(song.getReleaseDate());
        dto.setActive(song.isActive());

        // ED-266-SJ // ED-275-SJ
        dto.setGenres(GenreResponseMapper.getSongGenresForAdmin(song));
        // ED-275-SJ
        dto.setCreators(CreatorResponseMapper.getSongCreatorsForAdmin(song));

        dto.setAlbumTracks(mapAlbumTracksWithId(song.getAlbumTracks()));

        return dto;
    }

    // ED-80-SJ
    public static SongResponseDTO toDtoNoId(Song song)
    {
     if (song == null) {return null;}

        SongResponseDTO dto = new SongResponseDTO();
        dto.setTitle(song.getTitle());
        dto.setUrl(song.getUrl());
        dto.setLength(song.getLength());
        dto.setReleaseDate(song.getReleaseDate());

        // ED-266-SJ // ED-275-SJ
        dto.setGenres(GenreResponseMapper.getSongGenresForUser(song));
        // ED-275-SJ
        dto.setCreators(CreatorResponseMapper.getSongCreatorForUser(song));

        dto.setAlbumTracks(mapAlbumTracksNoId(song.getAlbumTracks()));

        return dto;
    }

    // ED-80-SJ
    public static List<SongResponseDTO> toDtoListWithId(List<Song> songs)
    {
        if (songs == null || songs.isEmpty()) {return Collections.emptyList();}
        return songs.stream()
                .map(SongResponseMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    // ED-80-SJ
    public static List<SongResponseDTO> toDtoListNoId(List<Song> songs)
    {
        if (songs == null || songs.isEmpty()) {return Collections.emptyList();}
        return songs.stream()
                .map(SongResponseMapper::toDtoNoId)
                .collect(Collectors.toList());
    }

    // ED-281-SJ
    public static SongResponseDTO toDtoClientOnlyId(Long songId){
        SongResponseDTO dto = new SongResponseDTO();
        dto.setId(songId);
        return dto;
    }

    private static List<AlbumTrackInfoDTO> mapAlbumTracksWithId(List<AlbumTrack> albumTracks) {

        if (albumTracks == null) return Collections.emptyList();

        return albumTracks.stream()
                .map(at -> new AlbumTrackInfoDTO(
                        at.getAlbum() != null ? at.getAlbum().getId() : null,
                        at.getAlbum() != null ? at.getAlbum().getTitle() : null,
                        at.getTrackIndex()
                ))
                .collect(Collectors.toList());
    }

    private static List<AlbumTrackInfoDTO> mapAlbumTracksNoId(List<AlbumTrack> albumTracks) {
        if (albumTracks == null) return Collections.emptyList();

        return albumTracks.stream()
                .map(at -> new AlbumTrackInfoDTO(
                        null,
                        at.getAlbum() != null ? at.getAlbum().getTitle() : null,
                        at.getTrackIndex()
                ))
                .collect(Collectors.toList());
    }

}
