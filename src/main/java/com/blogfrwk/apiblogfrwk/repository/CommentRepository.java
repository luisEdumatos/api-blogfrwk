package com.blogfrwk.apiblogfrwk.repository;

import com.blogfrwk.apiblogfrwk.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment WHERE post_id=?1", nativeQuery = true)
    List<Comment> findCommentByPostId(Long post_id); 
}
