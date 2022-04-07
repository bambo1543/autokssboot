package de.promove.autokss;

import de.promove.autokss.web.scope.ViewScope;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.UrlBasedViewResolverRegistration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import jakarta.faces.webapp.FacesServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

@Configuration
public class JsfConfiguration implements ServletContextAware, ServletContextInitializer {

    @Bean
    public FacesServlet facesServlet() {
        return new FacesServlet();
    }

    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(facesServlet(), "*.xhtml", "*.jsf");
        registration.setName("FacesServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

//    @Bean
//    public FileUploadFilter fileUploadFilter() {
//        return new FileUploadFilter();
//    }
//
//    @Bean
//    public FilterRegistrationBean uploadFilterRegistration() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(fileUploadFilter());
//        filterRegistrationBean.addServletNames("FacesServlet");
//        System.out.println("register file upload filter .......................................");
//        return filterRegistrationBean;
//    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }

//    @Bean
//    public ViewResolver getJsfViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        //Files in /WEB-INF folder are indeed not publicly accessible by enduser
//        resolver.setPrefix("/views/");
//        //resolver.setSuffix(".jsf");
//        resolver.setSuffix(".xhtml");
//
//
//        return resolver;
//    }

//    @Bean
//    public UrlBasedViewResolver urlBasedViewResolver() {
//        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
//        urlBasedViewResolver.setViewClass(FaceletViewResolver.class);
//        urlBasedViewResolver.setPrefix("/views/");
//        urlBasedViewResolver.setSuffix(".xhtml");
//        //  urlBasedViewResolver.setSuffix(".jsf");
//        return urlBasedViewResolver;
//    }
//
//    @Bean
//    public UrlBasedViewResolverRegistration urlBasedViewResolverRegistration() {
//        UrlBasedViewResolverRegistration urlBasedViewResolverRegistration = new UrlBasedViewResolverRegistration
//                (urlBasedViewResolver());
//
//        return urlBasedViewResolverRegistration;
//    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		servletContext.setInitParameter("jakarta.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", Boolean.TRUE.toString());
//		servletContext.setInitParameter("jakarta.faces.validator.DISABLE_DEFAULT_BEAN_VALIDATOR", Boolean.TRUE.toString());
		servletContext.setInitParameter("primefaces.THEME", "#{themeSwitcherBean.currentTheme}");
		servletContext.setInitParameter("jakarta.faces.FACELETS_LIBRARIES", "/WEB-INF/compositions/composition-taglib.xml");
//		servletContext.setInitParameter("com.sun.faces.expressionFactory", "org.apache.el.ExpressionFactoryImpl");

        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
        servletContext.setInitParameter("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", Boolean.TRUE.toString());
        servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING", Boolean.FALSE.toString());
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Production");
        servletContext.setInitParameter("primefaces.UPLOADER", "commons");

    }

    @Bean
    public CustomScopeConfigurer getViewScope() {
        CustomScopeConfigurer scope = new CustomScopeConfigurer();
        scope.addScope("view", new ViewScope());
        return scope;
    }

}
