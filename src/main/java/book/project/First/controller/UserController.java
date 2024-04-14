package book.project.First.controller;

import book.project.First.dto.user.UserCreateDto;
import book.project.First.dto.user.UserDto;
import book.project.First.dto.user.UserUpdateDto;
import book.project.First.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserCreateDto userDto) {
        return userService.create(userDto);
    }
    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public UserDto find(@PathVariable Long id) {
        return userService.find(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody UserUpdateDto userDto) {
        userService.update(id, userDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}