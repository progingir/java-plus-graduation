package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.entity.UserAction;
import ru.practicum.repository.UserActionRepository;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionServiceImpl implements UserActionService {

    private final UserActionRepository userActionRepository;

    @Override
    public void updateUserAction(UserActionAvro userActionAvro) {
        long userId = userActionAvro.getUserId();
        long eventId = userActionAvro.getEventId();
        int newWeight = convertWeight(userActionAvro.getActionType());
        long ts = userActionAvro.getTimestamp();

        Instant interactionTime = Instant.ofEpochMilli(ts);

        UserAction userAction = userActionRepository.findByUserIdAndEventId(userId, eventId);
        if (userAction == null) {
            userAction = new UserAction();
            userAction.setUserId(userId);
            userAction.setEventId(eventId);
            userAction.setMaxWeight((double) newWeight);
            userAction.setLastInteraction(interactionTime);
            userActionRepository.save(userAction);
            return;
        }

        if (newWeight > userAction.getMaxWeight()) {
            userAction.setMaxWeight((double) newWeight);
        }

        if (interactionTime.isAfter(userAction.getLastInteraction())) {
            userAction.setLastInteraction(interactionTime);
        }
        userActionRepository.save(userAction);
    }

    private int convertWeight(ActionTypeAvro actionType) {
        return switch (actionType) {
            case REGISTER -> 2;
            case LIKE -> 3;
            default -> 1;
        };
    }
}