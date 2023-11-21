package com.ll.sb20231114.domain.article.repository;

import com.ll.sb20231114.domain.article.entity.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    private final List<Article> articles = new ArrayList<>();

    //@PostConstruct // 생성자가 생성된 뒤에 실행된다.
    //void init() {}

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


    public Optional<Article> findById(Long id) {
        return articles.stream()
                .filter(article -> article.getId() == id)
                .findFirst();
    }

    public void delete(Article article) {
        articles.remove(article);
    }
}