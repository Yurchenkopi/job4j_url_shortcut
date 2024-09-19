package ru.job4j.urlshotcuts.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshotcuts.model.User;
import ru.job4j.urlshotcuts.model.dto.UserDto;
import ru.job4j.urlshotcuts.service.UserService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping
public class UserController {
    private final UserService simpleUserService;

    @PostMapping({"/registration"})
    public ResponseEntity<UserDto> create(@Valid @RequestBody User user) {
        return simpleUserService.save(user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(409).build());
    }

}
