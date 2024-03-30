package book.project.First.service.implementation;

import book.project.First.dto.book.BaseBook;
import book.project.First.dto.book.BookDto;
import book.project.First.dto.book.BookPatch;
import book.project.First.dto.book.common.ValueDto;
import book.project.First.entity.book.BookEntity;
import book.project.First.exceptions.BadRequestException;
import book.project.First.filter.BookFilterOptions;
import book.project.First.mapper.BookMapper;
import book.project.First.repository.BookRepository;
import book.project.First.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto Create(BaseBook bookDto) {
        BookEntity bookEntity = bookMapper.toEntity(bookDto);
        BookEntity createFacultyEntity = bookRepository.Create(bookEntity);
        return bookMapper.toDto(createFacultyEntity);
    }

    @Override
    public List<BookDto> FindAll(BookFilterOptions filterOptions, Integer limit, Integer offset) {
        List<BookEntity> bookEntities = bookRepository.FindAll(filterOptions, limit, offset);
        return bookMapper.toDtoList(bookEntities);
    }

    @Override
    public ValueDto<Integer> Count(BookFilterOptions filterOptions) {
        int count = bookRepository.Count(filterOptions);
        return new ValueDto<>(count);
    }


    @Override
    public BookDto Find(Long id) {
        BookEntity bookEntity = bookRepository.Find(id);
        return bookMapper.toDto(bookEntity);
    }

    @Override
    public void Update(Long id, BaseBook bookDto) {
        BookEntity facultyEntity = bookMapper.toEntity(bookDto);
        facultyEntity.setId(id);

        bookRepository.Update(facultyEntity);
    }

    @Override
    public void Patch(Long id, BookPatch bookPatch) {
        if (bookPatch.isEmpty()) {
            throw new BadRequestException("Faculty patch is empty!");
        }

        bookRepository.Patch(id, bookPatch);
    }

    @Override
    public void Delete(Long id) {
        bookRepository.Delete(id);
    }
}
