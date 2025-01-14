package org.wildcodeschool.myBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myBlog.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
