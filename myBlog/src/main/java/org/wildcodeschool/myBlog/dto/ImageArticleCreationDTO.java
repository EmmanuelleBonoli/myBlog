package org.wildcodeschool.myBlog.dto;

import org.hibernate.validator.constraints.URL;
import org.wildcodeschool.myBlog.model.Image;

import java.time.LocalDateTime;

public record ImageArticleCreationDTO(
        Long id,
        @URL(message = "L'URL de l'image doit être valide")
        String url
) {

    public static Image convertToEntity(ImageArticleCreationDTO imageArticleCreationDTO) {
        Image image = new Image();
        image.setUrl(imageArticleCreationDTO.url());
        return image;


    }
}
