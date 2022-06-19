package com.example.SpringBootRestApi.service;

import com.example.SpringBootRestApi.entity.Comment;
import com.example.SpringBootRestApi.entity.Dto.CommentDto;

import java.util.List;

public interface CommentServiceInterface {
  CommentDto createComment(long postId ,CommentDto commentDto);
  List<CommentDto> getCommentsByPostId(long id);
  CommentDto findCommentById(long postId, long commentId);
  CommentDto updateCommentById(long postId, long commentId, CommentDto commentRequest);
  void deleteCommentById(long postId, long commentId);

}
