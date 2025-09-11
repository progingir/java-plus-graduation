package ru.practicum.category.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.exception.NotFoundException;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryServiceImpl implements CategoryService {
    final CategoryMapper categoryMapper;
    final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto dto) {
        Category category = categoryMapper.toCategory(dto);
        CategoryDto categoryDto = categoryMapper.toDto(categoryRepository.save(category));
        log.info("Category with id = {} was created", categoryDto.getId());
        return categoryDto;
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        log.info("Get category with id = {}", categoryId);
        return categoryMapper.toDto(findCategoryById(categoryId));
    }

    @Override
    public Collection<CategoryDto> getCategories(int from, int size) {
        int pageNumber = from / size;
        Pageable pageable = PageRequest.of(pageNumber, size);

        Page<Category> page = categoryRepository.findAll(pageable);

        log.info("Get users with {from, size} = ({}, {})", from, size);
        return page.getContent().stream().map(categoryMapper::toDto).toList();
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto dto, Long categoryId) {
        Category oldCategory = findCategoryById(categoryId);
        oldCategory.setName(dto.getName());
        CategoryDto categoryDto = categoryMapper.toDto(categoryRepository.save(oldCategory));
        log.info("Category with id = {} was updated", categoryId);
        return categoryDto;
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        "Category with id = %d not found"
                                .formatted(categoryId)));

        categoryRepository.delete(category);
        log.info("Category with id = {} was deleted", categoryId);
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException(String.format("Category with id = %d not found", categoryId)));
    }
}