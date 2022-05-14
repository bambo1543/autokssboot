package de.promove.autokss.web.util;

import de.promove.autokss.web.scope.ViewScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ViewScope
public @interface View {

    @AliasFor(annotation = Controller.class)
    String value() default "";

    @AliasFor(annotation = ViewScope.class)
    ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;

}
