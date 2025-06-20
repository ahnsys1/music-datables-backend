package com.music.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music.application.entity.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
