package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest")
@AllArgsConstructor
public class RestAdminController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> viewUser(@PathVariable("id") Long id) {
        try {
            User user = userService.findById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(String.valueOf(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@ModelAttribute("user") @Valid User user,
                                           @RequestParam("listroles[]") String[] listroles) {
        return new ResponseEntity<>(userService.saveUserAndRoles(user, listroles), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@ModelAttribute("user") @Valid User user,
                                           @RequestParam("listroles[]") String[] listroles) {
        return new ResponseEntity<>(userService.saveUserAndRoles(user, listroles), HttpStatus.OK);
    }
}