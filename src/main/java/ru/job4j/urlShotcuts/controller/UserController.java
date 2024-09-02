package ru.job4j.urlShotcuts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.urlShotcuts.model.User;
import ru.job4j.urlShotcuts.model.dto.UserDto;
import ru.job4j.urlShotcuts.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
