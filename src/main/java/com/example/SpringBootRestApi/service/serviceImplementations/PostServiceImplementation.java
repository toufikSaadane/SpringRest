package com.example.SpringBootRestApi.service.serviceImplementations;

import com.example.SpringBootRestApi.entity.Dto.PostDto;
import com.example.SpringBootRestApi.entity.Post;
import com.example.SpringBootRestApi.entity.ResponseFormat.PostResponse;
import com.example.SpringBootRestApi.exception.ResourceNotFoundException;
import com.example.SpringBootRestApi.repository.PostRepository;
import com.example.SpringBootRestApi.service.PostServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostServiceInterface {

  @Autowired
  private final PostRepository postRepository;

  public PostServiceImplementation(PostRepository postRepository) {
    this.postRepository = postRepository;
  }



  @Override
  public PostDto createPost(PostDto postDto) {
    Post post = new Post();
    Post mappedPost = post.mapToEntity(postDto);
    Post savedPost = postRepository.save(mappedPost);

    return postDto.mapToDto(savedPost);
  }

  @Override
  public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy) {
    //create pageable
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    Page<Post> posts = postRepository.findAll(pageable);
    List<Post> postList = posts.getContent();

    PostDto postDto = new PostDto();
    PostResponse postResponse = new PostResponse();

    List<PostDto> content =  postList.stream().map(postDto::mapToDto).collect(Collectors.toList());
    postResponse.setContent(content);
    postResponse.setPageNumber(posts.getNumber());
    postResponse.setPageSize(posts.getSize());
    postResponse.setTotalElements(posts.getTotalElements());
    postResponse.setTotalPages(posts.getTotalPages());
    postResponse.setLastPage(posts.isLast());

    return postResponse;
  }

  @Override
  public PostDto getPostById(Long id) {
    Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
    PostDto postDto = new PostDto();
    return postDto.mapToDto(post);
  }

  @Override
  public PostDto updatePostById(PostDto postDto, Long id) {
    Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
    post.setTitle(postDto.getTitle());
    post.setDescription(postDto.getDescription());
    post.setContent(postDto.getContent());
    Post savedPost = postRepository.save(post);

    return postDto.mapToDto(savedPost);
  }

  @Override
  public void deletePost(Long id) {
    Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
    postRepository.delete(post);
  }


  public List<Post> searchPost(int pageNumber, int pageSize, String sortBy,String query) {
    return postRepository.searchPost(query);
  }

}
