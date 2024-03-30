package book.project.First.mapper;

import book.project.First.dto.book.BaseBook;
import book.project.First.dto.book.BookDto;
import book.project.First.entity.book.BookEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookEntity toEntity(BaseBook bookDto);
    BookDto toDto(BookEntity bookEntity);
    List<BookDto> toDtoList(List<BookEntity> bookEntities);
}
