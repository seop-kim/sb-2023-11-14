package com.ll.sb20231114.domain.article.repository;

import com.ll.sb20231114.domain.article.entity.Article;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findFirstByOrderByIdDesc();
}