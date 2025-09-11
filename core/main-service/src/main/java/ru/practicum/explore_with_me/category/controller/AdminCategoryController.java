package ru.practicum.explore_with_me.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.service.CategoryService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AdminCategoryController {
    final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto request) {
        return categoryService.createCategory(request);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto request,
                                      @PathVariable(name = "catId") Long categoryId) {
        return categoryService.updateCategory(request, categoryId);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(name = "catId") Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
    }
}