package com.ll.sb20231114.global.initData;

import com.ll.sb20231114.domain.article.service.ArticleService;
import com.ll.sb20231114.domain.member.entity.Member;
import com.ll.sb20231114.domain.member.service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!prod") // 운영모드가 아니면 이 코드를 실행해라
@Configuration
public class NotProd {
    @Bean
    public ApplicationRunner initNotProd(MemberService memberService, ArticleService articleService) {
        return args -> {
            Member admin = memberService.join("admin", "1234");
            Member member1 = memberService.join("user1", "1234");
            Member member2 = memberService.join("user2", "1234");

            articleService.write(admin, "test1", "test1");
            articleService.write(member1, "test2", "test2");
            articleService.write(member2, "test3", "test3");
            System.out.println("test data init");
        };
    }
}
