package book.project.First.controller;

import book.project.First.annotation.TrackExecution;
import book.project.First.dto.book.BaseBook;
import book.project.First.dto.book.BookDto;
import book.project.First.dto.book.BookPatch;
import book.project.First.dto.book.common.ValueDto;
import book.project.First.filter.BookFilterOptions;
import book.project.First.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@TrackExecution
@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService _facultyService;

    @GetMapping
    public List<BookDto> findAll(BookFilterOptions filterOptions,
                                 @Parameter(name = "limit") @RequestParam(required = false) Integer limit,
                                 @Parameter(name = "offset") @RequestParam(required = false) Integer offset)  {
        return _facultyService.FindAll(filterOptions, limit, offset);
    }

    @GetMapping("count")
    public ValueDto<Integer> count(BookFilterOptions filterOptions) {
        return _facultyService.Count(filterOptions);
    }


    @TrackExecution(isEnabled = false)
    @GetMapping("{id}")
    public BookDto Find(@PathVariable Long id){
        return _facultyService.Find(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BaseBook faculty) {
        return _facultyService.Create(faculty);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody BaseBook facultyDto) {
        _facultyService.Update(id, facultyDto);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody BookPatch facultyPatch) {
        _facultyService.Patch(id, facultyPatch);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        _facultyService.Delete(id);
    }
}
