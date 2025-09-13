package ru.practicum.compilations.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.service.CompilationService;
import ru.practicum.dto.compilations.CompilationResponse;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicCompilationController {
    final CompilationService compilationService;

    @GetMapping("/{compId}")
    public CompilationResponse getCompilationById(@PathVariable(name = "compId") Long compId) {
        return compilationService.getCompilationById(compId);
    }

    @GetMapping
    public Collection<CompilationResponse> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                           @RequestParam(defaultValue = "0") Integer from,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return compilationService.getCompilations(pinned, from, size);
    }
}