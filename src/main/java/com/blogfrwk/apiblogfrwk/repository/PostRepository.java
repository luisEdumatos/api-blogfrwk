package com.blogfrwk.apiblogfrwk.repository;

import com.blogfrwk.apiblogfrwk.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
