package com.blogfrwk.apiblogfrwk.dto.request;

import com.blogfrwk.apiblogfrwk.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    @NotNull
    private Post post;

    @NotEmpty
    @Size(min = 10, max = 80)
    private String comment;
}
