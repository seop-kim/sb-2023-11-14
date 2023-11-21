package com.ll.sb20231114.global.rq;

import com.ll.sb20231114.domain.member.entity.Member;
import com.ll.sb20231114.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
@Getter
public class Rq {

    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;
    private Member member;

    public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
        this.req = req;
        this.resp = resp;
        this.memberService = memberService;
    }

    public String redirect(String path, String msg) {
        msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        return "redirect:" + path + "?msg=" + msg;
    }

    private Long getMemberId() {
        return Optional.ofNullable(req.getSession().getAttribute("loginedMemberId"))
                .map(_id -> (Long) _id)
                .orElse(0L);
    }

    public boolean isLogined() {
        return getMemberId() > 0;
    }

    public Member getMember() {
        if (!isLogined()) {
            return null;
        }

        if (member == null) {
            member = memberService.findById(getMemberId()).get();
        }

        return member;
    }

    public void setSessionAttr(String name, Object value) {
        req.getSession().setAttribute(name, value);
    }
    public void removeSessionAttr(String name) {
        req.getSession().removeAttribute(name);
    }

    public boolean isAdmin() {
        return getMember().isAdmin();
    }

    public <T> T getSessionAttr(String name) {
        return (T) req.getSession().getAttribute(name);
    }
}
