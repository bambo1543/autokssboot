package de.promove.autokss.web.crud;

import de.promove.autokss.dao.QueryOrder;
import de.promove.autokss.model.Bereich;
import de.promove.autokss.model.Bereich_;
import de.promove.autokss.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
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

}