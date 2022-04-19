package de.promove.autokss.web.view;

import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.*;
import de.promove.autokss.service.GenericService;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.NavigationOutcome;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@ViewScope
public class MessungSecondView {

    @Autowired
    private GenericService genericService;

    private Messung messung;

    @PostConstruct
    public void init() {
    }

    public Messung getMessung() {
        return messung;
    }


    public String save() {
        return NavigationOutcome.START.getOutcome();
    }

    public String cancel() {
        return NavigationOutcome.START.getOutcome();
    }

}
