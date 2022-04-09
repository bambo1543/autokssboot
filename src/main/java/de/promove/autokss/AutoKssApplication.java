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

}
