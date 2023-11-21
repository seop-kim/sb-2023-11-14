package com.ll.sb20231114.domain.member.controller;

import com.ll.sb20231114.domain.member.entity.Member;
import com.ll.sb20231114.domain.member.service.MemberService;
import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    // join form
    @GetMapping("/member/join")
    String showWrite() {
        return "/member/join";
    }

    // join
    @PostMapping("/member/join")
    String join(@Valid MemberJoinForm form) {
        Member member = memberService.join(form.getUsername(), form.getPassword());
        return rq.redirect("/article/list", "회원가입이 완료되었습니다.");
    }

    @GetMapping("/member/login")
    String showLogin() {
        return "/member/login";
    }

    @PostMapping("/member/login")
    String login(@Valid MemberJoinForm form, HttpServletRequest req, HttpServletResponse resp) {
        Optional<Member> opFindMember = memberService.login(form.username, form.password);

        if (opFindMember.isEmpty()) {
            throw new IllegalArgumentException("등록된 회원이 없습니다.");
        }

        Member findMember = opFindMember.get();

        if (!findMember.getPassword().equals(form.password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        rq.setSessionAttr("loginedMemberId", findMember.getId());

        return rq.redirect("/article/list", "로그인이 완료되었습니다.");
    }

    @GetMapping("/member/me")
    String me() {
        return "/";
    }

    @GetMapping("/member/logout")
    String logout() {
        rq.removeSessionAttr("loginedMemberId");

        return rq.redirect("/article/list", "로그아웃 되었습니다.");
    }

    @Data
    public static class MemberJoinForm {

        @NotBlank(message = "username is not null")
        @NotNull
        private String username;

        @NotBlank(message = "password is not null")
        @NotNull
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