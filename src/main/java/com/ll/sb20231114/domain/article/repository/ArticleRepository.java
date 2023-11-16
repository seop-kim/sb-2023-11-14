package com.ll.sb20231114.domain.article.repository;

import com.ll.sb20231114.domain.article.entity.Article;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    private final List<Article> articles = new ArrayList<>(){{
        add(new Article(1L,"title1", "content1"));
        add(new Article(2L,"title2", "content2"));
        add(new Article(3L,"title3", "content3"));
    }};

    public Article save(Article article) {
        if (article.getId() == null) {
            article.setId(articles.size() + 1L);
        }

        articles.add(article);

        return article;
    }

    public Article findLastArticle() {
        return articles.getLast();
    }

    public List<Article> findAll() {
        return articles;
    }
}