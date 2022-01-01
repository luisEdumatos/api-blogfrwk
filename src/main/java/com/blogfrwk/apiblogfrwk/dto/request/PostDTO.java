package com.blogfrwk.apiblogfrwk.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;

    @NotEmpty
    @Size(min = 10, max = 100)
    private String description;

    @Size(min = 10, max = 100)
    private String mainImage;

    @Size(min = 10, max = 100)
    private String mainLink;
}
