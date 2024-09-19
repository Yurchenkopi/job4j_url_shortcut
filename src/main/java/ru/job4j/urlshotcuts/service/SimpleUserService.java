package ru.job4j.urlshotcuts.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshotcuts.model.User;
import ru.job4j.urlshotcuts.model.dto.UserDto;
import ru.job4j.urlshotcuts.repository.UserRepository;
import ru.job4j.urlshotcuts.service.utils.CodeGenerator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    private final CodeGenerator codeGenerator;

    private BCryptPasswordEncoder encoder;

    private EntityManager entityManager;

    private static final Logger LOG = LoggerFactory.getLogger(SimpleUserService.class.getName());

    @Override
    public Optional<UserDto> save(User user) {
        var currentUser = findUserBySite(user.getSite());
        UserDto userDto = new UserDto();
        if (currentUser.isEmpty()) {
            userDto.setRegistration(true);
            String password = generatePassword();
            user.setLogin(generateUniqueLogin());
            user.setPassword(encoder.encode(password));
            userDto.setLogin(user.getLogin());
            userDto.setPassword(password);
            try {
                userRepository.save(user);
            } catch (Exception e) {
                LOG.error("Произошла ошибка при сохранении записи в БД: " + e.getMessage());
                return Optional.empty();
            }
        } else {
            userDto.setRegistration(false);
            userDto.setLogin(currentUser.get().getLogin());
            userDto.setPassword(currentUser.get().getPassword());
        }
        return Optional.of(userDto);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        entityManager.clear();
        Optional<User> rsl = Optional.empty();
        try {
            rsl = userRepository.findById(userId);
        } catch (Exception e) {
            LOG.error("Произошла ошибка при поиске пользователя по id: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        entityManager.clear();
        Optional<User> rsl = Optional.empty();
        try {
            rsl = userRepository.findUserByLogin(login);
        } catch (Exception e) {
            LOG.error("Произошла ошибка при поиске пользователя по login: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public Optional<User> findUserBySite(String site) {
        entityManager.clear();
        Optional<User> rsl = Optional.empty();
        try {
            rsl = userRepository.findUserBySite(site);
        } catch (Exception e) {
            LOG.error("Произошла ошибка при поиске пользователя по зарегистрированному сайту: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public List<User> findAll() {
        List<User> rsl = Collections.emptyList();
        try {
            rsl = userRepository.findAll();
        } catch (Exception e) {
            LOG.error("Произошла ошибка при поиске всех пользоавтелей: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public boolean update(User user) {
        boolean rsl = false;
        Optional<User> currentUser = findById(user.getId());
        if (currentUser.isPresent()) {
            if (!currentUser.get().equals(user)) {
                try {
                    userRepository.save(user);
                    rsl = true;
                } catch (Exception e) {
                    LOG.error("Произошла ошибка при обновлении записи в БД: " + e.getMessage());
                }
            }
        }
        return rsl;
    }

    @Override
    public boolean delete(User user) {
        boolean rsl = false;
        Optional<User> currentUser = findById(user.getId());
        if (currentUser.isPresent()) {
            try {
                userRepository.delete(user);
                rsl = true;
            } catch (Exception e) {
                LOG.error("Произошла ошибка при удалении записи в БД: " + e.getMessage());
            }
        }
        return rsl;
    }

    @Override
    public String generateUniqueLogin() {
        var code = codeGenerator.generate(8);
        var login = "user" + code;
        var user = findUserByLogin(login);
        if (user.isPresent()) {
            generateUniqueLogin();
        }
        return login;
    }

    @Override
    public String generatePassword() {
        return codeGenerator.generate(12);
    }
}
