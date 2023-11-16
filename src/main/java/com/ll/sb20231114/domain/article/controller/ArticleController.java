package com.ll.sb20231114.domain.article.controller;

import com.ll.sb20231114.domain.article.entity.Article;
import com.ll.sb20231114.domain.article.service.ArticleService;
import com.ll.sb20231114.global.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final Rq rq;

    // write form
    @GetMapping("/article/write")
    String showWrite() {
        return "article/write";
    }

    // write
    @PostMapping("/article/write")
    String write(@Valid WriteForm form) {
        Article article = articleService.write(form.getTitle(), form.getBody());

        String msg = "id %d, article created".formatted(article.getId());

        return "redirect:/article/list?msg=" + msg;
    }

    // list
    @GetMapping("/article/list")
    String showList(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "/article/list";
    }

    @GetMapping("/article/detail/{id}")
    String showDetail(@PathVariable("id") Long id, Model model) {
        Optional<Article> findOne = articleService.findById(id);
        Article article = findOne.get();
        model.addAttribute("article", article);
        return "/article/detail";
    }


    @GetMapping("/article/getLastArticle")
    @ResponseBody
    Article getLastArticle() {
        return articleService.findLastArticle();
    }

    @GetMapping("/article/getArticles")
    @ResponseBody
    List<Article> getArticles() {
        return articleService.findAll();
    }

    @GetMapping("/article/articleServicePointer")
    @ResponseBody
    ArticleService articleServicePointer() {
        return articleService;
    }

    @GetMapping("/article/httpServletRequestPointer")
    @ResponseBody
    String httpServletRequestPointer(HttpServletRequest req) {
        return req.toString();
    }

    @GetMapping("/article/httpServletResponsePointer")
    @ResponseBody
    String httpServletRequestPointer(HttpServletResponse resq) {
        return resq.toString();
    }

    @GetMapping("/article/rqPointer")
    @ResponseBody
    String rqPointer(Rq rq) {
        return rq.toString();
    }

    @GetMapping("/article/rqTest")
    String showRqTest(Model model) {
        model.addAttribute("rq", rq.toString());
        return "/article/rqTest";
    }

    @Data
    public static class WriteForm { // inner class

        @NotBlank(message = "title is not null")
        @NotNull
        private String title;
        @NotBlank(message = "body is not null")
        @NotNull
        private String body;

    }
}