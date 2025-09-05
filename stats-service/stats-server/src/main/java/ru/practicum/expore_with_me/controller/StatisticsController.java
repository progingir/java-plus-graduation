package ru.practicum.expore_with_me.controller;

import dto.GetResponse;
import dto.HitRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.client.StatsClient;
import ru.practicum.expore_with_me.service.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StatisticsController implements StatsClient {
    final StatisticsService statisticsService;

    @Override
    public void addHit(HitRequest hitRequest) {
        log.info("Post request to /hit");
        statisticsService.createHit(hitRequest);
    }

    @Override
    public List<GetResponse> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Get request to /stats");
        return statisticsService.getStatistics(start, end, uris, unique);
    }
}