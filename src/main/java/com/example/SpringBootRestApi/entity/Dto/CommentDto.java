package com.example.SpringBootRestApi.entity.Dto;

import com.example.SpringBootRestApi.entity.Comment;
import com.example.SpringBootRestApi.util.Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
  private Long id;
  private String name;
  private String email;
  private String body;

  public CommentDto mapToDto(Comment comment) {
    Mapper modelMapper = new Mapper();
    return modelMapper.mapper().map(comment, CommentDto.class);
  }

}
