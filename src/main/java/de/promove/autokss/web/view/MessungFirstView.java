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
public class MessungFirstView {

    @Autowired
    private GenericService genericService;

    private Messung messung = new Messung();

    @PostConstruct
    public void init() {
        String remoteUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        User user = genericService.find(User.class, QueryParameter.with(User_.email, remoteUser));
        messung.setPruefer(user);
        messung.setPruefDatum(new Date());
    }

    public Messung getMessung() {
        return messung;
    }

    public List<Maschine> getMaschinenForFirstMessung() {
        List<Maschine> maschinen = genericService.listAll(Maschine.class);
        List<Messung> messungen = genericService.list(Messung.class, QueryParameter.with(Messung_.locked, Boolean.FALSE), null, null);
        for (Messung messung : messungen) {
            maschinen.remove(messung.getMaschine());
        }
        Collections.sort(maschinen);
        return maschinen;
    }

    public String save() {
        messung.setTimestamp(Calendar.getInstance().getTime());
        genericService.persist(messung);
        return NavigationOutcome.START.getOutcome();
    }

    public String cancel() {
        return NavigationOutcome.START.getOutcome();
    }

}
