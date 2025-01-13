package org.wildcodeschool.myBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myBlog.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}