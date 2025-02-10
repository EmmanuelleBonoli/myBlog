package org.wildcodeschool.myBlog.dto;

import jakarta.validation.constraints.*;
import org.wildcodeschool.myBlog.model.Article;
import org.wildcodeschool.myBlog.model.Image;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleDTO(
        Long id,

        @NotBlank(message = "Le titre ne doit pas être vide")
        @Size(min = 2, max = 50, message = "Le titre doit contenir entre 2 et 50 caractères")
        String title,

        @NotBlank(message = "Le contenu ne doit pas être vide")
        @Size(min = 10, message = "Le contenu doit contenir au moins 10 caractères")
        String content,
        LocalDateTime updatedAt,

        @NotBlank(message = "La catégorie ne doit pas être vide")
        @Size(min = 2, max = 50, message = "La catégorie doit contenir entre 2 et 50 caractères")
        String categoryName,

        @NotEmpty(message = "La liste des images ne doit pas être vide")
        List<String> imageUrls,

        @NotEmpty(message = "La liste des auteurs ne doit pas être vide")
        List<AuthorDTO> Authors
) {

    public static ArticleDTO mapFromEntity(Article article) {
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getUpdatedAt(),
                article.getCategory() != null ? article.getCategory().getName() : null,
                article.getImages() != null ? article.getImages().stream().map(Image::getUrl).toList() : null,
                article.getArticleAuthors() != null ? article.getArticleAuthors().stream().map((i) -> AuthorDTO.mapFromEntity(i.getAuthor())).toList() : null
        );
    }
}