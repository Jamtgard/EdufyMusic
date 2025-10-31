package com.example.EdufyMusic.models.DTO.mappers;

import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.AlbumTrackSongDTO;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.entities.AlbumTrack;
import com.example.EdufyMusic.models.entities.Song;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumResponseMapper {

    private AlbumResponseMapper() {}

    public static AlbumResponseDTO toDto(Album entity) {
        if (entity == null) {return null;}

        AlbumResponseDTO dto = new AlbumResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setUrl(entity.getUrl());
        dto.setLength(entity.getLength());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setTimesPlayed(entity.getNumberOfStreams());
        dto.setActive(entity.isActive());

        dto.setCreatorUsernames(Collections.emptyList()); // TODO albumCreatorIds
        dto.setGenreNames(Collections.emptyList());       // TODO albumGenreIds

        dto.setAlbumTracks(mapTracks(entity.getAlbumTracks()));

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
