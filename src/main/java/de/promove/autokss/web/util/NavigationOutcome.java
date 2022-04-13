package de.promove.autokss.web.util;

import lombok.Getter;

@Getter
public enum NavigationOutcome {

    START("/index.xhtml"), LOGIN("/login.xhtml");

    private final String outcome;

    NavigationOutcome(String outcome) {
        this.outcome = outcome;
    }

}
