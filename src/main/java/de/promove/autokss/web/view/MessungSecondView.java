package de.promove.autokss.web.view;

import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.model.*;
import de.promove.autokss.service.GenericService;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.FacesUtils;
import de.promove.autokss.web.util.GrowlMessenger;
import de.promove.autokss.web.util.NavigationOutcome;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@ViewScope
public class MessungSecondView {

    @Autowired
    private GenericService genericService;

    @Getter
    private Messung messung = new Messung();

    @Getter
    private List<Maschine> maschinen = new ArrayList<>();

    @Getter
    @Setter
    private Maschine maschine;
    @Getter
    private User pruefer;

    @PostConstruct
    public void init() {
        String remoteUsername = FacesUtils.getRemoteUsername();
        pruefer = genericService.find(User.class, QueryParameter.with(User_.email, remoteUsername));
        List<Messung> messungen = genericService.list(Messung.class, QueryParameter.with(Messung_.pruefer, pruefer).and(Messung_.locked, Boolean.FALSE));
        maschinen = messungen.stream().map(Messung::getMaschine).collect(Collectors.toList());
        if(!messungen.isEmpty()) {
            messung = messungen.get(0);
        }
    }

    public void onMaschineChange() {
        messung = genericService.find(Messung.class, QueryParameter.with(Messung_.pruefer, pruefer)
                .and(Messung_.locked, Boolean.FALSE).and(Messung_.maschine, messung.getMaschine()));
    }

    public String save() {
        Messung byId = genericService.findById(Messung.class, messung.getId());
        if(!byId.isLocked()) {
            messung.setTimestamp(Calendar.getInstance().getTime());
            messung.setLocked(true);
            genericService.merge(messung);
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
