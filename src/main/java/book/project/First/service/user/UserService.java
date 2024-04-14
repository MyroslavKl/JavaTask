package book.project.First.service.user;

import book.project.First.dto.user.UserCreateDto;
import book.project.First.dto.user.UserDto;

public interface UserService {
    UserDto create (UserCreateDto userDto);
}