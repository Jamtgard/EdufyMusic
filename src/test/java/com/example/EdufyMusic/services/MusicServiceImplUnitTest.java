package com.example.EdufyMusic.services;


import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.clients.GenreClient;
import com.example.EdufyMusic.clients.ThumbClient;
import com.example.EdufyMusic.converters.Roles;
import com.example.EdufyMusic.exceptions.BadRequestException;
import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.creation.AlbumCreateDTO;
import com.example.EdufyMusic.models.records.MusicRecord;
import com.example.EdufyMusic.models.DTO.creation.SongCreateDTO;
import com.example.EdufyMusic.models.DTO.mappers.CreatorResponseMapper;
import com.example.EdufyMusic.models.DTO.mappers.GenreResponseMapper;
import com.example.EdufyMusic.models.DTO.responses.CreatorDTO;
import com.example.EdufyMusic.models.DTO.responses.GenreDTO;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.entities.AlbumTrack;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.models.enums.MediaType;
import com.example.EdufyMusic.repositories.AlbumRepository;
import com.example.EdufyMusic.repositories.AlbumTrackRepository;
import com.example.EdufyMusic.repositories.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// ED-342-SJ
@ExtendWith(MockitoExtension.class)
class MusicServiceImplUnitTest {

    @Mock AlbumRepository albumRepository;
    @Mock SongRepository songRepository;
    @Mock AlbumTrackRepository albumTrackRepository;

    @Mock CreatorClient creatorClient;
    @Mock ThumbClient thumbClient;
    @Mock GenreClient genreClient;

    @Mock Authentication authentication;

    @InjectMocks MusicServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new MusicServiceImpl(
                albumRepository,
                songRepository,
                albumTrackRepository,
                creatorClient,
                thumbClient,
                genreClient
        );

        new GenreResponseMapper(genreClient);
        new CreatorResponseMapper(creatorClient);

        lenient().when(genreClient.getGenresByMedia(any(MediaType.class), anyLong())).thenReturn(List.of(dummyGenreDto()));
        lenient().when(creatorClient.getCreatorsByMedia(any(MediaType.class), anyLong())).thenReturn(List.of(dummyCreatorDto()));
    }

    // getDiscography --------------------------------------------------------------------------------------------------

    @Test
    void getDiscography_WhenUserIsAdmin_ShouldReturnActiveAndInactiveAndDTOsWithIds() {
        try (MockedStatic<Roles> roles = mockStatic(Roles.class)) {
            roles.when(() -> Roles.getRoles(authentication)).thenReturn(List.of("music_admin"));

            Long creatorId = 10L;

            List<Long> songIds = List.of(2L, 1L);
            List<Long> albumIds = List.of(8L, 9L);

            when(creatorClient.getMusicByCreator(creatorId, MediaType.SONG)).thenReturn(songIds);
            when(creatorClient.getMusicByCreator(creatorId, MediaType.ALBUM)).thenReturn(albumIds);

            Song song1 = makeSong(1L, true);
            Song song2 = makeSong(2L, false);
            when(songRepository.findAllById(songIds)).thenReturn(List.of(song1, song2));

            Album album8 = makeAlbum(8L, true);
            Album album9 = makeAlbum(9L, false);
            when(albumRepository.findAllById(albumIds)).thenReturn(List.of(album8, album9));

            MusicRecord dto = service.getDiscography(creatorId, authentication);

            assertNotNull(dto);
            assertNotNull(dto.songs());
            assertNotNull(dto.albums());
            assertEquals(2, dto.songs().size());
            assertEquals(2, dto.albums().size());

            verify(creatorClient).getMusicByCreator(creatorId, MediaType.SONG);
            verify(creatorClient).getMusicByCreator(creatorId, MediaType.ALBUM);
        }
    }

    @Test
    void getDiscography_WhenUserIsNotAdmin_ShouldFilterOutInactiveAndReturnDTOsWithoutIds() {
        try (MockedStatic<Roles> roles = mockStatic(Roles.class)) {
            roles.when(() -> Roles.getRoles(authentication)).thenReturn(List.of("user"));

            Long creatorId = 10L;

            List<Long> songIds = List.of(1L, 2L, 999L);
            List<Long> albumIds = List.of(8L, 9L);

            when(creatorClient.getMusicByCreator(creatorId, MediaType.SONG)).thenReturn(songIds);
            when(creatorClient.getMusicByCreator(creatorId, MediaType.ALBUM)).thenReturn(albumIds);

            Song activeSong = makeSong(1L, true);
            Song inactiveSong = makeSong(2L, false);
            when(songRepository.findAllById(songIds)).thenReturn(List.of(activeSong, inactiveSong));

            Album activeAlbum = makeAlbum(8L, true);
            Album inactiveAlbum = makeAlbum(9L, false);
            when(albumRepository.findAllById(albumIds)).thenReturn(List.of(activeAlbum, inactiveAlbum));

            MusicRecord dto = service.getDiscography(creatorId, authentication);

            assertNotNull(dto);
            assertEquals(1, dto.songs().size());
            assertEquals(1, dto.albums().size());
        }
    }

    // addSong ---------------------------------------------------------------------------------------------------------

    @Test
    void addSong_WhenAlbumExistsAndTrackIndexIsNull_ShouldUseNextIndexAndCallMicroservices() {
        Album album = makeAlbum(1L, true);

        album.getAlbumTracks().add(mockTrackWithIndex(1));
        album.getAlbumTracks().add(mockTrackWithIndex(2));

        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        when(songRepository.save(any(Song.class))).thenAnswer(inv -> {
            Song s = inv.getArgument(0);
            s.setId(101L);
            if (s.getAlbumTracks() == null) s.setAlbumTracks(new ArrayList<>());
            return s;
        });

        when(albumTrackRepository.save(any(AlbumTrack.class))).thenAnswer(inv -> inv.getArgument(0));
        when(albumRepository.save(any(Album.class))).thenAnswer(inv -> inv.getArgument(0));

        SongCreateDTO dto = makeValidSongCreateDtoWithAlbumId(1L);
        dto.setTrackIndex(null);

        var response = service.addSong(dto);
        assertNotNull(response);

        ArgumentCaptor<AlbumTrack> captor = ArgumentCaptor.forClass(AlbumTrack.class);
        verify(albumTrackRepository).save(captor.capture());
        AlbumTrack savedTrack = captor.getValue();
        assertEquals(3, savedTrack.getTrackIndex());

        verify(thumbClient).createRecordOfSong(101L, dto.getTitle());
        verify(genreClient).createRecordOfSong(101L, dto.getGenreIds());
        verify(creatorClient).createRecordOfMusic(101L, dto.getCreatorIds(), MediaType.SONG);

        verify(creatorClient, never()).createRecordOfMusic(eq(1L), anyList(), eq(MediaType.ALBUM));
    }

    @Test
    void addSong_WhenAlbumExistsAndProvidedTrackIndexIsTaken_ShouldUseNextAvailableIndex() {
        Album album = makeAlbum(1L, true);
        album.getAlbumTracks().add(mockTrackWithIndex(5));

        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        when(albumTrackRepository.existsByAlbum_IdAndTrackIndex(1L, 5)).thenReturn(true);

        when(songRepository.save(any(Song.class))).thenAnswer(inv -> {
            Song s = inv.getArgument(0);
            s.setId(102L);
            if (s.getAlbumTracks() == null) s.setAlbumTracks(new ArrayList<>());
            return s;
        });

        when(albumTrackRepository.save(any(AlbumTrack.class))).thenAnswer(inv -> inv.getArgument(0));
        when(albumRepository.save(any(Album.class))).thenAnswer(inv -> inv.getArgument(0));

        SongCreateDTO dto = makeValidSongCreateDtoWithAlbumId(1L);
        dto.setTrackIndex(5);

        service.addSong(dto);

        ArgumentCaptor<AlbumTrack> captor = ArgumentCaptor.forClass(AlbumTrack.class);
        verify(albumTrackRepository).save(captor.capture());
        assertEquals(6, captor.getValue().getTrackIndex());
    }

    @Test
    void addSong_WhenAlbumIsCreatedInline_ShouldAlsoCreateAlbumCreatorRecord() {
        AlbumCreateDTO inlineAlbum = makeInlineAlbumDto();
        SongCreateDTO dto = makeValidSongCreateDtoInlineAlbum(inlineAlbum);

        when(albumRepository.findFirstByTitleIgnoreCaseAndReleaseDateAndUrl(
                inlineAlbum.getTitle(), inlineAlbum.getReleaseDate(), inlineAlbum.getUrl()
        )).thenReturn(Optional.empty());

        when(albumRepository.save(any(Album.class))).thenAnswer(inv -> {
            Album a = inv.getArgument(0);
            if (a.getId() == null) a.setId(7L);
            if (a.getAlbumTracks() == null) a.setAlbumTracks(new ArrayList<>());
            return a;
        });

        when(songRepository.save(any(Song.class))).thenAnswer(inv -> {
            Song s = inv.getArgument(0);
            s.setId(201L);
            if (s.getAlbumTracks() == null) s.setAlbumTracks(new ArrayList<>());
            return s;
        });

        when(albumTrackRepository.save(any(AlbumTrack.class))).thenAnswer(inv -> inv.getArgument(0));

        service.addSong(dto);

        verify(creatorClient).createRecordOfMusic(201L, dto.getCreatorIds(), MediaType.SONG);
        verify(creatorClient).createRecordOfMusic(7L, inlineAlbum.getCreatorIds(), MediaType.ALBUM);
    }

    @Test
    void addSong_WhenAlbumIdDoesNotExist_ShouldThrowResourceNotFoundException() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());
        SongCreateDTO dto = makeValidSongCreateDtoWithAlbumId(1L);
        assertThrows(ResourceNotFoundException.class, () -> service.addSong(dto));
    }

    @Test
    void addSong_WhenDtoIsNull_ShouldThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> service.addSong(null));
    }

    @Test
    void addSong_WhenAlbumIdAndInlineAlbumAreMissing_ShouldThrowBadRequestException() {
        SongCreateDTO dto = makeValidSongCreateDtoWithAlbumId(null);
        dto.setAlbumId(null);
        dto.setAlbum(null);

        assertThrows(BadRequestException.class, () -> service.addSong(dto));
    }

    @Test
    void addSong_WhenTitleIsBlank_ShouldThrowBadRequestException() {
        SongCreateDTO dto = makeValidSongCreateDtoWithAlbumId(1L);
        dto.setTitle("   ");

        assertThrows(BadRequestException.class, () -> service.addSong(dto));
    }

    // addAlbum --------------------------------------------------------------------------------------------------------


    @Test
    void addAlbum_WhenValidDtoWithTwoSongs_ShouldCreateSongsCreateTracksUpdateAlbumLengthAndCallMicroservices() {
        AlbumCreateDTO dto = makeValidAlbumCreateDtoWith2Songs();

        when(albumRepository.save(any(Album.class))).thenAnswer(inv -> {
            Album a = inv.getArgument(0);
            if (a.getId() == null) a.setId(50L);
            if (a.getAlbumTracks() == null) a.setAlbumTracks(new ArrayList<>());
            return a;
        });

        when(albumTrackRepository.existsByAlbum_IdAndTrackIndex(eq(50L), eq(1))).thenReturn(false);
        when(albumTrackRepository.existsByAlbum_IdAndTrackIndex(eq(50L), eq(2))).thenReturn(true);

        when(songRepository.save(any(Song.class))).thenAnswer(new org.mockito.stubbing.Answer<Song>() {
            long id = 300;
            @Override public Song answer(org.mockito.invocation.InvocationOnMock inv) {
                Song s = inv.getArgument(0);
                s.setId(++id);
                if (s.getAlbumTracks() == null) s.setAlbumTracks(new ArrayList<>());
                return s;
            }
        });

        when(albumTrackRepository.save(any(AlbumTrack.class))).thenAnswer(inv -> inv.getArgument(0));

        var response = service.addAlbum(dto);
        assertNotNull(response);

        verify(albumTrackRepository, times(4)).save(any(AlbumTrack.class));

        verify(creatorClient).createRecordOfMusic(50L, dto.getCreatorIds(), MediaType.ALBUM);

        verify(thumbClient, times(2)).createRecordOfSong(anyLong(), anyString());
        verify(genreClient, times(2)).createRecordOfSong(anyLong(), anyList());
        verify(creatorClient, times(2)).createRecordOfMusic(anyLong(), anyList(), eq(MediaType.SONG));
    }

    @Test
    void addAlbum_WhenDtoIsNull_ShouldThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> service.addAlbum(null));
    }

    @Test
    void addAlbum_WhenSongsAreEmpty_ShouldThrowBadRequestException() {
        AlbumCreateDTO dto = new AlbumCreateDTO();
        dto.setTitle("A");
        dto.setUrl("U");
        dto.setReleaseDate(LocalDate.of(2025, 11, 30));
        dto.setActive(true);
        dto.setCreatorIds(List.of(10L));
        dto.setSongs(List.of());

        assertThrows(BadRequestException.class, () -> service.addAlbum(dto));
    }

    // Helpers ---------------------------------------------------------------------------------------------------------

    private SongCreateDTO makeValidSongCreateDtoWithAlbumId(Long albumId) {
        SongCreateDTO dto = new SongCreateDTO();
        dto.setTitle("Song T");
        dto.setUrl("https://example.com/audio/x.mp3");
        dto.setLength(LocalTime.parse("00:03:25"));
        dto.setReleaseDate(LocalDate.of(2025, 11, 30));
        dto.setActive(true);
        dto.setGenreIds(List.of(1L, 2L));
        dto.setCreatorIds(List.of(10L));
        dto.setAlbumId(albumId);
        return dto;
    }

    private AlbumCreateDTO makeInlineAlbumDto() {
        AlbumCreateDTO a = new AlbumCreateDTO();
        a.setTitle("Inline Album");
        a.setUrl("https://example.com/album/inline.jpg");
        a.setReleaseDate(LocalDate.of(2025, 11, 30));
        a.setActive(true);
        a.setCreatorIds(List.of(10L));
        return a;
    }

    private SongCreateDTO makeValidSongCreateDtoInlineAlbum(AlbumCreateDTO album) {
        SongCreateDTO dto = makeValidSongCreateDtoWithAlbumId(null);
        dto.setAlbumId(null);
        dto.setAlbum(album);
        return dto;
    }

    private AlbumCreateDTO makeValidAlbumCreateDtoWith2Songs() {
        AlbumCreateDTO dto = new AlbumCreateDTO();
        dto.setTitle("Album X");
        dto.setUrl("https://example.com/album/x.jpg");
        dto.setReleaseDate(LocalDate.of(2025, 11, 30));
        dto.setActive(true);
        dto.setCreatorIds(List.of(10L));

        SongCreateDTO s1 = new SongCreateDTO();
        s1.setTitle("S1");
        s1.setUrl("https://example.com/audio/s1.mp3");
        s1.setLength(LocalTime.parse("00:01:00"));
        s1.setReleaseDate(LocalDate.of(2025, 11, 30));
        s1.setActive(true);
        s1.setGenreIds(List.of(1L));
        s1.setCreatorIds(List.of(10L));
        s1.setTrackIndex(1);

        SongCreateDTO s2 = new SongCreateDTO();
        s2.setTitle("S2");
        s2.setUrl("https://example.com/audio/s2.mp3");
        s2.setLength(LocalTime.parse("00:02:00"));
        s2.setReleaseDate(LocalDate.of(2025, 11, 30));
        s2.setActive(true);
        s2.setGenreIds(List.of(2L));
        s2.setCreatorIds(List.of(10L));
        s2.setTrackIndex(2);

        dto.setSongs(List.of(s1, s2));
        return dto;
    }

    private Song makeSong(Long id, boolean active) {
        Song s = new Song();
        s.setId(id);
        s.setTitle("Song-" + id);
        s.setUrl("url-" + id);
        s.setLength(LocalTime.parse("00:01:00"));
        s.setReleaseDate(LocalDate.of(2025, 11, 30));
        s.setActive(active);
        if (s.getAlbumTracks() == null) s.setAlbumTracks(new ArrayList<>());
        return s;
    }

    private Album makeAlbum(Long id, boolean active) {
        Album a = new Album();
        a.setId(id);
        a.setTitle("Album-" + id);
        a.setUrl("aurl-" + id);
        a.setReleaseDate(LocalDate.of(2025, 11, 30));
        a.setActive(active);
        a.setNumberOfStreams(0L);
        a.setLength(LocalTime.of(0, 0, 0));
        if (a.getAlbumTracks() == null) a.setAlbumTracks(new ArrayList<>());
        return a;
    }

    private AlbumTrack mockTrackWithIndex(int idx) {
        AlbumTrack t = mock(AlbumTrack.class);
        when(t.getTrackIndex()).thenReturn(idx);
        return t;
    }

    private GenreDTO dummyGenreDto() {
        GenreDTO dto = new GenreDTO();
        dto.setId(1L);
        dto.setName("DummyGenre");
        return dto;
    }

    private CreatorDTO dummyCreatorDto() {
        CreatorDTO dto = new CreatorDTO();
        dto.setId(10L);
        dto.setUsername("dummy_creator");
        return dto;
    }


}
