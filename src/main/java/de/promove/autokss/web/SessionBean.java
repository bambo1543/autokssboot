package de.promove.autokss.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.context.FacesContext;
import java.io.Serializable;

@Controller
@SessionScope
public class SessionBean implements Serializable {

    public String getSessionId() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
    }

}
