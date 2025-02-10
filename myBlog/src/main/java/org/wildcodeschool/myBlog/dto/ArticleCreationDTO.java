package org.wildcodeschool.myBlog.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.wildcodeschool.myBlog.model.*;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleCreationDTO(
        @NotBlank(message = "Le titre ne doit pas être vide")
        @Size(min = 2, max = 50, message = "Le titre doit contenir entre 2 et 50 caractères")
        String title,

        @NotBlank(message = "Le contenu ne doit pas être vide")
        @Size(min = 10, message = "Le contenu doit contenir au moins 10 caractères")
        String content,

        @NotNull(message = "L'ID de la catégorie ne doit pas être nul")
        @Positive(message = "L'ID de la catégorie doit être un nombre positif")
        Long categoryId,

        @NotEmpty(message = "La liste des images ne doit pas être vide")
        List<@Valid ImageArticleCreationDTO> images,

        @NotEmpty(message = "La liste des auteurs ne doit pas être vide")
        List<@Valid AuthorContributionArticleCreationDTO> authors
) {

    public static Article convertToEntity(ArticleCreationDTO articleCreationDTO) {
        Article article = new Article();
        article.setTitle(articleCreationDTO.title());
        article.setContent(articleCreationDTO.content());
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        return article;


    }
}
