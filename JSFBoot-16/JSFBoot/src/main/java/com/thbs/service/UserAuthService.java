package com.thbs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thbs.entities.User;
import com.thbs.entities.UserRole;
import com.thbs.repos.UserRepository;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOneByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("User " + username + " not found");

		Collection<UserRole> roles = user.getRole();
		
		if (roles == null || roles.isEmpty())
			throw new UsernameNotFoundException("Role not found for " + username );
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities = roles.stream().map(e->new SimpleGrantedAuthority(e.getRole())).collect(Collectors.toList());
		
		boolean enabled = user.isEnabled();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = !user.isLocked();
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

}

	