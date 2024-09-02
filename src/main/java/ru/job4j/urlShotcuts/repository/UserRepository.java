package ru.job4j.urlShotcuts.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlShotcuts.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserBySite(String site);
}
