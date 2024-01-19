package com.kyura.message.models;

import com.kyura.message.common.ROLE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configuration")
public class Configuration {
	@Id
	private String key;

	@Column(columnDefinition = "text")
	private String value;

}