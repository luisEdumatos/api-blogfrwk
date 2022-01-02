package com.blogfrwk.apiblogfrwk.repository;

import com.blogfrwk.apiblogfrwk.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository  extends JpaRepository<Photo, Long> {
}
