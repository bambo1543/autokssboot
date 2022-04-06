package de.promove.autokss.web.theme;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
@Controller
@SessionScope
public class ThemeSwitcherBean implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private List<String> themes;

    private String currentTheme;

    @PostConstruct
    public void init() {
        themes = new ArrayList<>();

        themes.add("arya");
        themes.add("luna-amber");
        themes.add("luna-blue");
        themes.add("luna-green");
        themes.add("luna-pink");
        themes.add("nova-colored");
        themes.add("nova-dark");
        themes.add("nova-light");
        themes.add("saga");
        themes.add("vela");

        themes.add("afterdark");
        themes.add("afternoon");
        themes.add("afterwork");
        themes.add("black-tie");
        themes.add("blitzer");
        themes.add("bluesky");
        themes.add("bootstrap");
        themes.add("casablanca");
        themes.add("cruze");
        themes.add("cupertino");
        themes.add("dark-hive");
        themes.add("delta");
        themes.add("dot-luv");
        themes.add("eggplant");
        themes.add("excite-bike");
        themes.add("flick");
        themes.add("glass-x");
        themes.add("home");
        themes.add("hot-sneaks");
        themes.add("humanity");
        themes.add("le-frog");
        themes.add("midnight");
        themes.add("mint-choc");
        themes.add("overcast");
        themes.add("pepper-grinder");
        themes.add("redmond");
        themes.add("rocket");
        themes.add("sam");
        themes.add("smoothness");
        themes.add("south-street");
        themes.add("start");
        themes.add("sunny");
        themes.add("swanky-purse");
        themes.add("trontastic");
        themes.add("ui-darkness");
        themes.add("ui-lightness");
        themes.add("vader");

        currentTheme = "afterwork";
//        currentTheme = themes.get(0);
    }

    public List<String> getThemes() {
        return themes;
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(String currentTheme) {
        this.currentTheme = currentTheme;
        submitUserSettings();
    }

    public void submitUserSettings() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ignored) {
        }
    }

}
