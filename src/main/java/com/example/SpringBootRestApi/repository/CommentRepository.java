package com.example.SpringBootRestApi.repository;

import com.example.SpringBootRestApi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
  List<Comment> findCommentsByPost_Id(long postId);
}
