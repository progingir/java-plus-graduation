package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.repository.CommentRepository;

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
