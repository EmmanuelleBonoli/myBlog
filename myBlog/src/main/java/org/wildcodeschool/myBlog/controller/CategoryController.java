package org.wildcodeschool.myBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wildcodeschool.myBlog.dto.ArticleDTO;
import org.wildcodeschool.myBlog.dto.AuthorDTO;
import org.wildcodeschool.myBlog.dto.CategoryDTO;
import org.wildcodeschool.myBlog.model.Category;
import org.wildcodeschool.myBlog.model.Image;
import org.wildcodeschool.myBlog.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<CategoryDTO> categoryDTOs = categories.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(category));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody Category categoryName) {

        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setName(categoryName.getName());

        Category updatedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(convertToDTO(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {

        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        categoryRepository.delete(category);
        return ResponseEntity.noContent().build();
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        if (category.getArticles() != null) {
            categoryDTO.setArticles(category.getArticles().stream().map(article -> {
                ArticleDTO articleDTO = new ArticleDTO();
                articleDTO.setId(article.getId());
                articleDTO.setTitle(article.getTitle());
                articleDTO.setContent(article.getContent());
                articleDTO.setUpdatedAt(article.getUpdatedAt());
                articleDTO.setCategoryName(article.getCategory().getName());
                articleDTO.setImageUrls(article.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
                articleDTO.setAuthors(article.getArticleAuthors().stream()
                        .filter(articleAuthor -> articleAuthor.getAuthor() != null)
                        .map(articleAuthor -> {
                            AuthorDTO authorDTO = new AuthorDTO();
                            authorDTO.setId(articleAuthor.getAuthor().getId());
                            authorDTO.setFirstname(articleAuthor.getAuthor().getFirstname());
                            authorDTO.setLastname(articleAuthor.getAuthor().getLastname());
                            return authorDTO;
                        })
                        .collect(Collectors.toList()));
                return articleDTO;
            }).collect(Collectors.toList()));
        }
        return categoryDTO;
    }

}
