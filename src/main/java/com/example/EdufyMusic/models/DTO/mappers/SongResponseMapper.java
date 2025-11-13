package com.example.EdufyMusic.models.DTO.mappers;

import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.clients.GenreClient;
import com.example.EdufyMusic.models.DTO.AlbumTrackInfoDTO;
import com.example.EdufyMusic.models.DTO.CreatorDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.models.entities.AlbumTrack;
import com.example.EdufyMusic.models.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// ED-74-SJ
@Component
public class SongResponseMapper {

    // ED-266-SJ
    private static GenreClient genreClient;
    // ED-275-SJ
    private static CreatorClient creatorClient;

    // ED-266-SJ
    @Autowired
    private SongResponseMapper(GenreClient genreClient, CreatorClient creatorClient)
    {
        SongResponseMapper.genreClient = genreClient;
        SongResponseMapper.creatorClient = creatorClient;
    }

    public static SongResponseDTO toDtoWithId(Song song)
    {
        if (song == null) return null;

        SongResponseDTO dto = new SongResponseDTO();
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setUrl(song.getUrl());
        dto.setLength(song.getLength());
        dto.setReleaseDate(song.getReleaseDate());
        dto.setTimesStreamed(song.getNumberOfStreams());
        dto.setActive(song.isActive());

        // ED-266-SJ
        dto.setGenres(genreClient.getGenresByMedia("SONG", song.getId()));
        // ED-275-SJ
        dto.setCreators(creatorClient.getCreatorsByMedia("SONG", song.getId()));

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
        dto.setTimesStreamed(song.getNumberOfStreams());

        // ED-266-SJ
        dto.setGenres(genreClient.getGenresByMedia("SONG", song.getId()));

        // ED-275-SJ
        //dto.setCreators(creatorClient.getCreatorsByMedia("SONG", song.getId()));

        List<CreatorDTO> creators = creatorClient.getCreatorsByMedia("SONG", song.getId());
        if (creators != null){
            dto.setCreators(
                    creators.stream()
                            .map(c -> {
                                CreatorDTO creatorDTO = new CreatorDTO();
                                creatorDTO.setUsername(c.getUsername());
                                return creatorDTO;
                            })
                    .collect(Collectors.toList())
            );
        }


        dto.setAlbumTracks(mapAlbumTracksWithId(song.getAlbumTracks()));

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
                        at.getAlbum() != null ? at.getAlbum().getId() : null,
                        at.getAlbum() != null ? at.getAlbum().getTitle() : null,
                        at.getTrackIndex()
                ))
                .collect(Collectors.toList());
    }

}
