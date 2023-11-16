package com.ll.sb20231114.domain.article.controller;

import com.ll.sb20231114.domain.article.entity.Article;
import com.ll.sb20231114.domain.article.service.ArticleService;
import com.ll.sb20231114.global.Rq;
import com.ll.sb20231114.global.rsData.RsData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Validated
public class ArticleController {
    private final ArticleService articleService;
    private final Rq rq;

    @GetMapping("/article/write")
    String showWrite() {
        return "article/write";
    }


    @Data
    public static class WriteForm {
        @NotBlank(message = "title is not null")
        @NotNull
        private String title;
        @NotBlank(message = "body is not null")
        @NotNull
        private String body;
    }

    @PostMapping("/article/write")
    @ResponseBody
    RsData write(
            WriteForm form) {

        // validate param
        //if (title == null || body == null || title.isEmpty() || body.isEmpty()) {
        //return new RsData<>(
        //  "F-1",
        //  "제목 혹은 내용을 입력해 주세요"
        //);
        //throw new IllegalArgumentException("제목 혹은 내용을 입력해 주세요");
        //}

        Article article = articleService.write(form.getTitle(), form.getBody());

        RsData<Article> rs = new RsData<>(
                "S-1",
                "%d번 게시물이 작성되었습니다.".formatted(article.getId()),
                article
        );

        return rs;
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
}