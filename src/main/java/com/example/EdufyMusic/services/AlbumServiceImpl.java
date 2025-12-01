package com.example.EdufyMusic.services;

import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.converters.Roles;
import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.mappers.AlbumResponseMapper;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.repositories.AlbumRepository;
import com.example.EdufyMusic.utilities.MicroMethodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

// ED-39-SJ
@Service
public class AlbumServiceImpl implements AlbumService {

    // ED-75-SJ
    private final AlbumRepository albumRepository;
    //ED-237-SJ
    private final SongService songService;
    private final CreatorClient creatorClient;
    private final AlbumResponseMapper albumResponseMapper;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository,
                            @Lazy SongService songService,
                            CreatorClient creatorClient, AlbumResponseMapper albumResponseMapper)
    {
        this.albumRepository = albumRepository;
        this.songService = songService;
        this.creatorClient = creatorClient;
        this.albumResponseMapper = albumResponseMapper;
    }

    @Override
    public AlbumResponseDTO getAlbumById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Album", "id", id)
        );
        return AlbumResponseMapper.toDtoWithId(album);
    }

    // ED-50-SJ
    // ED-80-SJ reworked structure with new way of authentication.
    @Override
    public List<AlbumResponseDTO> getAlbumsByTitle(String title, Authentication authentication) {

        List<String> roles = Roles.getRoles(authentication);
        boolean isAdmin = roles.contains("music_admin") || roles.contains("edufy_realm_admin");

        List<Album> albums = isAdmin
                ? albumRepository.findByTitleContainingIgnoreCase(title)
                : albumRepository.findByTitleContainingIgnoreCaseAndActiveIsTrue(title);

        MicroMethodes.validateListNotEmpty(albums, "List of Albums by title");

        return isAdmin
                ? AlbumResponseMapper.toDtoListWithId(albums)
                : AlbumResponseMapper.toDtoListNoId(albums);
    }

    // ED-81-SJ
    @Override
    public List<AlbumResponseDTO> getAllAlbums(Authentication authentication) {

        List<String> roles = Roles.getRoles(authentication);
        boolean isAdmin = roles.contains("music_admin") || roles.contains("edufy_realm_admin");

        List<Album> albums = isAdmin
                ? albumRepository.findAll()
                : albumRepository.findAllByActiveTrue();

        MicroMethodes.validateListNotEmpty(albums, "List of all Albums");

        return isAdmin
                ? AlbumResponseMapper.toDtoListWithId(albums)
                : AlbumResponseMapper.toDtoListNoId(albums);
    }
}
