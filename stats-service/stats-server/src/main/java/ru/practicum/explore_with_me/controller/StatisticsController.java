package ru.practicum.explore_with_me.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.stats.GetResponse;
import ru.practicum.dto.stats.HitRequest;
import ru.practicum.feign.StatsClient;
import ru.practicum.explore_with_me.service.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticsController implements StatsClient {
    final StatisticsService statisticsService;

    @Override
    @PostMapping("/hit")
    public void addHit(@RequestBody HitRequest hitRequest) {
        log.info("Post request to /hit");
        statisticsService.createHit(hitRequest);
    }

    @Override
    @GetMapping("/stats")
    public List<GetResponse> getStatistics(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Get request to /stats");
        return statisticsService.getStatistics(start, end, uris, unique);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Ошибка валидации: {}", ex.getMessage());
        return Map.of("error", "Неверные параметры запроса", "message", ex.getMessage());
    }
}