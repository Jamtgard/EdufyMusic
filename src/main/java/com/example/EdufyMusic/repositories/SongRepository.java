package com.example.EdufyMusic.repositories;

import com.example.EdufyMusic.models.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// ED-74-SJ

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

// USER SEARCHES -------------------------------------------------------------------------------------------------------

    // ED-49-SJ
    List<Song> findByTitleContainingIgnoreCaseAndActiveIsTrue(String title);

    // ED-80-SJ
    List<Song> findAllByActiveTrue();


// ADMIN SEARCHES ------------------------------------------------------------------------------------------------------

    // ED-261-SJ
    List<Song> findByTitleContainingIgnoreCase(String title);

    // ED-80-SJ
    List<Song> findAll();

// CLIENT SEARCHES -----------------------------------------------------------------------------------------------------

    // ED-281-SJ
    @Query("SELECT s.id FROM Song s JOIN s.userHistory h WHERE KEY(h) = :userId")
    List<Long> findSongIdsByUserIdInHistory(@Param("userId") Long userId);

    // ED-253-SJ
    Song findByIdAndActiveTrue(Long id);


}
