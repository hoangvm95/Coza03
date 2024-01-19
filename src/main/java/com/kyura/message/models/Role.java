package com.kyura.message.models;

import com.kyura.message.common.ROLE;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ROLE name;

	@OneToOne(mappedBy = "role",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private MenuManagement menuManagement;

}