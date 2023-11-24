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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final Rq rq;

    // write form
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    String showWrite() {
        return "/article/write";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write2")
    String showWrite2() {
        return "/article/write2";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write2")
    String write2(@Valid ArticleCreateForm articleCreateForm) {
        Article article = articleService.write(rq.getMember(), articleCreateForm.getTitle(), articleCreateForm.getBody());
        return rq.redirect("/", "%d번 게시물 생성되었습니다.".formatted(article.getId()));
    }

    // write
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    String write(@Valid ArticleController.ArticleWriteForm form) {
        Article article = articleService.write(rq.getMember(), form.getTitle(), form.getBody());
        return rq.redirect("/", "%d번 게시물이 생성되었습니다.".formatted(article.getId()));
    }

    // list
    @GetMapping("/list")
    String showList(Model model) {
        List<Article> articles = articleService.findAll();

        model.addAttribute("articles", articles);

        return "/article/list";
    }

    // article detail
    @GetMapping("/detail/{id}")
    String showDetail(@PathVariable("id") Long id, Model model) {
        Optional<Article> findOne = articleService.findById(id);
        Article article = findOne.get();
        model.addAttribute("article", article);
        return "/article/detail";
    }

    // delete
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    String articleDel(@PathVariable("id") Long id) {
        Optional<Article> findOne = articleService.findById(id);
        Article article = findOne.get();

        if (!articleService.canDelete(rq.getMember(), article)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        articleService.delete(article);
        return rq.redirect("/", "%d번 게시물이 삭제되었습니다.".formatted(id));
    }

    // modify form
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    String modifyForm(@PathVariable("id") Long id, Model model) {
        Optional<Article> findOne = articleService.findById(id);
        Article article = findOne.get();

        if (!articleService.canModify(rq.getMember(), article)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        model.addAttribute("article", article);
        return "/article/modify";
    }

    // modify
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/modify/{id}")
    String modify(@PathVariable("id") Long id, @Valid MemberModifyForm modifyForm) {
        Article article = articleService.findById(id).get();
        if (!articleService.canModify(rq.getMember(), article)) throw new RuntimeException("수정권한이 없습니다.");
        articleService.modify(article, modifyForm.title, modifyForm.body);
        return rq.redirect("/", "%d번 게시물 수정되었습니다.".formatted(id));
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

    @Data
    public static class ArticleCreateForm {
        @NotBlank
        @NotBlank(message = "제목을 입력해주세요.")
        private String title;
        @NotBlank
        @NotBlank(message = "내용을 입력해주세요.")
        private String body;
    }
}