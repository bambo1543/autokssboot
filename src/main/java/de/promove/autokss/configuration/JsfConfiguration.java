package de.promove.autokss.configuration;

import de.promove.autokss.web.scope.JsfViewScope;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import org.springframework.context.annotation.Bean;

import jakarta.faces.webapp.FacesServlet;
import jakarta.servlet.ServletContext;

@Configuration
public class JsfConfiguration implements ServletContextAware, ServletContextInitializer {

    public static final String VIEW_SCOPE = "view";

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

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }

    @Override
    public void onStartup(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
//		servletContext.setInitParameter("com.sun.faces.expressionFactory", "org.apache.el.ExpressionFactoryImpl");
		servletContext.setInitParameter("jakarta.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", Boolean.TRUE.toString());
//		servletContext.setInitParameter("jakarta.faces.validator.DISABLE_DEFAULT_BEAN_VALIDATOR", Boolean.TRUE.toString());
		servletContext.setInitParameter("jakarta.faces.FACELETS_LIBRARIES", "/WEB-INF/compositions/composition-taglib.xml");
//        servletContext.setInitParameter("jakarta.faces.PARTIAL_STATE_SAVING", Boolean.FALSE.toString());
//        servletContext.setInitParameter("jakarta.faces.PROJECT_STAGE", "Production");

        servletContext.setInitParameter("primefaces.UPLOADER", "native");
        servletContext.setInitParameter("primefaces.THEME", "#{themeSwitcherBean.currentTheme}");
//        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
    }

    @Bean
    public CustomScopeConfigurer getViewScope() {
        CustomScopeConfigurer scope = new CustomScopeConfigurer();
        scope.addScope(VIEW_SCOPE, new JsfViewScope());
        return scope;
    }

//    @Bean
//    public CharacterEncodingFilter characterEncodingFilter() {
//        return new CharacterEncodingFilter();
//    }
//    @Bean
//    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterFilterRegistrationBean() {
//        FilterRegistrationBean<CharacterEncodingFilter> filterRegistrationBean = new FilterRegistrationBean<>(characterEncodingFilter());
//        filterRegistrationBean.addServletNames("FacesServlet");
//        return filterRegistrationBean;
//    }


//    @Bean
//    public FileUploadFilter fileUploadFilter() {
//        return new FileUploadFilter();
//    }
//    @Bean
//    public FilterRegistrationBean<FileUploadFilter> uploadFilterRegistration() {
//        FilterRegistrationBean<FileUploadFilter> filterRegistrationBean = new FilterRegistrationBean<>(fileUploadFilter());
//        filterRegistrationBean.addServletNames("FacesServlet");
//        return filterRegistrationBean;
//    }

}
