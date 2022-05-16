package de.promove.autokss.service.encryption;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Aspect
@Component
public class PasswordInterceptor {

    public static final String PASSWORD_KEY = "password";

    @Autowired
    private EncryptionHandler encryptionHandler;

    @Pointcut("@annotation(de.promove.autokss.service.encryption.Encrypt))")
    public void encrypt() {}

    @Pointcut("@annotation(de.promove.autokss.service.encryption.Decrypt))")
    public void decrypt() {}


    @Before("encrypt()")
    public void encrypt(@NotNull JoinPoint pjp) {
        Properties properties = (Properties) pjp.getArgs()[0];
        for (String key : properties.stringPropertyNames()) {
            if (key.contains(PASSWORD_KEY)) {
                if (StringUtils.hasText(properties.getProperty(key))) {
                    properties.put(key, encryptionHandler.encrypt(properties.getProperty(key)));
                } else {
                    properties.remove(key);
                }
            }
        }
    }

    @Around("decrypt()")
    public Object decrpyt(@NotNull ProceedingJoinPoint pjp) throws Throwable {
        Properties properties = (Properties) pjp.proceed();
        for (String key : properties.stringPropertyNames()) {
            if (key.contains(PASSWORD_KEY)) {
                properties.put(key, encryptionHandler.decrypt(properties.getProperty(key)));
            }
        }
        return properties;
    }
}
