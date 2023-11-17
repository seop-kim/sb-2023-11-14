package com.ll.sb20231114.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
}