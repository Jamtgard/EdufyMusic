package com.example.EdufyMusic.repositories;

import com.example.EdufyMusic.models.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// ED-75-SJ
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("""
           select a from Album a
           left join fetch a.albumTracks t
           left join fetch t.song s
           where a.id = :id
           """)
    Optional<Album> findByIdWithTracks(@Param("id") Long id);
}
