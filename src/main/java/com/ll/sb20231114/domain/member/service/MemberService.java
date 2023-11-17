package com.ll.sb20231114.domain.member.service;

import com.ll.sb20231114.domain.member.entity.Member;
import com.ll.sb20231114.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(String username, String password) {
        Member member = new Member(username, password);

        memberRepository.save(member);

        return member;
    }

    public Member findLastMember() {
        return memberRepository.findLastMember();
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public void delete(Long id) {
        memberRepository.delete(id);
    }

    public void modify(Long id, String username, String password) {
        Optional<Member> findOne = findById(id);
        Member member = findOne.get();

        member.memberUpdate(username, password);
    }

    public Optional<Member> login(String username, String password) {
        return memberRepository.findByUserName(username);
    }
}