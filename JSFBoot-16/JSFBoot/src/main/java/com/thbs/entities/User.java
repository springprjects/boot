package com.thbs.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }),
		@UniqueConstraint(columnNames = { "user_name" }, name = "uidx_user_name"),
		@UniqueConstraint(columnNames = { "verificationKey" }, name = "uidx_verification_key") })
@Getter
@Setter
public class User  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public long id;

	@Size(min = 1, max = 50)
	@Column(name = "user_name", length = 50, nullable = false)
	private String username;

	@Size(min = 1, max = 50)
	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;

	@Size(min = 1, max = 50)
	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	@Size(min = 1, max = 50)
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "{user.invalid.email.format}")
	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@Column(name = "password", length = 80)
	private String password;

	@Column(name = "is_enabled", columnDefinition = "INT(1) default 0")
	private boolean enabled;

	@Column(name = "is_locked", columnDefinition = "INT(1) default 0")
	private boolean locked;

	@Column(name = "is_subscribed", columnDefinition = "INT(1) default 0")
	private boolean subscribed;

	@Column(name = "verificationKey", length = 64)
	private String verificationKey;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserRole> role;

}
