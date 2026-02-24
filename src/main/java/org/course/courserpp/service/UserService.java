package org.course.courserpp.service;

import org.course.courserpp.model.User;
import org.course.courserpp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean authenticate(String login, String password) {
        return userRepository.findByLogin(login)
                .map(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElse(false);
    }

    public boolean register(String login, String password) {
        if (userRepository.findByLogin(login).isPresent()) return false;
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }
}
