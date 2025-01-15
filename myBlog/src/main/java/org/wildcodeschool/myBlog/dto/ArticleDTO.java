package org.wildcodeschool.myBlog.dto;

import org.wildcodeschool.myBlog.model.Article;
import org.wildcodeschool.myBlog.model.ArticleAuthor;
import org.wildcodeschool.myBlog.model.Image;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public record ArticleDTO(
        Long id,
        String title,
        String content,
        LocalDateTime updatedAt,
        String categoryName,
        List<String> imageUrls,
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