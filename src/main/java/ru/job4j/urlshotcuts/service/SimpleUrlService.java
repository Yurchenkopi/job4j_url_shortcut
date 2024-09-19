package ru.job4j.urlshotcuts.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshotcuts.model.Url;
import ru.job4j.urlshotcuts.model.User;
import ru.job4j.urlshotcuts.model.dto.UrlDto;
import ru.job4j.urlshotcuts.repository.UrlRepository;
import ru.job4j.urlshotcuts.repository.UserRepository;
import ru.job4j.urlshotcuts.service.utils.CodeGenerator;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@AllArgsConstructor
public class SimpleUrlService implements UrlService {
    private final UrlRepository urlRepository;

    private final UserRepository userRepository;

    private final CodeGenerator codeGenerator;

    private EntityManager entityManager;

    private static final Logger LOG = LoggerFactory.getLogger(SimpleUrlService.class.getName());

    @Override
    public List<UrlDto> findAllByUser() {
        List<UrlDto> rsl = Collections.emptyList();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = Optional.empty();
        if (authentication != null && authentication.isAuthenticated()) {
            String userName = (String) authentication.getPrincipal();
            userOptional = userRepository.findUserByLogin(userName);
        }
        if (userOptional.isPresent()) {
            rsl = urlRepository.findAllByUser(userOptional.get()).stream()
                    .map(this::urlToUrlDto)
                    .toList();
        }
        return rsl;

    }

    @Override
    public Optional<Url> findByUrl(String url) {
        entityManager.clear();
        Optional<Url> rsl = Optional.empty();
        try {
            rsl = urlRepository.findByUrl(url);
        } catch (Exception e) {
            LOG.error("Произошла ошибка при поиске Url по имени: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public Optional<Url> findUrlByCode(String code) {
        entityManager.clear();
        Optional<Url> rsl = Optional.empty();
        try {
            rsl = urlRepository.findUrlByCode(code);
        } catch (Exception e) {
            LOG.error("Произошла ошибка при поиске Url по уникальному коду: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public Map<String, String> convert(Url url) {
        var rsl = new HashMap<String, String>();
        var currentUrl = findByUrl(url.getUrl());
        if (currentUrl.isEmpty()) {
            var user = new User();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String userName = (String) authentication.getPrincipal();
                var userOptional = userRepository.findUserByLogin(userName);
                if (userOptional.isPresent()) {
                    user = userOptional.get();
                }
            }
            if (url.getUrl().startsWith(user.getSite())) {
                url.setUser(user);
                url.setUrl(url.getUrl());
                url.setCode(generateUniqueUrlCode());
                rsl.put("code", url.getCode());
                try {
                    urlRepository.save(url);
                } catch (Exception e) {
                    LOG.error("Произошла ошибка при сохранении записи в БД: " + e.getMessage());
                    return Collections.emptyMap();
                }
            } else {
                throw new IllegalArgumentException("Url should start with: " + user.getSite());
            }
        } else {
            rsl.put("code", currentUrl.get().getCode());
        }
        return rsl;
    }

    @Override
    public String generateUniqueUrlCode() {
        var code = codeGenerator.generate(8);
        var url = urlRepository.findUrlByCode(code);
        if (url.isPresent()) {
            generateUniqueUrlCode();
        }
        return code;
    }

    @Override
    public Optional<Url> incrementTotalAndGet(String code) {
        urlRepository.incrementTotal(code);
        return urlRepository.findUrlByCode(code);
    }

    private UrlDto urlToUrlDto(Url url) {
        return new UrlDto(url.getUrl(), url.getTotal());
    }
}
