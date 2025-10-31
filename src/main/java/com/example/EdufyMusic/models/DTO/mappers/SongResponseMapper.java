package com.example.EdufyMusic.models.DTO.mappers;

import com.example.EdufyMusic.models.DTO.AlbumTrackInfoDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.models.entities.AlbumTrack;
import com.example.EdufyMusic.models.entities.Song;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// ED-74-SJ
public class SongResponseMapper {

    private SongResponseMapper() {}

    public static SongResponseDTO toDto(Song entity)
    {
        if (entity == null) return null;

        SongResponseDTO dto = new SongResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setUrl(entity.getUrl());
        dto.setLength(entity.getLength());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setTimesStreamed(entity.getNumberOfStreams());
        dto.setActive(entity.isActive());

        dto.setCreatorUsernames(Collections.emptyList()); // TODO: resolve via songCreatorIds
        dto.setGenreNames(Collections.emptyList());       // TODO: resolve via songGenreIds

        dto.setAlbumTracks(mapAlbumTracks(entity.getAlbumTracks()));

        return dto;
    }

    private static List<AlbumTrackInfoDTO> mapAlbumTracks(List<AlbumTrack> albumTracks) {

        if (albumTracks == null) return Collections.emptyList();

        return albumTracks.stream()
                .map(at -> new AlbumTrackInfoDTO(
                        at.getAlbum() != null ? at.getAlbum().getId() : null,
                        at.getAlbum() != null ? at.getAlbum().getTitle() : null,
                        at.getTrackIndex()
                ))
                .collect(Collectors.toList());
    }

}
