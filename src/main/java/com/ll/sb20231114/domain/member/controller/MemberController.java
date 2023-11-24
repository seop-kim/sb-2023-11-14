package com.ll.sb20231114.domain.member.controller;

import com.ll.sb20231114.domain.member.service.MemberService;
import com.ll.sb20231114.global.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    // join form
    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/join")
    String showJoin() {
        return "/member/join";
    }

    // join
    @PreAuthorize("isAnonymous()")
    @PostMapping("/member/join")
    String join(@Valid MemberJoinForm form) {
        memberService.join(form.getUsername(), form.getPassword());
        return rq.redirect("/article/list", "회원가입이 완료되었습니다.");
    }
    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/login")
    String showLogin() {
        return "/member/login";
    }

    @GetMapping("/member/me")
    String me() {
        return "redirect:/";
    }


    @Data
    public static class MemberJoinForm {

        @NotBlank
        private String username;

        @NotBlank
        private String password;
    }

    @Data
    public static class MemberModifyForm {
        private Long id;

        @NotBlank(message = "username is not null")
        @NotNull
        private String username;

        @NotBlank(message = "password is not null")
        @NotNull
        private String password;
    }
}