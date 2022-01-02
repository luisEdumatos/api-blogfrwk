package com.blogfrwk.apiblogfrwk.dto.mapper;

import com.blogfrwk.apiblogfrwk.dto.request.CommentDTO;
import com.blogfrwk.apiblogfrwk.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment toModel(CommentDTO commentDTO);

    CommentDTO toDTO(Comment comment);
}
