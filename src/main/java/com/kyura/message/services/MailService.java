package com.kyura.message.services;

import java.util.List;
import java.util.Map;

public interface MailService {
	String sendEmail(final String toEmailAddress, final String fromEmailAddress, final String subject,
																String body);
	String sendEmail(final String toEmailAddresses, final String fromEmailAddress, final String subject,
									 Map<String, String> mapData, final String templateFile, final List<String> attachments) throws Exception;
}
