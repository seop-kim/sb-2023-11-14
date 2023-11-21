package com.ll.sb20231114.domain.article.repository;

import com.ll.sb20231114.domain.article.entity.Article;
import com.ll.sb20231114.domain.member.entity.Member;
import com.ll.sb20231114.domain.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    private final MemberRepository memberRepository;
    private final List<Article> articles = new ArrayList<>();

    @PostConstruct // 생성자가 생성된 뒤에 실행된다.
    void init() {
        Member member1 = memberRepository.findById(1L).get();
        Member member2 = memberRepository.findById(2L).get();

        save(new Article(member1, "test1", "test1"));
        save(new Article(member2, "test2", "test2"));
        save(new Article(member2, "test3", "test3"));
    }

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

    public void delete(Long id) {
        articles.removeIf(article -> article.getId() == id);
    }
}