package com.ll.sb20231114.domain.home.controller;

import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final Rq rq;
    @GetMapping("/")
    public String goToArticleList(String msg) {
        return rq.redirect("/article/list", msg);
    }


    @GetMapping("/home/session")
    @ResponseBody
    public Map<String, Object> showSession(HttpSession session) {
        return Collections.list(session.getAttributeNames()).stream()
                .collect(
                        Collectors.toMap(
                                key -> key,
                                key -> session.getAttribute(key)
                        )
                );
    }
}