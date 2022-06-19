package com.example.SpringBootRestApi.service.serviceImplementations;

import com.example.SpringBootRestApi.entity.Comment;
import com.example.SpringBootRestApi.entity.Dto.CommentDto;
import com.example.SpringBootRestApi.entity.Post;
import com.example.SpringBootRestApi.exception.ResourceNotFoundException;
import com.example.SpringBootRestApi.repository.CommentRepository;
import com.example.SpringBootRestApi.repository.PostRepository;
import com.example.SpringBootRestApi.service.CommentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImplementation implements CommentServiceInterface {

  @Autowired
  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  public CommentServiceImplementation(CommentRepository commentRepository, PostRepository postRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
  }

  @Override
  public CommentDto createComment(long postId, CommentDto commentDto) {

    Comment comment = new Comment();
    Comment commentEntity = comment.mapToEntity(commentDto);
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    commentEntity.setPost(post);
    Comment savedComment = commentRepository.save(commentEntity);

    return commentDto.mapToDto(savedComment);
  }

  @Override
  public List<CommentDto> getCommentsByPostId(long id) {
    List<Comment> comments = commentRepository.findCommentsByPost_Id(id);

    return comments.stream().map(comment -> getCommentDtoInstance().mapToDto(comment)).collect(Collectors.toList());
  }

  @Override
  public CommentDto findCommentById(long postId, long commentId) {
    Comment comment = findComment(postId, commentId);
    return getCommentDtoInstance().mapToDto(comment);
  }


  @Override
  public CommentDto updateCommentById(long postId, long commentId, CommentDto commentRequest) {
    Comment comment = findComment(postId, commentId);
    comment.setName(commentRequest.getName());
    comment.setBody(commentRequest.getBody());
    comment.setEmail(commentRequest.getEmail());

    Comment updatedComment = commentRepository.save(comment);
    return getCommentDtoInstance().mapToDto(updatedComment);
  }

  @Override
  public void deleteCommentById(long postId, long commentId) {
    commentRepository.delete(findComment(postId,commentId));
  }


  public static CommentDto getCommentDtoInstance(){
    return new CommentDto();
  }

  public  Comment findComment(long postId, long commentId){
    Post post = postRepository.findById(postId).orElseThrow(
            () -> new ResourceNotFoundException("Post", "id", postId));
    Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new ResourceNotFoundException("Comment", "id", commentId));
    if (!comment.getPost().getId().equals(post.getId())) {
      throw new ResourceNotFoundException("Comment", "id", commentId);
    }

    return comment;
  }
}
