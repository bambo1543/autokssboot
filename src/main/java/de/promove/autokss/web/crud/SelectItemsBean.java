package de.promove.autokss.web.crud;

import de.promove.autokss.dao.QueryOrder;
import de.promove.autokss.model.*;
import de.promove.autokss.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.view.ViewScoped;
import java.util.List;

@Component
@ManagedBean
@RequestScope
public class SelectItemsBean {

    @Autowired
    private GenericService genericService;

    public List<Bereich> getBereiche() {
        return genericService.listAll(Bereich.class, QueryOrder.by(Bereich_.name));
    }
    public List<Maschine> getMaschine() {
        return genericService.listAll(Maschine.class, QueryOrder.by(Maschine_.name));
    }
    public List<User> getUser() {
        return genericService.listAll(User.class, QueryOrder.by(User_.email));
    }

    public List<Einsatzkonzentration> getEinsatzkonzentrationen() {return genericService.listAll(Einsatzkonzentration.class, QueryOrder.by(Einsatzkonzentration_.SOLL));}

}
