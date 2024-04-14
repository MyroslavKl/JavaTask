package book.project.First.mapper;

import book.project.First.dto.user.UserCreateDto;
import book.project.First.dto.user.UserDto;
import book.project.First.dto.user.UserUpdateDto;
import book.project.First.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserCreateDto userDto);
    UserEntity toEntity(UserUpdateDto userDto);
    UserDto toDto(UserEntity userEntity);
    List<UserDto> toList(List<UserEntity> userEntities);
    List<UserDto> toDtoList(List<UserEntity> userEntities);
    void update(@MappingTarget UserEntity userEntity, UserUpdateDto userDto);
}