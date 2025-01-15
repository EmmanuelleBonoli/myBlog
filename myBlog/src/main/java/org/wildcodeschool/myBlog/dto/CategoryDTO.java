package org.wildcodeschool.myBlog.dto;

import org.wildcodeschool.myBlog.model.Category;

import java.util.List;

public record CategoryDTO(
        Long id,
        String name,
        List<ArticleDTO> articles
) {

    public static CategoryDTO mapFromEntity(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getArticles().stream().map((i) -> ArticleDTO.mapFromEntity(i)).toList()
        );
    }

}
