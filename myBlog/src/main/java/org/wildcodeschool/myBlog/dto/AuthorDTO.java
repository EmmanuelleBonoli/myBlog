package org.wildcodeschool.myBlog.dto;

import jakarta.validation.constraints.*;
import org.wildcodeschool.myBlog.model.Author;

public record AuthorDTO(
        @NotNull(message = "L'ID de l'auteur ne doit pas être nul")
        @Positive(message = "L'ID de l'auteur doit être un nombre positif")
        Long id,
        String firstname,
        String lastname
) {

    public static AuthorDTO mapFromEntity(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getFirstname(),
                author.getLastname()
        );
    }
}
