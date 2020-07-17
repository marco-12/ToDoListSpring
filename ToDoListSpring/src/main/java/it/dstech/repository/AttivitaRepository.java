package it.dstech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.dstech.models.Attivita;


@Repository
public interface AttivitaRepository extends CrudRepository<Attivita, Long> {

}
