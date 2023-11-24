package com.ll.sb20231114.domain.member.repository;

import com.ll.sb20231114.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String name);
    Optional<Member> findFirstByOrderByIdDesc();
}
