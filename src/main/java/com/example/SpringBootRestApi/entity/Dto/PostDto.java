package com.example.SpringBootRestApi.entity.Dto;

import com.example.SpringBootRestApi.entity.Comment;
import com.example.SpringBootRestApi.entity.Post;
import com.example.SpringBootRestApi.util.Mapper;
import lombok.Data;

import java.util.Set;


@Data
public class PostDto {
  private Long id;
  private String title;
  private String description;
  private String content;
  private String image;
  private String location;
  private Set<CommentDto> comments;


  /***
   *
   * @param post
   * @return
   */
  public PostDto mapToDto(Post post) {
    Mapper modelMapper = new Mapper();
    return modelMapper.mapper().map(post, PostDto.class);
  }
}
