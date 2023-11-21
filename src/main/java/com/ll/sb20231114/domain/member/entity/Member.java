package com.ll.sb20231114.domain.member.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Member {
    private Long id;
    private String username;
    private String password;

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void memberUpdate(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isAdmin() {
        return username.equals("admin");
    }

    public List<String> getAuthorities() {
        if (isAdmin()) {
            return List.of("ROLE_ADMIN");
        }

        return List.of("ROLE_MEMBER");
    }
}