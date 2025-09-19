package ru.practicum.event.service;

import ru.practicum.dto.event.*;
import ru.practicum.event.dto.EventRecommendationDto;

import java.util.Collection;
import java.util.List;

public interface EventService {
    Collection<EventShortDto> getAllEvents(Long userId, Integer from, Integer size);

    Collection<EventFullDto> getAllEventsAdmin(GetAllEventsAdminParams params);

    Collection<EventShortDto> getAllEventsPublic(GetAllEventsPublicParams params);

    EventFullDto patchEventById(Long eventId, AdminPatchEventDto adminPatchEventDto);

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventById(Long userId, Long eventId);

    EventFullDto getEventByIdFeign(Long eventId);

    EventFullDto getEventByIdPublic(Long eventId, Long userId);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateRequest);

    EventFullDto getEventByUserFeign(Long userId, Long eventId);

    void addLike(long userId, long eventId);

    List<EventRecommendationDto> getRecommendationsForUser(long userId, int maxResults);
}