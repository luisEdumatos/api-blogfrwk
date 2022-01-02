package com.blogfrwk.apiblogfrwk.repository;

import com.blogfrwk.apiblogfrwk.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
