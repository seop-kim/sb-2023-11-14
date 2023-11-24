package com.ll.sb20231114.domain.article.service;

import com.ll.sb20231114.domain.article.entity.Article;
import com.ll.sb20231114.domain.article.repository.ArticleRepository;
import com.ll.sb20231114.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 저는 단 한번만 생성되고, 그 이후에는 재사용되는 객체입니다.
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public Article write(Member author, String title, String body) {
        Article article = new Article(author, title, body);
        articleRepository.save(article);

        return article;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Transactional
    public void modify(Article article, String title, String body) {
        article.articleUpdate(title, body);
    }

    @Transactional
    public boolean canModify(Member actor, Article article) {
        if (actor == null) {
            return false;
        }
        return article.getAuthor().equals(actor);
    }

    @Transactional
    public boolean canDelete(Member actor, Article article) {
        if (actor == null) {
            return false;
        }

        if (actor.isAdmin()) {
            return true;
        }

        return article.getAuthor().equals(actor);
    }

    public Optional<Article> findLatest() {
        return articleRepository.findFirstByOrderByIdDesc();
    }
}