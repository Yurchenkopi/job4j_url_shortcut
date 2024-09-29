package ru.job4j.urlshotcuts.service;

import ru.job4j.urlshotcuts.model.User;
import ru.job4j.urlshotcuts.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> save(User user);

    Optional<User> findById(Integer userId);

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserBySite(String site);

    List<User> findAll();

    boolean update(User user);

    boolean delete(User user);

    String generateUniqueLogin();

    String generatePassword();
}
