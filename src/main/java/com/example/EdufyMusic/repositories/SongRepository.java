package com.example.EdufyMusic.repositories;

import com.example.EdufyMusic.models.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ED-74-SJ

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
