package com.ll.sb20231114.domain.member.service;

import com.ll.sb20231114.domain.member.entity.Member;
import com.ll.sb20231114.domain.member.repository.MemberRepository;
import com.ll.sb20231114.global.rsData.RsData;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RsData<Member> join(String username, String password) {
        if (findByUsername(username).isPresent()) {
            return new RsData<>("F-1", "이미 사용중인 아이디입니다.");
        }

        password = passwordEncoder.encode(password);
        Member member = new Member(username, password);

        memberRepository.save(member);

        return new RsData<>(
                "S-1",
                "%s님 환영합니다.".formatted(member.getUsername()),
                member
        );
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
//        memberRepository.delete(id);
    }

    @Transactional
    public void modify(Long id, String username, String password) {
        Optional<Member> findOne = findById(id);
        Member member = findOne.get();

        member.memberUpdate(username, password);
    }

    public Optional<Member> login(String username, String password) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findLatest() {
        return memberRepository.findFirstByOrderByIdDesc();
    }
}