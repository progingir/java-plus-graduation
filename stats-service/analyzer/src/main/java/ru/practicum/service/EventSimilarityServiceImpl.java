package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.entity.EventSimilarity;
import ru.practicum.repository.EventSimilarityRepository;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSimilarityServiceImpl implements EventSimilarityService {

    private final EventSimilarityRepository eventSimilarityRepository;

    @Override
    public void updateEventSimilarity(EventSimilarityAvro eventSimilarityAvro) {
        long eventA = eventSimilarityAvro.getEventA();
        long eventB = eventSimilarityAvro.getEventB();
        float score = eventSimilarityAvro.getScore();
        Instant ts = eventSimilarityAvro.getTimestamp();

        EventSimilarity existing = findPair(eventA, eventB);
        if (existing == null) {
            existing = new EventSimilarity();
            existing.setEventA(eventA);
            existing.setEventB(eventB);
            existing.setScore(score);
            existing.setTimestamp(ts);
            eventSimilarityRepository.save(existing);
        } else {
            existing.setScore(score);
            existing.setTimestamp(ts);
            eventSimilarityRepository.save(existing);
        }
    }

    private EventSimilarity findPair(long eventA, long eventB) {
        return eventSimilarityRepository.findByEventAOrEventB(eventA, eventB)
                .stream()
                .filter(e -> (e.getEventA().equals(eventA) && e.getEventB().equals(eventB))
                        || (e.getEventA().equals(eventB) && e.getEventB().equals(eventA)))
                .findFirst()
                .orElse(null);
    }
}