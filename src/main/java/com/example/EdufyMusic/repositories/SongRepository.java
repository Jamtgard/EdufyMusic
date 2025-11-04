package com.example.EdufyMusic.repositories;

import com.example.EdufyMusic.models.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ED-74-SJ

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

// USER SEARCHES -------------------------------------------------------------------------------------------------------

    // ED-49-SJ
    List<Song> findByTitleContainingIgnoreCaseAndActiveIsTrue(String title);


// ADMIN SEARCHES ------------------------------------------------------------------------------------------------------

    // ED-261-SJ
    List<Song> findByTitleContainingIgnoreCase(String title);
}
