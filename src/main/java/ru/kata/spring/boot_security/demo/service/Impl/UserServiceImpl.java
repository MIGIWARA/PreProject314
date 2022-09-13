package ru.kata.spring.boot_security.demo.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;


    @Transactional
    public User findById(Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUserAndRoles(User user, String[] listroles) {
        Set<Role> setRole = new HashSet<>();
        for (String role : listroles) {
            setRole.add(roleService.findByName(role));
        }
        user.setRoles(setRole);
        if (user.getPassword().equals("default_pass")) {
            user.setPassword(userRepository.findByEmail(user.getEmail()).getPassword());
        } else {
            user.setPassword(user.pswEncoder());
        }

        userRepository.save(user);
        return user;
    }


    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
