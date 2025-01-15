package org.wildcodeschool.myBlog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myBlog.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
