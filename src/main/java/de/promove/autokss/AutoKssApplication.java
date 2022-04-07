package de.promove.autokss;

import com.sun.faces.config.ConfigureListener;
import de.promove.autokss.web.scope.ViewScope;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import jakarta.faces.webapp.FacesServlet;
import jakarta.servlet.ServletContext;
import java.util.Arrays;

@SpringBootApplication
public class AutoKssApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AutoKssApplication.class, args);
	}

//	@Bean
//	ServletRegistrationBean jsfServletRegistration(ServletContext servletContext) {
//		//spring boot only works if this is set
//		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
//		servletContext.setInitParameter("jakarta.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", Boolean.TRUE.toString());
////		servletContext.setInitParameter("jakarta.faces.validator.DISABLE_DEFAULT_BEAN_VALIDATOR", Boolean.TRUE.toString());
//		servletContext.setInitParameter("primefaces.THEME", "#{themeSwitcherBean.currentTheme}");
//		servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/compositions/composition-taglib.xml");
////		servletContext.setInitParameter("com.sun.faces.expressionFactory", "org.apache.el.ExpressionFactoryImpl");
//
//		servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
//		servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
//		servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", Boolean.TRUE.toString());
//		servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
//		servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
//		servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
//		servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
//
//		//registration
//		ServletRegistrationBean<FacesServlet> srb = new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
//		srb.setLoadOnStartup(1);
//		return srb;
//	}

//	@Bean
//	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
//		return new ServletListenerRegistrationBean<>(new ConfigureListener());
//	}
//
//	@Bean
//	public ViewResolver getViewResolver() {
//		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//		resolver.setSuffix(".xhtml");
//		return resolver;
//	}

//	@Bean
//	public CustomScopeConfigurer getViewScope() {
//		CustomScopeConfigurer scope = new CustomScopeConfigurer();
//		scope.addScope("view", new ViewScope());
//		return scope;
//	}

}
