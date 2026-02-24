package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.UserRequestDto;
import org.course.courserpp.dto.UserResponseDto;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDto> findById(String id) {
        return userRepository.findById(id).map(this::toResponseDto);
    }

    public UserResponseDto create(UserRequestDto dto) {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        return toResponseDto(userRepository.save(user));
    }

    public Optional<UserResponseDto> update(String id, UserRequestDto dto) {
        return userRepository.findById(id).map(user -> {
            user.setLogin(dto.getLogin());
            user.setPassword(dto.getPassword());
            return toResponseDto(userRepository.save(user));
        });
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findEntityById(String id) {
        return userRepository.findById(id);
    }

    private UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        return dto;
    }
}
