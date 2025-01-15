package org.wildcodeschool.myBlog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myBlog.model.ArticleAuthor;

public interface ArticleAuthorRepository  extends JpaRepository<ArticleAuthor, Long> {
}
