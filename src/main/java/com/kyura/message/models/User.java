package com.kyura.message.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.converter.PasswordAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User extends AbstractAuditing{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@NotBlank
	@Size(max = 20)
	private String username;

	//@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@Size(max = 20)
	private String phone;

	@Size(max = 50)
	private String avatar;
	//@NotBlank
	@Size(max = 120)
	private String password;

	//@NotBlank
	@Size(max=10)
	private String gender;

	private Date date_of_birth;

	private String fullname;

	//@NotBlank
	@Size(max=50)
	private String address;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", columnDefinition = "varchar(20) default 'ACTIVE'")
	private ACTIVE_STATUS status;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private MenuManagement menuManagement;

	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private RefreshToken refreshToken;

	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private Hospitals hospitals;

	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Booking booking;

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(String username, String email, String phone, String avatar, String password, String gender, Date date_of_birth, String address,String fullname) {
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.avatar = avatar;
		this.password = password;
		this.gender = gender;
		this.date_of_birth = date_of_birth;
		this.address = address;
		this.fullname = fullname;
	}

}
