package com.example.EdufyMusic.models.records;

import com.example.EdufyMusic.models.entities.Album;

import java.util.List;

// ED-309-SJ
public record AlbumResolution(
        Album album,
        boolean createdNewAlbum,
        List<Long> albumCreatorIds
) {
    public static AlbumResolution existing(Album album) {
        return new AlbumResolution(album, false, null);
    }

    public static AlbumResolution created(Album album, List<Long> albumCreatorIds) {
        return new AlbumResolution(album, true, albumCreatorIds);
    }
}
