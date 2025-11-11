package com.example.EdufyMusic.models.DTO.mappers;

import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.AlbumTrackSongDTO;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.entities.AlbumTrack;
import com.example.EdufyMusic.models.entities.Song;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumResponseMapper {

    public static AlbumResponseDTO toDto(Album album) {
        if (album == null) {return null;}

        AlbumResponseDTO dto = new AlbumResponseDTO();
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setUrl(album.getUrl());
        dto.setLength(album.getLength());
        dto.setReleaseDate(album.getReleaseDate());
        dto.setTimesPlayed(album.getNumberOfStreams());
        dto.setActive(album.isActive());

        dto.setCreatorUsernames(Collections.emptyList()); // TODO albumCreatorIds

        dto.setAlbumTracks(mapTracks(album.getAlbumTracks()));

        return dto;
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
