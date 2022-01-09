package com.blogfrwk.apiblogfrwk.repository;

import com.blogfrwk.apiblogfrwk.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotoRepository  extends JpaRepository<Photo, Long> {

    @Query(value = "SELECT * FROM photo WHERE post_id=?1", nativeQuery = true)
    List<Photo> findPhotoByPostId(Long post_id);
}
