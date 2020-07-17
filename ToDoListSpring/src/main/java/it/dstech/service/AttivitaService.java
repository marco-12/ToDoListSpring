package it.dstech.service;

import java.util.List;

import it.dstech.models.Attivita;

public interface AttivitaService {

	Attivita save(Attivita attivita);

	Attivita findByID(Long id);

	void delete(Long id);

	List<Attivita> getAllActivities();

}