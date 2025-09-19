package ru.practicum.event.controller;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.GetAllEventsPublicParams;
import ru.practicum.dto.event.enums.SortType;
import ru.practicum.event.dto.EventRecommendationDto;
import ru.practicum.event.service.EventService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicEventController {
    final EventService eventService;

    private static final String X_EWM_USER_ID_HEADER = "X-EWM-USER-ID";

    @GetMapping
    public Collection<EventShortDto> getAllEventsPublic(
            @RequestParam(required = false) @Size(min = 1, max = 7000, message = "Description should be between 1 and 7000 characters long") String text,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) SortType sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get all public events");
        return eventService.getAllEventsPublic(GetAllEventsPublicParams.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .build());
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdPublic(@PathVariable Long eventId, @RequestHeader(X_EWM_USER_ID_HEADER) long userId) {
        log.info("Get public event with id: {}", eventId);
        return eventService.getEventByIdPublic(eventId, userId);
    }

    @GetMapping("/recommendations")
    public List<EventRecommendationDto> getRecommendations(@RequestHeader(X_EWM_USER_ID_HEADER) long userId,
                                                           @RequestParam(defaultValue = "10") int maxResults) {
        log.info("Get recommendations for user {}", userId);
        return eventService.getRecommendationsForUser(userId, maxResults);
    }

    @PutMapping("/{eventId}/like")
    public void likeEvent(@PathVariable Long eventId,
                          @RequestHeader(X_EWM_USER_ID_HEADER) long userId) {
        log.info("User {} likes event {}", userId, eventId);
        eventService.addLike(userId, eventId);
    }
}