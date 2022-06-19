package com.example.SpringBootRestApi.repository;

import com.example.SpringBootRestApi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepository extends JpaRepository<Post,Long> {

  @Query(value = "Select p from Post p where " +
      "p.title LIKE CONCAT('%', :query,'%')" +
      "OR p.description LIKE CONCAT('%', :query,'%')")
  List<Post> searchPost(String query);
}
