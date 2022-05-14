package de.promove.autokss.service.constant;

import lombok.Getter;

public enum PersistentPropertiesEnum {

    HOST("admin.email.spring.mail.host");

    @Getter
    private String key;

    PersistentPropertiesEnum(String key) {
        this.key = key;
    }

}
