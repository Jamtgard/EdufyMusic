package com.example.EdufyMusic.models.DTO.mappers;

import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.exceptions.NoContentException;
import com.example.EdufyMusic.models.DTO.CreatorDTO;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// ED-275-SJ
@Component
public class CreatorResponseMapper {

    private static CreatorClient creatorClient;

    @Autowired
    public CreatorResponseMapper(CreatorClient creatorClient) {CreatorResponseMapper.creatorClient = creatorClient;}

    // ED-275-SJ
    public static List<CreatorDTO> getSongCreatorsForAdmin(Song song) {
        List<CreatorDTO> creators = creatorClient.getCreatorsByMedia("SONG", song.getId());
        if (creators == null || creators.isEmpty()) {
            throw new NoContentException("List of Creators");
        }
        return creators;
    }

    // ED-275-SJ
    public static List<CreatorDTO> getSongCreatorForUser(Song song) {
        return getSongCreatorsForAdmin(song).stream()
                .map(cDTO -> {
                    CreatorDTO creatorDTO = new CreatorDTO();
                    creatorDTO.setUsername(cDTO.getUsername());
                    return creatorDTO;
                })
                .collect(Collectors.toList());
    }

    // ED-275-SJ
    public static List<CreatorDTO> getAlbumCreatorForAdmin(Album album) {
        List<CreatorDTO> creators = creatorClient.getCreatorsByMedia("ALBUM", album.getId());
        if (creators == null || creators.isEmpty()) {
            throw new NoContentException("List of Creators");
        }
        return creators;
    }

    // ED-275-SJ
    public static List<CreatorDTO> getAlbumCreatorForUser(Album album) {
        return getAlbumCreatorForAdmin(album).stream()
                .map(cDTO -> {
                    CreatorDTO creatorDTO = new CreatorDTO();
                    creatorDTO.setUsername(cDTO.getUsername());
                    return creatorDTO;
                })
                .collect(Collectors.toList());
    }
}
