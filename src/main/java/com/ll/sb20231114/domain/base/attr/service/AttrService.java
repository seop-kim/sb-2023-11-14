package com.ll.sb20231114.domain.base.attr.service;

import com.ll.sb20231114.domain.base.attr.entity.Attr;
import com.ll.sb20231114.domain.base.attr.repository.AttrRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // DB와 연동된 service는 무조건 붙인다.
public class AttrService {

    private final AttrRepository attrRepository;

    @Transactional
    public void set(String name, Long val) {
        set(name, String.valueOf(val));
    }

    @Transactional
    public void set(String name, boolean val) {
        set(name, String.valueOf(val));
    }

    @Transactional
    public void set(String name, String val) {
        Optional<Attr> opAttr = attrRepository.findByName(name);

        if (opAttr.isPresent()) {
            opAttr.get().setVal(val);
            return;
        }

        attrRepository.save(
                Attr.builder()
                .name(name)
                .val(val)
                .build()
        );
    }

    public Long getAsLong(String name, Long defaultValue) {
        String val = get(name);

        if (val == null) {
            return defaultValue;
        }

        try {
            return Long.parseLong(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getAsBool(String name, boolean defaultValue) {
        String val = get(name);

        if (val == null) {
            return defaultValue;
        }

        return switch (val) {
            case "true" -> true;
            case "false" -> false;
            default -> defaultValue;
        };
    }

    public String getAsString(String name, String defaultValue) {
        String val = get(name);

        if (val == null) {
            return defaultValue;
        }

        try {
            return val;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String get(String name) {
        return attrRepository
                .findByName(name)
                .map(Attr::getVal)
                .orElse(null);
    }
}
