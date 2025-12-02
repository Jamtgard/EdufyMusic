package com.example.EdufyMusic.repositories;

import com.example.EdufyMusic.models.entities.AlbumTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ED-309-SJ
@Repository
public interface AlbumTrackRepository extends JpaRepository<AlbumTrack, Long> {

    boolean existsByAlbum_IdAndSong_Id(Long albumId, Long songId);

    boolean existsByAlbum_IdAndTrackIndex(Long albumId, Integer trackIndex);

}
