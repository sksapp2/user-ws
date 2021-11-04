package com.infc.ms.user.common;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class ValidatorHandler {

    private final Validator validator;

    public <T> void validate(T o) {
        Set<ConstraintViolation<T>> validate = validator.validate(o);
        if(! validate.isEmpty()) {
          String message=  validate.stream().map(m->m.getMessage()).collect(Collectors.joining(","));
            ConstraintViolation<T> violation = validate.stream().iterator().next();
            log.info("Validation error : {}",message);
            throw new ServerWebInputException(message);
        }
    }
}
