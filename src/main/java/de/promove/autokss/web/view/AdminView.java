package de.promove.autokss.web.view;

import de.promove.autokss.service.PersistentPropertiesService;
import de.promove.autokss.web.util.NavigationOutcome;
import de.promove.autokss.web.util.View;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

@View
public class AdminView {

    @Autowired
    private PersistentPropertiesService service;
    private Properties properties;

    public Properties getProperties() {
        if(properties == null) {
            properties = service.loadProperties("mail");
        }
        return properties;
    }

    public String save() {
        service.persistProperties(properties);
        return NavigationOutcome.START.getOutcome();
    }
    public String cancel() {
        return NavigationOutcome.START.getOutcome();
    }

}
