package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.request.RequestDto;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Request;
import ru.practicum.repository.RequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveRequestTransactional {

    final RequestRepository requestRepository;
    final RequestMapper requestMapper;

    @Transactional
    public RequestDto save(Request request) {
        return requestMapper.toDto(requestRepository.save(request));
    }

    @Transactional
    public void saveAll(List<Request> requests) {
        requestRepository.saveAll(requests);
    }
}