package org.wildcodeschool.myBlog.dto;

import org.hibernate.validator.constraints.URL;
import org.wildcodeschool.myBlog.model.Image;

import java.util.List;

public record ImageDTO(
        Long id,

        @URL(message = "L'URL de l'image doit être valide")
        String url,
        List<Long> articleIds
) {

    public static ImageDTO mapFromEntity(Image image) {
        return new ImageDTO(
                image.getId(),
                image.getUrl(),
                image.getArticles().stream().map((article) -> article.getId()).toList()
        );
    }

}
