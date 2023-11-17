package com.ll.sb20231114.domain.member.controller;

import com.ll.sb20231114.domain.member.entity.Member;
import com.ll.sb20231114.domain.member.service.MemberService;
import com.ll.sb20231114.global.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    // write form
    @GetMapping("/member/join")
    String showWrite() {
        return "/member/join";
    }

    // write
    @PostMapping("/member/join")
    String join(@Valid MemberController.MemberWriteForm form) {
        Member member = memberService.join(form.getUsername(), form.getPassword());
        return rq.redirect("/article/list", "회원가입이 완료되었습니다.");
    }

    @Data
    public static class MemberWriteForm {

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