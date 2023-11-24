package com.ll.sb20231114.domain.home.controller;

import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/home/test")
    @ResponseBody
    public Map<String, Object> showTest1() {
        return new HashMap<>() {{
            put("msg", "test1");
        }};
    }

    @GetMapping("/home/test2")
    public String showTest2(Model model) {
        model.addAttribute("age", 20);
        return "home/home/test2";
    }
}