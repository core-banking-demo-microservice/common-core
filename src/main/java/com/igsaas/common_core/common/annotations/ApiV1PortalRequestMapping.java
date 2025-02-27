package com.igsaas.common_core.common.annotations;

import com.igsaas.common_core.common.ApiUrls;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@RequestMapping(ApiUrls.BASE_URL)
public @interface ApiV1PortalRequestMapping {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
