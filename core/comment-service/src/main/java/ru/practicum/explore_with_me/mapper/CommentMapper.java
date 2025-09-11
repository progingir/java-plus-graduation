package ru.practicum.explore_with_me.mapper;

import org.mapstruct.*;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.UpdateCommentDto;
import ru.practicum.explore_with_me.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorId", source = "userId")
    @Mapping(target = "publishedOn", source = "updateCommentDto.publishedOn")
    Comment toComment(UpdateCommentDto updateCommentDto, Long eventId, Long userId);

    @Mapping(target = "author.id", source = "authorId")
    @Mapping(target = "event.id",  source = "eventId")
    CommentDto toDto(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedOn", source = "updateCommentDto.publishedOn")
    @Mapping(target = "authorId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateComment(UpdateCommentDto updateCommentDto, Long eventId, @MappingTarget Comment comment);
}
