package com.ll.sb20231114.domain.member.repository;

import com.ll.sb20231114.domain.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final List<Member> members = new ArrayList<>();

    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(members.size() + 1L);
        }

        members.add(member);

        return member;
    }

    public Member findLastMember() {
        return members.getLast();
    }

    public List<Member> findAll() {
        return members;
    }


    public Optional<Member> findById(Long id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst();
    }
    // 메모리 저장을 하는  로그인을 1번

    public void delete(Long id) {
        members.removeIf(member -> member.getId() == id);
    }

    public Optional<Member> findByUserName(String username) {
        return members.stream()
                .filter(member -> member.getUsername().equals(username))
                .findFirst();
    }

    public Optional<Member> findLatest() {
        return Optional.ofNullable(
                members.isEmpty() ? null : members.getLast()
        );
    }
}
