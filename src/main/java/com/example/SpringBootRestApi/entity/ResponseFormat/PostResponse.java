package com.example.SpringBootRestApi.entity.ResponseFormat;


import com.example.SpringBootRestApi.entity.Dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostResponse {

  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean lastPage;
  private List<PostDto> content;

}
