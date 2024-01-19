package com.kyura.message.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PasswordAttributeConverter implements AttributeConverter<String, String> {

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public String convertToDatabaseColumn(String plainTextPassword) {
		return  passwordEncoder.encode(plainTextPassword);
	}

	@Override
	public String convertToEntityAttribute(String encryptedPassword) {
		//we do not want to decode passwords :)
		return encryptedPassword;
	}
}
