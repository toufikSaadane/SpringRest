package com.example.SpringBootRestApi.controller;

import com.example.SpringBootRestApi.entity.Dto.CommentDto;
import com.example.SpringBootRestApi.service.CommentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/")
public class CommentController {

  private final CommentServiceInterface commentServiceInterface;

  @Autowired
  public CommentController(CommentServiceInterface commentServiceInterface) {
    this.commentServiceInterface = commentServiceInterface;
  }

  /***
   *
   * @param commentDto
   * @param postId
   * @return
   */
  @PostMapping(value = "posts/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(@Valid
          @RequestBody CommentDto commentDto,
          @PathVariable(name = "postId") long postId) {

    return new ResponseEntity<>(commentServiceInterface.createComment(postId, commentDto), HttpStatus.CREATED);
  }

  /***
   *
   * @param postId
   * @param commentId
   * @return
   */
  @GetMapping(value = "posts/{postId}/comments/{commentId}")
  public ResponseEntity<CommentDto> getCommentId(
          @PathVariable(name = "postId") long postId,
          @PathVariable(name = "commentId") long commentId
  ) {
    return new ResponseEntity<>(commentServiceInterface.findCommentById(postId, commentId), HttpStatus.CREATED);
  }

  @GetMapping(value = "posts/{postId}/comments")
  public ResponseEntity<List<CommentDto>> getCommentsByPostId(
          @PathVariable(name = "postId") long postId) {
    return new ResponseEntity<>(commentServiceInterface.getCommentsByPostId(postId), HttpStatus.CREATED);
  }

  @PutMapping(value = "posts/{postId}/comments/{commentId}/update")
  public ResponseEntity<CommentDto> updateComment(
          @PathVariable(name = "postId") long postId,
          @PathVariable(name = "commentId") long commentId,
          @RequestBody CommentDto commentRequest
  ) {
    return new ResponseEntity<>(commentServiceInterface.updateCommentById(
            postId, commentId, commentRequest), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "posts/{postId}/comments/{commentId}/delete")
  public  ResponseEntity<String> deleteComment(
          @PathVariable(name = "postId") long postId,
          @PathVariable(name = "commentId") long commentId
  ) {
    commentServiceInterface.deleteCommentById(postId, commentId);
    String message = String.format("Post %d deleted", commentId);
    return new ResponseEntity<>(message, HttpStatus.OK);
  }


}
