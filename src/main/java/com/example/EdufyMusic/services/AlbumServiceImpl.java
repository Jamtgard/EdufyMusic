package com.example.EdufyMusic.services;

import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.converters.Roles;
import com.example.EdufyMusic.exceptions.BadRequestException;
import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.AlbumCreateDTO;
import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.SongCreateDTO;
import com.example.EdufyMusic.models.DTO.mappers.AlbumResponseMapper;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.enums.MediaType;
import com.example.EdufyMusic.repositories.AlbumRepository;
import com.example.EdufyMusic.utilities.MicroMethodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

        // TODO hämta creatorUsernames via albumCreatorIds

        return AlbumResponseMapper.toDtoWithId(album);
    }

    // ED-50-SJ
    // ED-80-SJ reworked structure with new way of authentication.
    @Override
    public List<AlbumResponseDTO> getAlbumsByTitle(String title, Authentication authentication) {

        List<Album> allAlbumsByTitle;
        List<String> roles = Roles.getRoles(authentication);

        if (roles.contains("music_admin") || roles.contains("edufy_realm_admin")) {
            allAlbumsByTitle = albumRepository.findByTitleContainingIgnoreCase(title);
            MicroMethodes.validateListNotEmpty(allAlbumsByTitle, "List of Albums by title");
            return AlbumResponseMapper.toDtoListWithId(allAlbumsByTitle);
        } else {
            allAlbumsByTitle = albumRepository.findByTitleContainingIgnoreCaseAndActiveIsTrue(title);
            MicroMethodes.validateListNotEmpty(allAlbumsByTitle, "List of Albums by title");
            return AlbumResponseMapper.toDtoListNoId(allAlbumsByTitle);
        }
    }

    // ED-81-SJ
    @Override
    public List<AlbumResponseDTO> getAllAlbums(Authentication authentication) {

        List<Album> allAlbums;
        List<String> roles = Roles.getRoles(authentication);

        if (roles.contains("music_admin") || roles.contains("edufy_realm_admin")) {
            allAlbums = albumRepository.findAll();
            MicroMethodes.validateListNotEmpty(allAlbums, "List of all Albums");
            return AlbumResponseMapper.toDtoListWithId(allAlbums);
        } else {
            allAlbums = albumRepository.findAllByActiveTrue();
            MicroMethodes.validateListNotEmpty(allAlbums, "List of all Albums");
            return AlbumResponseMapper.toDtoListNoId(allAlbums);
        }
    }

    // ED-237-SJ
    // * - Checks if call comes from SongServiceImpl. Both entity calls each other, this is to stop endless loop.
    // ** - Checks if album already exists, if so = no creation of new entity. Existing entity will be used.
    // *** - Call is not redirected from songServiceImpl - create all songs contained in AlbumCreateDTO dto.
    @Override
    @Transactional
    public AlbumResponseDTO createAlbum(AlbumCreateDTO dto, boolean redirected) {

        if (dto == null) throw new BadRequestException("Album", "body", "null");
        if (dto.getTitle() == null || dto.getTitle().isBlank()) throw new BadRequestException("Album", "title", String.valueOf(dto.getTitle()));
        if (dto.getUrl() == null || dto.getUrl().isBlank()) throw new BadRequestException("Album", "url", String.valueOf(dto.getUrl()));
        if (dto.getReleaseDate() == null) throw new BadRequestException("Album", "releaseDate", "null");
        if (dto.getCreatorIds() == null || dto.getCreatorIds().isEmpty()) throw new BadRequestException("Album", "creatorIds", String.valueOf(dto.getCreatorIds()));

        if (!redirected && (dto.getSongs() == null || dto.getSongs().isEmpty())) throw new BadRequestException("Album", "songs", "empty");

        // *
        if (redirected) {

            Optional<Album> existing = albumRepository
                    .findFirstByTitleIgnoreCaseAndReleaseDateAndUrl(
                            dto.getTitle(), dto.getReleaseDate(), dto.getUrl()
                    );

            // **
            if (existing.isPresent()) {
                return AlbumResponseMapper.toDtoWithId(existing.get());
            }

            Album album = new Album();
            album.setTitle(dto.getTitle());
            album.setUrl(dto.getUrl());
            album.setReleaseDate(dto.getReleaseDate());
            album.setActive(dto.isActive());
            album.setNumberOfStreams(0L);
            album.setLength(LocalTime.of(0,0,0));

            album = albumRepository.save(album);

            creatorClient.createRecordOfMusic(album.getId(), dto.getCreatorIds(), MediaType.ALBUM);

            return AlbumResponseMapper.toDtoWithId(album);
        }

        Album album = new Album();
        album.setTitle(dto.getTitle());
        album.setUrl(dto.getUrl());
        album.setReleaseDate(dto.getReleaseDate());
        album.setActive(dto.isActive());
        album.setNumberOfStreams(0L);
        album.setLength(LocalTime.of(0,0,0));

        album = albumRepository.save(album);

        // ***
        int index = 1;
        for (SongCreateDTO songDto : dto.getSongs()) {
            if (songDto.getCreatorIds() == null || songDto.getCreatorIds().isEmpty()) {
                songDto.setCreatorIds(dto.getCreatorIds());
            }

            songDto.setAlbumId(album.getId());
            songDto.setTrackIndex(index++);

            songService.createSong(songDto, true);
        }

        creatorClient.createRecordOfMusic(album.getId(), dto.getCreatorIds(), MediaType.ALBUM);

        return AlbumResponseMapper.toDtoWithId(album);
    }

    @Override
    public Album getAlbumEntityById(Long id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Album", "id", id));
    }
}
