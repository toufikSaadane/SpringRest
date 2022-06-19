package com.example.SpringBootRestApi.entity;

import com.example.SpringBootRestApi.entity.Dto.PostDto;
import com.example.SpringBootRestApi.util.Mapper;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "blog_posts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"post_title"})
        })
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotEmpty
  @Size(min = 3, message = "title too short")
  @Column(name = "post_title", nullable = false)
  private String title;
  @NotEmpty
  @Size(min = 10, message = "description too short")
  @Column(name = "post_description")
  private String description;
  @NotEmpty
  @Size(min = 10, message = "content too short")
  @Column(name = "post_content", nullable = false)
  private String content;
  @Column(name = "image")
  private String image;
  @Column(name = "location")
  private String location;

  @OneToMany(
          mappedBy = "post",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private Set<Comment> comments = new HashSet<>();

  public Post mapToEntity(PostDto postDto) {
    Mapper modelMapper = new Mapper();
    return modelMapper.mapper().map(postDto, Post.class);
  }

}
