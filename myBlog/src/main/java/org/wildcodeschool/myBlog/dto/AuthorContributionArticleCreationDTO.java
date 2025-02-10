package org.wildcodeschool.myBlog.dto;

import jakarta.validation.constraints.*;

public record AuthorContributionArticleCreationDTO(
        @NotNull(message = "L'ID de l'auteur ne doit pas être nul")
        @Positive(message = "L'ID de l'auteur doit être un nombre positif")
        Long authorId,

        @NotBlank(message = "La contribution de l'auteur ne doit pas être vide")
        String contribution
) {
}
