package com.example.EdufyMusic.repositories;

import com.example.EdufyMusic.models.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// ED-75-SJ
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    // ED-75-SJ
    @Query("""
           select a from Album a
           left join fetch a.albumTracks t
           left join fetch t.song s
           where a.id = :id
           """)
    List<Album> findByIdWithTracks(@Param("id") Long id);

    // ED-50-SJ
    List<Album> findByTitleContainingIgnoreCaseAndActiveIsTrue(String title);

}
