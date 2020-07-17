package it.dstech.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private MailContentBuilder mailContentBuilder;

	@Autowired
	private JavaMailSender javaMailSender;

	
	public void inviaMail(String destinatarioMail, String oggettoMail, String messaggioMail) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setSubject(oggettoMail);
		mimeMessageHelper.setTo(destinatarioMail);
		String contenuto = mailContentBuilder.build(messaggioMail);
		mimeMessageHelper.setText(contenuto, true);
		javaMailSender.send(mimeMessage);
	}	
	
}
