package de.promove.autokss.web.scope;

import de.promove.autokss.configuration.JsfConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Scope(JsfConfiguration.VIEW_SCOPE)
public @interface ViewScope {
    @AliasFor(
            annotation = Scope.class
    )
    ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;
}