package com.ll.sb20231114.domain.article.service;

import com.ll.sb20231114.domain.article.entity.Article;
import com.ll.sb20231114.domain.article.repository.ArticleRepository;
import com.ll.sb20231114.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // 저는 단 한번만 생성되고, 그 이후에는 재사용되는 객체입니다.
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article write(Member author, String title, String body) {
        Article article = new Article(author, title, body);
        articleRepository.save(article);

        return article;
    }

    public Article findLastArticle() {
        return articleRepository.findLastArticle();
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public void delete(Long id) {
        articleRepository.delete(id);
    }

    public void modify(Long id, String title, String body) {
        Optional<Article> findOne = findById(id);
        Article article = findOne.get();

        article.articleUpdate(title, body);
    }
}