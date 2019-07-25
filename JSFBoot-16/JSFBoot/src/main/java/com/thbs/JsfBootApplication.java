package com.thbs;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sun.faces.config.ConfigureListener;

@SpringBootApplication
public class JsfBootApplication extends WebMvcConfigurerAdapter implements ServletContextAware  {

	@Value("${homePage}") 
	private String homePage;
	
	
	public static void main(String[] args) {
		SpringApplication.run(JsfBootApplication.class, args);
	}
	
	
		@Bean
		public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
			return new ServletListenerRegistrationBean<ConfigureListener>(
					new ConfigureListener());
		}
		
		@Bean
		public ServletRegistrationBean<FacesServlet> facesServletRegistration() {
			ServletRegistrationBean<FacesServlet> registration = new ServletRegistrationBean<FacesServlet>(
					new FacesServlet(), "*.xhtml");
			registration.setName("FacesServlet");
			return registration;
		}
		
		@Override
	    public void setServletContext(ServletContext servletContext) {
			
	        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());           
	    }
		
		@Override
	    public void addViewControllers( ViewControllerRegistry registry ) {
	        registry.addViewController( "/" ).setViewName( homePage );
	        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
	        super.addViewControllers( registry );
	    }
		
		@Bean
		public ServletContextInitializer initializer() {
		    return new ServletContextInitializer() {

		        @Override
		        public void onStartup(ServletContext servletContext) throws ServletException {
		          servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/spring-security.taglib.xml");
		        }
		    };
		}
}
