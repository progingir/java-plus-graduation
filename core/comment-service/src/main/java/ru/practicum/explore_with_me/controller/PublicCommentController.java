package ru.practicum.explore_with_me.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.explore_with_me.service.CommentService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicCommentController {
    final CommentService commentService;

    @GetMapping("/events/{eventId}/comments")
    public Collection<CommentDto> getAllCommentsByEvent(@PathVariable Long eventId,
                                                        @RequestParam(defaultValue = "0") Integer from,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        return commentService.getAllCommentsByEvent(eventId, from, size);
    }

    @GetMapping("/comments/{commentId}")
    public CommentDto getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }
}