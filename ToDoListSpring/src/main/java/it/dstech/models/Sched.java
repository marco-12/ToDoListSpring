package it.dstech.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Sched implements Runnable {
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime date;

	private String message;

	public Sched() {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		System.out.println(message);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}


}
