package de.promove.autokss.web.util;

import lombok.Getter;

@Getter
public enum NavigationOutcome {

    START("/index.xhtml"), LOGIN("/login.xhtml");

    private final String outcome;

    NavigationOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getOutcome(boolean redirect, String... parameters) {
        String result = outcome;
        if(redirect || parameters.length > 0) {
            result+="?";
            if(redirect) {
                result+="faces-redirect=true&";
            }
            for (String parameter : parameters) {
                result+=parameter + "&";
            }
            result = result.substring(0, result.length()-1);
        }
        return result;
    }

}
