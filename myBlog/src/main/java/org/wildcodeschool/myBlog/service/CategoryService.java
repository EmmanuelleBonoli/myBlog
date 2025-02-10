package org.wildcodeschool.myBlog.service;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myBlog.dto.CategoryDTO;
import org.wildcodeschool.myBlog.exception.ResourceNotFoundException;
import org.wildcodeschool.myBlog.model.Category;
import org.wildcodeschool.myBlog.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryDTO::mapFromEntity).collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La catégorie avec l'id " + id + " n'a pas été trouvé"));
        return CategoryDTO.mapFromEntity(category);
    }

    public CategoryDTO createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return CategoryDTO.mapFromEntity(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, Category categoryName) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La catégorie avec l'id " + id + " n'a pas été trouvé"));

        category.setName(categoryName.getName());

        Category updatedCategory = categoryRepository.save(category);
        return CategoryDTO.mapFromEntity(updatedCategory);
    }

    public boolean deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La catégorie avec l'id " + id + " n'a pas été trouvé"));

        categoryRepository.delete(category);
        return true;
    }
}
