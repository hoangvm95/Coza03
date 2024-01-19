package com.kyura.message.services.impl;

import com.kyura.message.services.MailService;
import com.kyura.message.util.Constant;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private VelocityEngine velocityEngine;

	private Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

	@Value("${mail.host}")
	private String host;

	@Value("${mail.port}")
	private int port;

	@Value("${mail.username}")
	private String username;

	@Value("${mail.password}")
	private String password;

	@Override
	public String sendEmail(final String toEmailAddresses, final String fromEmailAddress, final String subject,
																			 String body) {
		// Assuming you are sending email from localhost
		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(fromEmailAddress));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmailAddresses));
			// Set Subject: header field
			message.setSubject(subject);
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			messageBodyPart.setText(body);
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// Part two is attachment
//			messageBodyPart = new MimeBodyPart();
//			String filename = "file.txt";
//			DataSource source = new FileDataSource(filename);
//			messageBodyPart.setDataHandler(new DataHandler(source));
//			messageBodyPart.setFileName(filename);
//			multipart.addBodyPart(messageBodyPart);
			// Send the complete message parts
			message.setContent(multipart );
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		return Constant.SUCCESS;
	}

	@Override
	public String sendEmail(final String toEmailAddresses, final String fromEmailAddress, final String subject,
													Map<String, String> mapData, final String templateFile, final List<String> attachments) throws Exception {
		// Assuming you are sending email from localhost
		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		properties.put("mail.smtp.port", "587"); //TLS Port
		properties.put("mail.smtp.auth", "true"); //enable authentication
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

		//create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmailAddress, password);
			}
		};
		Session session = Session.getInstance(properties, auth);
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(fromEmailAddress));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmailAddresses));
			// Set Subject: header field
			message.setSubject(subject);
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			VelocityContext context = new VelocityContext();
			if(!CollectionUtils.isEmpty(mapData)) {
				mapData.entrySet().stream().forEachOrdered(e -> context.put(e.getKey(), e.getValue()));
			}
			StringWriter stringWriter = new StringWriter();
			velocityEngine.mergeTemplate("templates/" + templateFile, Constant.UTF_8_ENCODING, context, stringWriter);
			final MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(stringWriter.toString(), Constant.HEADER_HTML_EMAIL);
			// Create a multipart message
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(htmlPart);
			// Part two is attachment
			if(!CollectionUtils.isEmpty(attachments)) {
				for (String attachment : attachments) {
					messageBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(attachment);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(FilenameUtils.getName(attachment));
					multipart.addBodyPart(messageBodyPart);
				};
			}
			// Send the complete message parts
			message.setContent(multipart);
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			throw new Exception(Constant.ERR_SEND_MAIL_FAILED);
		}
		return Constant.SUCCESS;
	}


}
