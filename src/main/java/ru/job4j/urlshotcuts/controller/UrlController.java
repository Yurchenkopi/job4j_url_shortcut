package ru.job4j.urlshotcuts.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshotcuts.model.Url;
import ru.job4j.urlshotcuts.dto.UrlDto;
import ru.job4j.urlshotcuts.service.UrlService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UrlController {

    private final UrlService simpleUrlService;

    @PostMapping({"/convert"})
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody Url url) {
        return simpleUrlService.convert(url)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        return simpleUrlService.incrementTotalAndGet(code)
                .map(url -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(url.getUrl()))
                        .build()
                )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/statistic")
    public ResponseEntity<List<UrlDto>> stat() {
        var rsl = simpleUrlService.findAllByUser();
        return rsl.isEmpty()
                ? ResponseEntity.notFound().build() : ResponseEntity.ok(rsl);
    }
}
