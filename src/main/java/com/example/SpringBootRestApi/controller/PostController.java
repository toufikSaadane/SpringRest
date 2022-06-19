package com.example.SpringBootRestApi.controller;

import com.example.SpringBootRestApi.entity.Dto.PostDto;
import com.example.SpringBootRestApi.entity.Post;
import com.example.SpringBootRestApi.entity.ResponseFormat.PostResponse;
import com.example.SpringBootRestApi.service.serviceImplementations.PostServiceImplementation;
import com.example.SpringBootRestApi.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class PostController {

  private final PostServiceImplementation postServiceInmplementation;

  @Autowired
  public PostController(PostServiceImplementation postServiceInmplementation) {
    PostServiceImplementation p = postServiceInmplementation;
    this.postServiceInmplementation = postServiceInmplementation;
  }

  @PostMapping
  public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
    return new ResponseEntity<>(postServiceInmplementation.createPost(postDto), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<PostResponse> getPosts(
          @RequestParam(value = "pageNumber", defaultValue= AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNumber,
          @RequestParam(value = "pageSize",  defaultValue= AppConstants.DEFAULT_PAGE_SIZE ,required = false) int pageSize,
          @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_PAGE_SORT_PARAM, required = false) String sortBy
  ) {

    PostResponse postResponse = postServiceInmplementation.getAllPosts(pageNumber, pageSize, sortBy);
    return new ResponseEntity<>(postResponse, HttpStatus.OK);
  }

  @GetMapping(value = "{postId}")
  public ResponseEntity<PostDto> findPostById(@PathVariable( name = "postId") Long id) {
    return new ResponseEntity<>(postServiceInmplementation.getPostById(id), HttpStatus.CREATED);
  }

  @PutMapping(value = "{postId}/update")
  public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "postId") Long id){
    return new ResponseEntity<>(postServiceInmplementation.updatePostById(postDto, id), HttpStatus.OK);
  }

  @DeleteMapping(value = "{postId}/delete")
  public  ResponseEntity<String> deletePostById(@PathVariable(name = "postId") Long id){
    postServiceInmplementation.deletePost(id);
    String message = String.format("Post %d deleted", id);
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  // mysql://bd0b1cd4f8e43c:4bedc78a@us-cdbr-east-05.cleardb.net/heroku_8c98504a99b57be?reconnect=true
  @GetMapping("/search")
  public ResponseEntity<PostResponse> search(
      @RequestParam(value = "query", required = true) String query,
      @RequestParam(value = "pageNumber", defaultValue= AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNumber,
      @RequestParam(value = "pageSize",  defaultValue= AppConstants.DEFAULT_PAGE_SIZE ,required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_PAGE_SORT_PARAM, required = false) String sortBy
  ) {
    PostResponse postResponse = postServiceInmplementation.getAllPosts(pageNumber, pageSize, sortBy);

    List<PostDto> filtredPosts = postResponse
        .getContent()
        .stream()
        .filter(x -> x.getTitle().contains(query)).collect(Collectors.toList());
    postResponse.setContent(filtredPosts);
    return new ResponseEntity<>(postResponse, HttpStatus.OK);
  }
}
