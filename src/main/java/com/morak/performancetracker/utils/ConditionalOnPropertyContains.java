package com.morak.performancetracker.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Map;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Conditional(ConditionalOnPropertyContains.PropertyContainsCondition.class)
public @interface ConditionalOnPropertyContains {

    String value() default "";

    String containsValue() default "";

    boolean matchIfEmpty() default false;

    class PropertyContainsCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> attrs = metadata.getAnnotationAttributes(ConditionalOnPropertyContains.class.getName());
            if (attrs == null) {
                return false;
            }
            String containsValue = (String) attrs.get("containsValue");
            boolean matchIfEmpty = (boolean) attrs.get("matchIfEmpty");
            String propertyName = (String) attrs.get("value");
            String value = context.getEnvironment().getProperty(propertyName);

            if (matchIfEmpty && !StringUtils.hasLength(value)) {
                return true;
            }
            return Arrays.asList(value.split(",[ ]?")).contains(containsValue);
        }
    }
}
