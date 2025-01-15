package org.wildcodeschool.myBlog.dto;

import org.wildcodeschool.myBlog.model.Image;

import java.util.List;

public record ImageDTO(
        Long id,
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
