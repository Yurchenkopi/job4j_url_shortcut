package ru.job4j.urlshotcuts.service;

import ru.job4j.urlshotcuts.model.Url;
import ru.job4j.urlshotcuts.dto.UrlDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UrlService {
    List<UrlDto> findAllByUser();

    Optional<Url> findByUrl(String url);

    Optional<Url> findUrlByCode(String code);

    Optional<Map<String, String>> convert(Url url);

    String generateUniqueUrlCode();

    Optional<Url> incrementTotalAndGet(String code);
}
