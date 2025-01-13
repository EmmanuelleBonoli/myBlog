package org.wildcodeschool.myBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myBlog.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}
