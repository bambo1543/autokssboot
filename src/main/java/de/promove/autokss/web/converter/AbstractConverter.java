package de.promove.autokss.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by IntelliJ IDEA.
 * User: andreas
 * Date: 22.09.2010
 * Time: 00:47:41
 * To change this template use File | Settings | File Templates.
 */
public class AbstractConverter<T> implements Converter {

//    private Class clazz;
//
//    private GenericService genericService;
//
//    public AbstractConverter(Class clazz) {
//        this.clazz = clazz;
//    }
//
//    @Override
//    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
//        if(genericService == null) {
//            BeanManager bm = getBeanManager(facesContext);
//            Bean<GenericService> bean = (Bean<GenericService>) bm.getBeans(GenericService.class).iterator().next();
//            CreationalContext<GenericService> ctx = bm.createCreationalContext(bean);
//            genericService = (GenericService) bm.getReference(bean, GenericService.class, ctx);
//        }
//        return genericService.findById(clazz, s);
//    }
//
//    @Override
//    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
//        return (String) o;
//    }
//
//    private BeanManager getBeanManager(FacesContext facesContext)
//    {
//        return (BeanManager)
//              ((ServletContext) facesContext.getExternalContext().getContext())
//                   .getAttribute("javax.enterprise.inject.spi.BeanManager");
//    }
//

    public T getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
