package book.project.First.service.implementation;

import book.project.First.dto.user.UserCreateDto;
import book.project.First.dto.user.UserDto;
import book.project.First.dto.user.UserUpdateDto;
import book.project.First.entity.user.UserEntity;
import book.project.First.exceptions.DataConflictException;
import book.project.First.exceptions.NotFoundException;
import book.project.First.mapper.UserMapper;
import book.project.First.repository.user.UserRepository;
import book.project.First.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto create(UserCreateDto userDto) {
        UserEntity userEntity = userMapper.toEntity(userDto);

        String passwordHash = bCryptPasswordEncoder.encode(userDto.getPassword());
        userEntity.setPasswordHash(passwordHash);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ConstraintViolationException
                    && "users_username_key".equals(((ConstraintViolationException) cause).getConstraintName())) {

                String errorMessage = String.format("Username %s is already used!", userEntity.getUsername());
                throw new DataConflictException(errorMessage);
            }

            throw e;
        }

        return userMapper.toDto(userEntity);
    }

    @Override
    public List<UserDto> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userMapper.toDtoList(userEntities);
    }

    @Override
    public UserDto find(Long id) {
        UserEntity userEntity = findEntity(id);
        return userMapper.toDto(userEntity);
    }

    public void update(Long id, UserUpdateDto userDto) {
        UserEntity userEntity = findEntity(id);
        userMapper.update(userEntity, userDto);

        try {
            userRepository.save(userEntity);
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ConstraintViolationException
                    && "users_username_key".equals(((ConstraintViolationException) cause).getConstraintName())) {

                String errorMessage = String.format("Username %s is already used!", userEntity.getUsername());
                throw new DataConflictException(errorMessage);
            }

            throw e;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        int affectedRows = userRepository.removeById(id);
        if (affectedRows == 0) {
            throw new NotFoundException("User with id " + id + " not found!");
        }
    }

    private UserEntity findEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
    }
}