package ru.practicum.explore_with_me.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.UpdateCommentDto;
import ru.practicum.explore_with_me.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments/{commentId}")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminCommentController {
    final CommentService commentService;

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
    }

    @PatchMapping
    public CommentDto updateComment(@PathVariable Long commentId,
                                    @Valid @RequestBody UpdateCommentDto updateCommentDto) {
        return commentService.updateCommentByIdAndAuthorId(commentId, null, updateCommentDto);
    }
}