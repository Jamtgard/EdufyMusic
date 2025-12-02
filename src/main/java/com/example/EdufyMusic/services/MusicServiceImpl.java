package com.example.EdufyMusic.services;

import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.clients.GenreClient;
import com.example.EdufyMusic.clients.ThumbClient;
import com.example.EdufyMusic.converters.Roles;
import com.example.EdufyMusic.exceptions.BadRequestException;
import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.*;
import com.example.EdufyMusic.models.DTO.creation.AlbumCreateDTO;
import com.example.EdufyMusic.models.DTO.creation.SongCreateDTO;
import com.example.EdufyMusic.models.DTO.mappers.AlbumResponseMapper;
import com.example.EdufyMusic.models.DTO.mappers.SongResponseMapper;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.entities.AlbumTrack;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.models.enums.MediaType;
import com.example.EdufyMusic.models.records.AlbumResolution;
import com.example.EdufyMusic.models.records.MusicRecord;
import com.example.EdufyMusic.repositories.AlbumRepository;
import com.example.EdufyMusic.repositories.AlbumTrackRepository;
import com.example.EdufyMusic.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

// ED-51-SJ
@Service
public class MusicServiceImpl implements MusicService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final AlbumTrackRepository albumTrackRepository;

    // ED-309-SJ
    private final CreatorClient creatorClient;
    private final ThumbClient thumbClient;
    private final GenreClient genreClient;

    @Autowired
    public MusicServiceImpl(
            AlbumRepository albumRepository,
            SongRepository songRepository,
            AlbumTrackRepository albumTrackRepository,
            CreatorClient creatorClient,
            ThumbClient thumbClient,
            GenreClient genreClient
    ) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.albumTrackRepository = albumTrackRepository;
        this.creatorClient = creatorClient;
        this.thumbClient = thumbClient;
        this.genreClient = genreClient;

    }

    // ED-51-SJ
    @Override
    public MusicRecord getDiscography(Long creatorId, Authentication authentication) {

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

        return new MusicRecord(albumDTOs, songDTOs);
    }

    // ED-309-SJ
    // * Sets up required Album
    // ** Sets up required AlbumTrack index.
    // *** Creates Song, sets AlbumTrack index, sets Album length.
    // **** Calls related microservices
    // ***** Calls related Microservice for Album if Album was created inline (*) using addSong.
    @Override
    @Transactional
    public SongResponseDTO addSong(SongCreateDTO dto) {
        validateSongCreate(dto);

        // *
        AlbumResolution albumResolution = resolveAlbumForSong(dto);
        Album album = albumResolution.album();

        // **
        Integer trackIndex = dto.getTrackIndex();
        if (trackIndex == null || albumTrackRepository.existsByAlbum_IdAndTrackIndex(album.getId(), trackIndex)) {
            trackIndex = nextTrackIndex(album);
        }


        // ***
        Song song = new Song();
        song.setTitle(dto.getTitle());
        song.setUrl(dto.getUrl());
        song.setLength(dto.getLength());
        song.setReleaseDate(dto.getReleaseDate());
        song.setActive(dto.isActive());

        song = songRepository.save(song);

        AlbumTrack track = new AlbumTrack(album, song, trackIndex);
        albumTrackRepository.save(track);

        if (!album.getAlbumTracks().contains(track)) album.getAlbumTracks().add(track);
        if (!song.getAlbumTracks().contains(track)) song.getAlbumTracks().add(track);

        album.setLength(calculateAlbumLength(album));
        albumRepository.save(album);

        // ****
        thumbClient.createRecordOfSong(song.getId(), song.getTitle());
        genreClient.createRecordOfSong(song.getId(), dto.getGenreIds());
        creatorClient.createRecordOfMusic(song.getId(), dto.getCreatorIds(), MediaType.SONG);

        // *****
        if (albumResolution.createdNewAlbum()) {
            creatorClient.createRecordOfMusic(album.getId(), albumResolution.albumCreatorIds(), MediaType.ALBUM);
        }

        return SongResponseMapper.toDtoWithId(song);
    }

    // ED-309-SJ
    private void validateSongCreate(SongCreateDTO dto) {
        if (dto == null) throw new BadRequestException("Song", "body", "null");
        if (dto.getTitle() == null || dto.getTitle().isBlank()) throw new BadRequestException("Song", "title", String.valueOf(dto.getTitle()));
        if (dto.getUrl() == null || dto.getUrl().isBlank()) throw new BadRequestException("Song", "url", String.valueOf(dto.getUrl()));
        if (dto.getLength() == null) throw new BadRequestException("Song", "length", "null");
        if (dto.getReleaseDate() == null) throw new BadRequestException("Song", "releaseDate", "null");
        if (dto.getGenreIds() == null || dto.getGenreIds().isEmpty()) throw new BadRequestException("Song", "genreIds", String.valueOf(dto.getGenreIds()));
        if (dto.getCreatorIds() == null || dto.getCreatorIds().isEmpty()) throw new BadRequestException("Song", "creatorIds", String.valueOf(dto.getCreatorIds()));
    }

    // ED-309-SJ
    // * Create & save Album
    // ** Creates Songs based on available SongDTOs in body.
    // ** - * - connect Songs to Album through AlbumTrack index.
    // ** - ** - Calls related microservices for Song.
    // *** Sets Album length based on all Songs available in Album.
    // **** Calls related Microservice for Album.
    @Override
    @Transactional
    public AlbumResponseDTO addAlbum(AlbumCreateDTO dto) {
        validateAlbumCreate(dto);

        // *
        Album album = new Album();
        album.setTitle(dto.getTitle());
        album.setUrl(dto.getUrl());
        album.setReleaseDate(dto.getReleaseDate());
        album.setActive(dto.isActive());
        album.setNumberOfStreams(0L);
        album.setLength(LocalTime.of(0, 0, 0));

        album = albumRepository.save(album);

        int index = 1;
        for (SongCreateDTO songDto : dto.getSongs()) {
            validateSongCreate(songDto);

            // ** - *
            Integer trackIndex = songDto.getTrackIndex();
            if (trackIndex == null) trackIndex = index;
            if (albumTrackRepository.existsByAlbum_IdAndTrackIndex(album.getId(), trackIndex)) {
                trackIndex = nextTrackIndex(album);
            }

            Song song = new Song();
            song.setTitle(songDto.getTitle());
            song.setUrl(songDto.getUrl());
            song.setLength(songDto.getLength());
            song.setReleaseDate(songDto.getReleaseDate());
            song.setActive(songDto.isActive());

            song = songRepository.save(song);

            AlbumTrack track = new AlbumTrack(album, song, trackIndex);
            albumTrackRepository.save(track);

            if (!album.getAlbumTracks().contains(track)) album.getAlbumTracks().add(track);
            if (!song.getAlbumTracks().contains(track)) song.getAlbumTracks().add(track);

            albumTrackRepository.save(track);

            // ** - **
            thumbClient.createRecordOfSong(song.getId(), song.getTitle());
            genreClient.createRecordOfSong(song.getId(), songDto.getGenreIds());
            creatorClient.createRecordOfMusic(song.getId(), songDto.getCreatorIds(), MediaType.SONG);

            index++;
        }

        album.setLength(calculateAlbumLength(album));
        albumRepository.save(album);

        creatorClient.createRecordOfMusic(album.getId(), dto.getCreatorIds(), MediaType.ALBUM);

        return AlbumResponseMapper.toDtoWithId(album);
    }

    // ED-309-SJ
    // * Checks if Album already exists.
    // ** Album is needed if previous check (*) = false.
    // *** Validates body (No Song needed)
    private AlbumResolution resolveAlbumForSong(SongCreateDTO dto) {

        // *
        if (dto.getAlbumId() != null) {
            Album album = albumRepository.findById(dto.getAlbumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album", "id", dto.getAlbumId()));
            return AlbumResolution.existing(album);
        }

        // **
        AlbumCreateDTO albumDto = dto.getAlbum();
        if (albumDto == null) throw new BadRequestException("Song", "album", "null");

        // ***
        validateAlbumForInlineCreation(albumDto);

        Optional<Album> existing = albumRepository
                .findFirstByTitleIgnoreCaseAndReleaseDateAndUrl(
                        albumDto.getTitle(), albumDto.getReleaseDate(), albumDto.getUrl()
                );

        if (existing.isPresent()) {
            return AlbumResolution.existing(existing.get());
        }

        Album album = new Album();
        album.setTitle(albumDto.getTitle());
        album.setUrl(albumDto.getUrl());
        album.setReleaseDate(albumDto.getReleaseDate());
        album.setActive(albumDto.isActive());
        album.setNumberOfStreams(0L);
        album.setLength(LocalTime.of(0, 0, 0));
        album = albumRepository.save(album);

        return AlbumResolution.created(album, albumDto.getCreatorIds());
    }


    // ED-309-SJ
    private void validateAlbumCreate(AlbumCreateDTO dto) {
        if (dto == null) throw new BadRequestException("Album", "body", "null");
        if (dto.getTitle() == null || dto.getTitle().isBlank()) throw new BadRequestException("Album", "title", String.valueOf(dto.getTitle()));
        if (dto.getUrl() == null || dto.getUrl().isBlank()) throw new BadRequestException("Album", "url", String.valueOf(dto.getUrl()));
        if (dto.getReleaseDate() == null) throw new BadRequestException("Album", "releaseDate", "null");
        if (dto.getCreatorIds() == null || dto.getCreatorIds().isEmpty()) throw new BadRequestException("Album", "creatorIds", String.valueOf(dto.getCreatorIds()));
        if (dto.getSongs() == null || dto.getSongs().isEmpty()) throw new BadRequestException("Album", "songs", "empty");
    }

    // ED-309-SJ
    private void validateAlbumForInlineCreation(AlbumCreateDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) throw new BadRequestException("Album", "title", String.valueOf(dto.getTitle()));
        if (dto.getUrl() == null || dto.getUrl().isBlank()) throw new BadRequestException("Album", "url", String.valueOf(dto.getUrl()));
        if (dto.getReleaseDate() == null) throw new BadRequestException("Album", "releaseDate", "null");
        if (dto.getCreatorIds() == null || dto.getCreatorIds().isEmpty()) throw new BadRequestException("Album", "creatorIds", String.valueOf(dto.getCreatorIds()));
    }

    // ED-309-SJ
    private Integer nextTrackIndex(Album album) {
        return album.getAlbumTracks().stream()
                .map(AlbumTrack::getTrackIndex)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }

    // ED-309-SJ
    private LocalTime calculateAlbumLength(Album album) {
        long seconds = album.getAlbumTracks().stream()
                .map(AlbumTrack::getSong)
                .filter(Objects::nonNull)
                .map(Song::getLength)
                .filter(Objects::nonNull)
                .mapToLong(LocalTime::toSecondOfDay)
                .sum();

        return LocalTime.ofSecondOfDay(seconds % (24 * 3600));
    }
}
