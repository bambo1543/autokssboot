package de.promove.autokss.web.view;

import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.*;
import de.promove.autokss.service.GenericService;
import de.promove.autokss.service.MessungService;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.FacesUtils;
import de.promove.autokss.web.util.GrowlMessenger;
import de.promove.autokss.web.util.NavigationOutcome;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
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

    @Autowired
    private MessungService messungService;

    private Messung messung = new Messung();

    @PostConstruct
    public void init() {
        String remoteUser = FacesUtils.getRemoteUsername();
        User user = genericService.find(User.class, QueryParameter.with(User_.email, remoteUser));
        messung.setPruefer(user);
        messung.setPruefDatum(new Date());
    }

    public List<Maschine> getMaschinenWithoutMessung() {
        List<Maschine> maschinen = genericService.listAll(Maschine.class);
        List<Messung> messungen = genericService.list(Messung.class, QueryParameter.with(Messung_.locked, Boolean.FALSE), null, null);
        for (Messung messung : messungen) {
            maschinen.remove(messung.getMaschine());
        }
        Collections.sort(maschinen);
        return maschinen;
    }

    public Messung getMessung() {
        return messung;
    }

    public void updateRefill() {
        if(messung.isMessung1Complete()) {
            messungService.calculateRefill(messung);
        }
    }

    public String save() {
        if(getMaschinenWithoutMessung().contains(messung.getMaschine()) && messung.isMessung1Complete()) {
            messungService.calculateRefill(messung);
            messung.setTimestamp(Calendar.getInstance().getTime());
            genericService.persist(messung);
        } else {
            GrowlMessenger.publish("Offene Messung fuer Maschine '" + messung.getMaschine().getName() + "' bereits vorhanden.");
            return null;
        }

        return NavigationOutcome.START.getOutcome();
    }

    public String cancel() {
        return NavigationOutcome.START.getOutcome();
    }

}
