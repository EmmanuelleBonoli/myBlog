package org.wildcodeschool.myBlog.dto;

import org.wildcodeschool.myBlog.model.ArticleAuthor;

public record ArticleAuthorDTO(
        String contribution
) {

    public static ArticleAuthorDTO mapFromEntity(ArticleAuthor articleAuthor) {
        return new ArticleAuthorDTO(
                articleAuthor.getContribution()
        );
    }
}
