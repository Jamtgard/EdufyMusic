package com.example.EdufyMusic.models.DTO.mappers;

import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.AlbumTrackSongDTO;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.entities.AlbumTrack;
import com.example.EdufyMusic.models.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumResponseMapper {

    // ED-275-SJ
    private static CreatorClient creatorClient;

    @Autowired
    public AlbumResponseMapper(CreatorClient creatorClient) {AlbumResponseMapper.creatorClient = creatorClient;}

    public static AlbumResponseDTO toDtoWithId(Album album) {
        if (album == null) {return null;}

        AlbumResponseDTO dto = new AlbumResponseDTO();
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setUrl(album.getUrl());
        dto.setLength(album.getLength());
        dto.setReleaseDate(album.getReleaseDate());
        dto.setTimesPlayed(album.getNumberOfStreams());
        dto.setActive(album.isActive());

        // ED-275-SJ
        dto.setCreators(creatorClient.getCreatorsByMedia("ALBUM", album.getId()));


        dto.setAlbumTracks(mapTracks(album.getAlbumTracks()));

        return dto;
    }

    // ED-80-SJ
    public static AlbumResponseDTO toDtoNoId(Album album) {
        if (album == null) {return null;}

        AlbumResponseDTO dto = new AlbumResponseDTO();
        dto.setTitle(album.getTitle());
        dto.setUrl(album.getUrl());
        dto.setLength(album.getLength());
        dto.setReleaseDate(album.getReleaseDate());
        dto.setTimesPlayed(album.getNumberOfStreams());

        // ED-275-SJ
        dto.setCreators(creatorClient.getCreatorsByMedia("ALBUM", album.getId()));

        dto.setAlbumTracks(mapTracks(album.getAlbumTracks()));

        return dto;
    }

    // ED-80-SJ
    public static List<AlbumResponseDTO> toDtoListWithId(List<Album> albums)
    {
        if (albums == null || albums.isEmpty()) {return Collections.emptyList();}
        return albums.stream()
                .map(AlbumResponseMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    // ED-80-SJ
    public static List<AlbumResponseDTO> toDtoListNoId(List<Album> albums)
    {
        if (albums == null || albums.isEmpty()) {return Collections.emptyList();}
        return albums.stream()
                .map(AlbumResponseMapper::toDtoNoId)
                .collect(Collectors.toList());
    }

    private static List<AlbumTrackSongDTO> mapTracks(List<AlbumTrack> tracks) {
        if (tracks == null) return Collections.emptyList();

        return tracks.stream()
                .map(t -> {
                    Song s = t.getSong();
                    return new AlbumTrackSongDTO(
                            t.getTrackIndex(),
                            s != null ? s.getId() : null,
                            s != null ? s.getTitle() : null,
                            s != null ? s.getLength() : null,
                            s != null ? s.getUrl() : null
                    );
                })
                .collect(Collectors.toList());
    }
}
