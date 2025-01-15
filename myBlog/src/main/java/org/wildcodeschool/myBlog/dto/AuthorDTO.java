package org.wildcodeschool.myBlog.dto;

import org.wildcodeschool.myBlog.model.Author;

public record AuthorDTO(
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
