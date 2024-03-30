package book.project.First.service;

import book.project.First.dto.book.BaseBook;
import book.project.First.dto.book.BookDto;
import book.project.First.dto.book.BookPatch;
import book.project.First.dto.book.common.ValueDto;
import book.project.First.filter.BookFilterOptions;

import java.util.List;

public interface BookService {
    BookDto Find(Long id);
    BookDto Create(BaseBook bookDto);
    List<BookDto> FindAll(BookFilterOptions filterOptions, Integer limit, Integer offset);
    ValueDto<Integer> Count(BookFilterOptions filterOptions);
    void Update(Long id, BaseBook bookDto);
    void Patch(Long id, BookPatch bookPatch);
    void Delete(Long id);
}
