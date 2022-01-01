package com.blogfrwk.apiblogfrwk.dto.mapper;

import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toModel(PostDTO postDTO);

    PostDTO toDTO(Post post);
}
