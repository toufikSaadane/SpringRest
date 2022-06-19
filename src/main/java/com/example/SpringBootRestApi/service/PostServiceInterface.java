package com.example.SpringBootRestApi.service;

import com.example.SpringBootRestApi.entity.Dto.PostDto;
import com.example.SpringBootRestApi.entity.ResponseFormat.PostResponse;

import java.util.List;

public interface PostServiceInterface {
  PostDto createPost(PostDto postDto);
  PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy);
  PostDto getPostById(Long id);
  PostDto updatePostById(PostDto postDto,Long id);
  void deletePost(Long id);
}
