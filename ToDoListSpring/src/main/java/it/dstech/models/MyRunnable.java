package it.dstech.models;

import javax.mail.MessagingException;

import it.dstech.service.MailService;


public class MyRunnable implements Runnable {
	
	private MailService mail;
	
	private Attivita attivita;
	
	public MyRunnable(Attivita attivita, MailService mail) {
		this.attivita = attivita;
		this.mail = mail;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		User currUser = attivita.getUser();
		try {
			mail.inviaMail(currUser.getEmail(), "Reminder " + attivita.getActivityTitle(), "This activity will expired in 5 minutes.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}

}
