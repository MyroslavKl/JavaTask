package book.project.First.service.user;

import book.project.First.dto.user.UserCreateDto;
import book.project.First.dto.user.UserDto;
import book.project.First.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserDto create (UserCreateDto userDto);
    List<UserDto> findAll();
    UserDto find(Long id);
    void update(Long id, UserUpdateDto userDto);
    void delete(Long id);
}