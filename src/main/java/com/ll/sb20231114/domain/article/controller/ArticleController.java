package com.ll.sb20231114.domain.article.controller;

import com.ll.sb20231114.domain.article.entity.Article;
import com.ll.sb20231114.domain.article.service.ArticleService;
import com.ll.sb20231114.global.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final Rq rq;

    // write form
    @GetMapping("/article/write")
    String showWrite() {
        return "/article/write";
    }

    // write
    @PostMapping("/article/write")
    String write(@Valid ArticleController.ArticleWriteForm form) {
        Article article = articleService.write(rq.getMember(), form.getTitle(), form.getBody());
        return rq.redirect("/article/list", "%d번 게시물이 생성되었습니다.".formatted(article.getId()));
    }

    // list
    @GetMapping("/article/list")
    String showList(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "/article/list";
    }

    // article detail
    @GetMapping("/article/detail/{id}")
    String showDetail(@PathVariable("id") Long id, Model model) {
        Optional<Article> findOne = articleService.findById(id);
        Article article = findOne.get();
        model.addAttribute("article", article);
        return "/article/detail";
    }

    // delete
    @GetMapping("/article/delete/{id}")
    String articleDel(@PathVariable Long id) {
        Optional<Article> findOne = articleService.findById(id);
        Article article = findOne.get();

        if (article == null) {
            throw new RuntimeException("None article");
        }

        if (!articleService.canDelete(rq.getMember(), article)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        articleService.delete(article);
        return rq.redirect("/article/list", "%d번 게시물이 삭제되었습니다.".formatted(id));
    }

    // modify form
    @GetMapping("/article/modify/{id}")
    String modifyForm(@PathVariable Long id, Model model) {
        Optional<Article> findOne = articleService.findById(id);
        Article article = findOne.get();

        if (article == null) {
            throw new RuntimeException("None article");
        }

        if (!articleService.canModify(rq.getMember(), article)) {
            throw new RuntimeException("변경 권한이 없습니다.");
        }

        model.addAttribute("article", article);
        return "/article/modify";
    }

    // modify
    @PostMapping("/article/modify")
    String modify(@Valid ArticleController.MemberModifyForm form) {
        articleService.modify(form.id, form.title, form.body);
        return rq.redirect("/article/detail/%d".formatted(form.getId()), "게시물이 수정되었습니다.");
    }

    @Data
    public static class ArticleWriteForm {
        @NotBlank(message = "title is not null")
        @NotNull
        private String title;

        @NotBlank(message = "body is not null")
        @NotNull
        private String body;
    }

    @Data
    public static class MemberModifyForm {
        private Long id;

        @NotBlank(message = "title is not null")
        @NotNull
        private String title;

        @NotBlank(message = "body is not null")
        @NotNull
        private String body;
    }
}