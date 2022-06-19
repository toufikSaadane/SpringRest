package com.example.SpringBootRestApi.entity;

import com.example.SpringBootRestApi.entity.Dto.CommentDto;
import com.example.SpringBootRestApi.util.Mapper;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "comment",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"body"})}
)
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotEmpty
  @Size(min = 3, message = "name too short")
  private String name;
  @Email
  private String email;
  @NotEmpty
  @Size(min = 30, message = "body too short")
  @Column(name = "body", nullable = false, unique = true)
  private String body;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;


  public Comment mapToEntity(CommentDto commentDto){
    Mapper modelMapper = new Mapper();
    return  modelMapper.mapper().map(commentDto, Comment.class);
  }
}
