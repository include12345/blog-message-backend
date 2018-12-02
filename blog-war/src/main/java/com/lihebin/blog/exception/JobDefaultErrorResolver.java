package com.lihebin.blog.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.ErrorData;
import com.googlecode.jsonrpc4j.ErrorResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Created by lihebin on 2018/10/19.
 */
public class JobDefaultErrorResolver implements ErrorResolver {
    private final Logger log= LoggerFactory.getLogger(getClass());

    @Override
    public JsonError resolveError(Throwable t, Method method, List<JsonNode> arguments) {
        log.error("resolveError:{},{},{}", t, method, arguments);
        if (t.getClass().equals(ConstraintViolationException.class)) {
            ConstraintViolationException exception = (ConstraintViolationException) t;
            Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
            final StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : violations) {
                stringBuilder.append(constraintViolation.getMessage());
            }
            String message = stringBuilder.toString();
            return new JsonError(400, message,
                    new ErrorData(exception.getClass().getName(), message));
        }
        ErrorData errorData = new ErrorData(t.getClass().getName(), t.getMessage());
        return new JsonError(1111, "未知错误", errorData);
    }
}
