package ru.job4j.urlshotcuts.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshotcuts.model.Url;
import ru.job4j.urlshotcuts.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {
    List<Url> findAllByUser(User user);

    Optional<Url> findByUrl(String url);

    Optional<Url> findUrlByCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE Url u SET u.total = u.total + 1 WHERE u.code = :code")
    void incrementTotal(String code);
}
