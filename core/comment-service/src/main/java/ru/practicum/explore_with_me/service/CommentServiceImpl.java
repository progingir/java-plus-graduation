package ru.practicum.explore_with_me.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.UpdateCommentDto;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.enums.EventState;
import ru.practicum.exception.EventIdIncorrectException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.PublicationException;
import ru.practicum.feign.EventClient;
import ru.practicum.feign.UserClient;
import ru.practicum.explore_with_me.mapper.CommentMapper;
import ru.practicum.explore_with_me.model.Comment;
import ru.practicum.explore_with_me.repository.CommentRepository;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentServiceImpl implements CommentService {

    final CommentRepository commentRepository;
    final CommentMapper commentMapper;
    final UserClient userClient;
    final EventClient eventClient;
    final SaveCommentTransactional saver;

    @Override
    @Transactional
    public CommentDto createComment(UpdateCommentDto updateCommentDto, Long userId) {
        userClient.getUserById(userId);
        EventFullDto event = findEventById(updateCommentDto.getEventId());

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new PublicationException("Event must be published");
        }

        Comment comment = commentMapper.toComment(updateCommentDto, event.getId(), userId);
        CommentDto response = saver.save(comment);
        log.info("Comment id = {} was created by user id = {}", response.getId(), response.getAuthor().getId());
        return response;
    }

    @Override
    @Transactional
    public void deleteCommentByIdAndAuthor(Long commentId, Long userId) {
        Comment comment = commentRepository
                .findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException(
                        "Comment with id = %d by author id = %d was not found"
                                .formatted(commentId, userId)));

        commentRepository.delete(comment);
        log.info("Comment with id = {} was deleted by user id = {}", commentId, userId);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new NotFoundException(
                        "Comment with id = %d was not found"
                                .formatted(commentId)));

        commentRepository.delete(comment);
        log.info("Comment with id={} was deleted by administrator", commentId);
    }

    @Override
    @Transactional
    public CommentDto updateCommentByIdAndAuthorId(Long commentId, Long userId, UpdateCommentDto updateCommentDto) {
        Comment oldComment;
        if (userId == null) {
            oldComment = commentRepository.findById(commentId).orElseThrow(() ->
                    new NotFoundException(String.format("Comment with id = %d was not found", commentId)));
        } else {
            oldComment = commentRepository.findByIdAndAuthorId(commentId, userId).orElseThrow(() ->
                    new NotFoundException(String.format("Comment with id=%d by author id = %d was not found", commentId, userId)));
        }

        if (!oldComment.getEventId().equals(updateCommentDto.getEventId())) {
            throw new EventIdIncorrectException("Event Id not correct");
        }

        commentMapper.updateComment(
                updateCommentDto,
                findEventById(updateCommentDto.getEventId()).getId(),
                oldComment);

        CommentDto response = saver.save(oldComment);
        if (userId == null) {
            log.info("Comment id = {} was updated by administrator", response.getId());
        } else {
            log.info("Comment id = {} was updated by user id = {}", response.getId(), response.getAuthor().getId());
        }
        return response;
    }

    @Override
    public Collection<CommentDto> getAllCommentsByUser(Long userId, Integer from, Integer size) {
        log.info("Get all comments for user id = {}", userId);
        return commentRepository.findAllByAuthorIdOrderByPublishedOnDesc(userId, createPageable(from, size))
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public Collection<CommentDto> getAllCommentsByEvent(Long eventId, Integer from, Integer size) {
        log.info("Get all comments for event id = {}", eventId);
        return commentRepository.findAllByEventIdOrderByPublishedOnDesc(eventId, createPageable(from, size))
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public Collection<CommentDto> getAllCommentsByUserAndEvent(Long userId, Long eventId, Integer from, Integer size) {
        log.info("Get all comments for event id = {} and user id = {}", eventId, userId);
        return commentRepository.findAllByAuthorIdAndEventIdOrderByPublishedOnDesc(userId, eventId, createPageable(from, size))
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        log.info("Get comment with id = {}", commentId);
        return commentMapper.toDto(commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id = %d was not found", commentId))));
    }

    private Pageable createPageable(Integer from, Integer size) {
        int pageNumber = from / size;
        return PageRequest.of(pageNumber, size);
    }

    private EventFullDto findEventById(Long eventId) {
        return eventClient.getEventByIdFeign(eventId);
    }
}