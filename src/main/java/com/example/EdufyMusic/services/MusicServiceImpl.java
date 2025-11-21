package com.example.EdufyMusic.services;

import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.converters.Roles;
import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.MusicResponseDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.models.DTO.mappers.AlbumResponseMapper;
import com.example.EdufyMusic.models.DTO.mappers.SongResponseMapper;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.models.enums.MediaType;
import com.example.EdufyMusic.repositories.AlbumRepository;
import com.example.EdufyMusic.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

// ED-51-SJ
@Service
public class MusicServiceImpl implements MusicService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final CreatorClient creatorClient;

    @Autowired
    public MusicServiceImpl(AlbumRepository albumRepository, SongRepository songRepository, CreatorClient creatorClient) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.creatorClient = creatorClient;
    }

    // ED-51-SJ
    @Override
    public MusicResponseDTO getDiscography(Long creatorId, Authentication authentication) {

        List<String> roles = Roles.getRoles(authentication);
        boolean isAdmin = roles.contains("music_admin") || roles.contains("edufy_realm_admin");

        List<Long> songIds = creatorClient.getMusicByCreator(creatorId, MediaType.SONG);
        List<Long> albumIds = creatorClient.getMusicByCreator(creatorId, MediaType.ALBUM);

        List<Song> songs = songRepository.findAllById(songIds);
        List<Album> albums = albumRepository.findAllById(albumIds);

        if (!isAdmin) {
            songs = songs.stream().filter(Song::isActive).toList();
            albums = albums.stream().filter(Album::isActive).toList();
        }

        Map<Long, Song> songMap = songs.stream()
                .collect(Collectors.toMap(Song::getId, Function.identity()));
        Map<Long, Album> albumMap = albums.stream()
                .collect(Collectors.toMap(Album::getId, Function.identity()));

        List<SongResponseDTO> songDTOs = songIds.stream()
                .map(songMap::get)
                .filter(Objects::nonNull)
                .map(song -> isAdmin
                        ? SongResponseMapper.toDtoWithId(song)
                        : SongResponseMapper.toDtoNoId(song))
                .toList();

        List<AlbumResponseDTO> albumDTOs = albumIds.stream()
                .map(albumMap::get)
                .filter(Objects::nonNull)
                .map(album -> isAdmin
                        ? AlbumResponseMapper.toDtoWithId(album)
                        : AlbumResponseMapper.toDtoNoId(album))
                .toList();

        return new MusicResponseDTO(albumDTOs, songDTOs);
    }
}
