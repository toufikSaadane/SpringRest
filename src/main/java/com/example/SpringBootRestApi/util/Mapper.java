package com.example.SpringBootRestApi.util;

import com.example.SpringBootRestApi.entity.Dto.PostDto;
import com.example.SpringBootRestApi.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

  public ModelMapper mapper(){
    return new ModelMapper();
  }

  public Post mapToEntity(PostDto postDto) {
    Mapper modelMapper = new Mapper();
    return modelMapper.mapper().map(postDto, Post.class);
  }

  public PostDto mapToDto(Post post) {
    Mapper modelMapper = new Mapper();
    return modelMapper.mapper().map(post, PostDto.class);
  }
}
