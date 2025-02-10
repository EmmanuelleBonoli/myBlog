package org.wildcodeschool.myBlog.dto;

import jakarta.validation.constraints.NotBlank;
import org.wildcodeschool.myBlog.model.ArticleAuthor;

public record ArticleAuthorDTO(

        @NotBlank(message = "La contribution de l'auteur ne doit pas être vide")
        String contribution
) {

    public static ArticleAuthorDTO mapFromEntity(ArticleAuthor articleAuthor) {
        return new ArticleAuthorDTO(
                articleAuthor.getContribution()
        );
    }
}
