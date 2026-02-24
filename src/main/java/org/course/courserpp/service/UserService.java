package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.UserRequestDto;
import org.course.courserpp.dto.response.UserResponseDto;
import org.course.courserpp.mapper.UserMapper;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto update(String id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
