package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.UserRequestDto;
import org.course.courserpp.dto.UserResponseDto;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserResponseDto findById(String id) {
        return userRepository.findById(id).map(this::toDto).orElseThrow();
    }

    public UserResponseDto save(UserRequestDto dto) {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        return toDto(userRepository.save(user));
    }

    public UserResponseDto update(String id, UserRequestDto dto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        return toDto(userRepository.save(user));
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    private UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        return dto;
    }
}
