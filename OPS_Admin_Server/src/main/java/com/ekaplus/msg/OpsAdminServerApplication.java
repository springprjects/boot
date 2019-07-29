package com.ekaplus.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class OpsAdminServerApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(OpsAdminServerApplication.class, args);
	}
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .formLogin()
            .loginPage("/login")
            .permitAll();
        http
            .logout().logoutUrl("/logout").permitAll();
        http.csrf()
            .ignoringAntMatchers("/actuator/**", "/instances/**", "/logout");
        http
            .authorizeRequests()
            .antMatchers("/**/*.css", "/assets/**", "/third-party/**", "/logout", "/login")
            .permitAll();
        http
            .authorizeRequests()
            .anyRequest()
            .authenticated();
        http.httpBasic(); // Activate Http basic Auth for the server
    }

}
