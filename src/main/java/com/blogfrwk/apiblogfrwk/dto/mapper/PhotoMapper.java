package com.blogfrwk.apiblogfrwk.dto.mapper;

import com.blogfrwk.apiblogfrwk.dto.request.PhotoDTO;
import com.blogfrwk.apiblogfrwk.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoMapper {

    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);

    Photo toModel(PhotoDTO photoDTO);

    PhotoDTO toDTO(Photo photo);
}
