package book.project.First.repository;

import book.project.First.dto.book.BookPatch;
import book.project.First.entity.book.BookEntity;
import book.project.First.filter.BookFilterOptions;

import java.util.List;

public interface BookRepository {
    BookEntity Find(Long Id);
    BookEntity Create(BookEntity book);
    List<BookEntity> FindAll(BookFilterOptions filterOptions, Integer limit, Integer offset);
    int Count(BookFilterOptions filterOptions);
    void Update(BookEntity bookEntity);
    void Patch(Long id, BookPatch bookPatch);
    void Delete(Long id);
}
