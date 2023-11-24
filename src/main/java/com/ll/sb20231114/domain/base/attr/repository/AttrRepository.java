package com.ll.sb20231114.domain.base.attr.repository;

import com.ll.sb20231114.domain.base.attr.entity.Attr;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AttrRepository extends JpaRepository<Attr, Long> {
    Optional<Attr> findByName(String name);
}
