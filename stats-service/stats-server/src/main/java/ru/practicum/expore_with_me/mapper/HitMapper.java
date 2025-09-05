package ru.practicum.expore_with_me.mapper;

import dto.HitRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.expore_with_me.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {
    @Mapping(target = "id", ignore = true)
    Hit requestToHit(HitRequest hitRequest);
}
