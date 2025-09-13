package ru.practicum.explore_with_me.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.explore_with_me.mapper.CommentMapper;
import ru.practicum.explore_with_me.model.Comment;
import ru.practicum.explore_with_me.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class SaveCommentTransactional {

    final CommentRepository commentRepository;
    final CommentMapper commentMapper;

    @Transactional
    public CommentDto save(Comment comment) {
        return commentMapper.toDto(commentRepository.save(comment));
    }
}
