package com.thbs.managedbeans;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;


@ManagedBean
@SessionScoped
@Getter
@Setter
public class LoginBean {

	private String username;

	private String password;

	private String actionText;

	@Autowired
	private AuthenticationManager authenticationManager;

	public String loginAction() {
		FacesMessage message = null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(this.username, String.valueOf(this.password)));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			password = null;
			return "/secure/user.xhtml?faces-redirect=true";
		} catch (BadCredentialsException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bad credentials", null);
		} catch (DisabledException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User is disabled", null);
		} catch (AuthenticationException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to authenticate", null);
		}
		if (message != null) {
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage("loginFormMessages", message);
		}

		return "";
	}

	public String logoutAction() {
		SecurityContextHolder.clearContext();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtm?faces-redirect=true";
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
    public String testUserAction(){
        actionText = "User action successfull";
        return "";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String testAdminAction(){
        actionText = "Admin action successfull";
        return "";
    }

}
