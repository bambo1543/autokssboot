package de.promove.autokss.web.theme;

import de.promove.autokss.model.User;
import de.promove.autokss.service.UserService;
import de.promove.autokss.web.util.FacesUtils;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
@Controller
@SessionScope
public class ThemeSwitcherBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567L;

    private Map<String, Theme> themes = new HashMap<>();

    private Theme currentTheme;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        themes.put("arya", new Theme("arya", true));
        themes.put("luna-amber", new Theme("luna-amber", true));
        themes.put("luna-blue", new Theme("luna-blue", true));
        themes.put("luna-green", new Theme("luna-green", true));
        themes.put("luna-pink", new Theme("luna-pink", true));
        themes.put("nova-colored", new Theme("nova-colored"));
        themes.put("nova-dark", new Theme("nova-dark"));
        themes.put("nova-light", new Theme("nova-light"));
        themes.put("saga", new Theme("saga"));
        themes.put("vela", new Theme("vela", true));

        themes.put("afterdark", new Theme("afterdark", true));
        themes.put("afternoon", new Theme("afternoon"));
        themes.put("afterwork", new Theme("afterwork"));
        themes.put("black-tie", new Theme("black-tie"));
        themes.put("blitzer", new Theme("blitzer"));
        themes.put("bluesky", new Theme("bluesky"));
        themes.put("bootstrap", new Theme("bootstrap"));
        themes.put("casablanca", new Theme("casablanca"));
        themes.put("cruze", new Theme("cruze", true));
        themes.put("cupertino", new Theme("cupertino"));
        themes.put("dark-hive", new Theme("dark-hive", true));
        themes.put("delta", new Theme("delta"));
        themes.put("dot-luv", new Theme("dot-luv", true));
        themes.put("eggplant", new Theme("eggplant", true));
        themes.put("excite-bike", new Theme("excite-bike"));
        themes.put("flick", new Theme("flick"));
        themes.put("glass-x", new Theme("glass-x"));
        themes.put("home", new Theme("home"));
        themes.put("hot-sneaks", new Theme("hot-sneaks"));
        themes.put("humanity", new Theme("humanity"));
        themes.put("le-frog", new Theme("le-frog"));
        themes.put("midnight", new Theme("midnight", true));
        themes.put("mint-choc", new Theme("mint-choc", true));
        themes.put("overcast", new Theme("overcast"));
        themes.put("pepper-grinder", new Theme("pepper-grinder"));
        themes.put("redmond", new Theme("redmond"));
        themes.put("rocket", new Theme("rocket"));
        themes.put("sam", new Theme("sam"));
        themes.put("smoothness", new Theme("smoothness"));
        themes.put("south-street", new Theme("south-street"));
        themes.put("start", new Theme("start"));
        themes.put("sunny", new Theme("sunny"));
        themes.put("swanky-purse", new Theme("swanky-purse", true));
        themes.put("trontastic", new Theme("trontastic", true));
        themes.put("ui-darkness", new Theme("ui-darkness", true));
        themes.put("ui-lightness", new Theme("ui-lightness"));
        themes.put("vader", new Theme("vader", true));

        currentTheme = themes.get("afterwork");
    }

    public List<String> getThemes() {
        List<String> themeNames = new ArrayList<>(themes.keySet());
        Collections.sort(themeNames);
        return themeNames;
    }

    public String getCurrentTheme() {
        return currentTheme.getName();
    }

    public void setCurrentTheme(String currentTheme) {
        this.currentTheme = themes.get(currentTheme);
        String username = FacesUtils.getRemoteUsername();
        if(username != null) {
            User user = userService.findUserByUsername(username);
            user.setTheme(currentTheme);
            userService.merge(user);
        }

        submitUserSettings();
    }

    public void submitUserSettings() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ignored) {
        }
    }

    @EventListener
    public void loginSuccessful(InteractiveAuthenticationSuccessEvent event) {
        User user = (User) event.getAuthentication().getPrincipal();
        if(user.getTheme() != null && themes.keySet().contains(user.getTheme())) {
            this.currentTheme = themes.get(user.getTheme());
        }
    }

    public boolean currentThemeIsDark() {
        return currentTheme.isDark();
    }
}
