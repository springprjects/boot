package com.thbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.thbs.service.UserAuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userAuthService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { 
		
		http.authorizeRequests()
	        .antMatchers("/secure/**").fullyAuthenticated()
	        .antMatchers("/secure/**").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/secure/admin/**", "/secured/admin/reports/**").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/login.xhtml", "/javax.faces.resources/**").permitAll()
        .and()
        	.formLogin()
        	.loginPage("/login.xhtml")
        	.failureForwardUrl("/login.xhtml")
        	.defaultSuccessUrl("/secure/user.xhtml?faces-redirect=true")

        .and()
        	.logout().logoutSuccessUrl("/login.xhtml").invalidateHttpSession(true).deleteCookies("JSESSIONID").clearAuthentication(true)
        .and()
        	.exceptionHandling().accessDeniedPage("/login.xhtml")
        .and()
	    	.sessionManagement()
	    	.sessionAuthenticationStrategy(concurrentSessionControlAuthenticationStrategy())
	        .maximumSessions(1)
	        .expiredUrl("/login.xhtml")
	        .maxSessionsPreventsLogin(true)
	        .sessionRegistry(sessionRegistry());

	
	}
	
	 public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy(){
	        ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
	        concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(true);
	        concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
	        return concurrentSessionControlAuthenticationStrategy;
	    }
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception        
    {
        return super.authenticationManagerBean();
    }
	
	
	  @Bean
	    public SessionRegistry sessionRegistry() {
	        SessionRegistry sessionRegistry = new SessionRegistryImpl();
	        return sessionRegistry;
	    }
	  
	  @Bean
	    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
	        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	    }
}
